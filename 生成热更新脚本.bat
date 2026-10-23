@echo off
setlocal
chcp 65001 >nul
set "HOTFIX_GENERATOR=%~f0"
powershell -NoProfile -ExecutionPolicy Bypass -Command "$s=[IO.File]::ReadAllText($env:HOTFIX_GENERATOR); & ([scriptblock]::Create(($s -split '(?m)^# POWERSHELL_BODY\r?\n',2)[1]))"
set "RESULT=%ERRORLEVEL%"
if /I not "%~1"=="/Q" pause
exit /b %RESULT%
# POWERSHELL_BODY
$ErrorActionPreference = 'Stop'
function Get-ContentHash([string]$path) {
    $stream = [IO.File]::OpenRead($path)
    $sha = [Security.Cryptography.SHA256]::Create()
    try { return [BitConverter]::ToString($sha.ComputeHash($stream)) }
    finally { $stream.Dispose(); $sha.Dispose() }
}
try {
    $root = Split-Path -Parent $env:HOTFIX_GENERATOR
    $online = Join-Path $root 'online'
    $groups = @(
        @{ Name='classes\java\main'; Source=(Join-Path $root 'build\classes\java\main'); Baseline=(Join-Path $online 'classes\java\main'); Filter='*.class' },
        @{ Name='xml'; Source=(Join-Path $root 'xml'); Baseline=(Join-Path $online 'xml'); Filter='*.xml' }
    )
    foreach ($group in $groups) {
        foreach ($path in @($group.Source, $group.Baseline)) {
            if (-not (Test-Path -LiteralPath $path -PathType Container)) { throw "Directory not found: $path" }
        }
    }
    $changes = @(foreach ($group in $groups) {
        foreach ($file in Get-ChildItem -LiteralPath $group.Source -Filter $group.Filter -File -Recurse) {
            $relative = $file.FullName.Substring($group.Source.Length + 1)
            $old = Join-Path $group.Baseline $relative
            $added = -not (Test-Path -LiteralPath $old -PathType Leaf)
            if ($added -or ((Get-ContentHash $file.FullName) -ne (Get-ContentHash $old))) {
                [pscustomobject]@{ Group=$group.Name; File=$file.FullName; Relative=$relative; Added=$added }
            }
        }
    })
    if ($changes.Count -eq 0) { Write-Host 'No class or XML changes found.'; exit 0 }
    $date = Get-Date -Format 'yyyyMMdd_HHmmss'
    $package = Join-Path $online $date
    $index = 1
    while (Test-Path -LiteralPath $package) { $package = Join-Path $online ($date + '_' + $index); $index++ }
    New-Item -ItemType Directory -Path $package | Out-Null
    foreach ($name in @('classes\java\main','xml')) { New-Item -ItemType Directory -Path (Join-Path $package $name) | Out-Null }
    foreach ($change in $changes) {
        $destination = Join-Path (Join-Path $package $change.Group) $change.Relative
        [IO.Directory]::CreateDirectory((Split-Path -Parent $destination)) | Out-Null
        Copy-Item -LiteralPath $change.File -Destination $destination
        $label = if ($change.Added) { 'Added' } else { 'Modified' }
        Write-Host "[$label][$($change.Group)] $($change.Relative)"
    }
    $update = @"
@echo off
setlocal
chcp 65001 >nul
set "PACKAGE=%~dp0"
if not "%~1"=="" goto :single
rem No arguments: deploy to both configured servers, even if one fails.
call :deploy 8080 "D:\game"
set "FIRST_RESULT=%ERRORLEVEL%"
call :deploy 8081 "D:\game2"
set "SECOND_RESULT=%ERRORLEVEL%"
if not "%FIRST_RESULT%"=="0" exit /b 1
if not "%SECOND_RESULT%"=="0" exit /b 1
echo Both servers updated successfully.
exit /b 0
:single
set "TARGET=%~2"
if not "%TARGET%"=="" goto :run_single
if "%~1"=="8080" set "TARGET=D:\game"
if "%~1"=="8081" set "TARGET=D:\game2"
if "%TARGET%"=="" (
    echo Usage: update.bat [port] [server-root]
    exit /b 1
)
:run_single
call :deploy "%~1" "%TARGET%"
exit /b %ERRORLEVEL%
:deploy
set "PORT=%~1"
set "SERVER=%~2"
echo Deploying to %SERVER% on port %PORT%
if not exist "%SERVER%\xml\" (
    echo Server xml directory not found. Pass the server root as the second argument.
    exit /b 1
)
echo Deploying changed files to %SERVER%
robocopy "%PACKAGE%xml" "%SERVER%\xml" *.xml /E /R:1 /W:1 /NFL /NDL /NJH /NJS
if errorlevel 8 exit /b 1
dir /b /s "%PACKAGE%classes\java\main\*.class" >nul 2>&1
if errorlevel 1 goto :done
robocopy "%PACKAGE%classes\java\main" "%SERVER%\classes\java\main" *.class /E /R:1 /W:1 /NFL /NDL /NJH /NJS
if errorlevel 8 exit /b 1
robocopy "%PACKAGE%classes\java\main" "%SERVER%\hotfix" *.class /E /R:1 /W:1 /NFL /NDL /NJH /NJS
if errorlevel 8 exit /b 1
set "HOTFIX_RESPONSE=%TEMP%\hotfix_response_%RANDOM%_%RANDOM%.json"
curl --fail --show-error --output "%HOTFIX_RESPONSE%" "http://localhost:%PORT%/script/hotfix"
if errorlevel 1 (
    del "%HOTFIX_RESPONSE%" 2>nul
    echo Hotfix request failed. Files were copied, but runtime activation is not confirmed.
    exit /b 1
)
type "%HOTFIX_RESPONSE%"
powershell -NoProfile -Command "try { `$r=Get-Content -LiteralPath `$env:HOTFIX_RESPONSE -Raw | ConvertFrom-Json; if (`$null -eq `$r.code -or `$r.code -ne 0) { exit 1 } } catch { exit 1 }"
if errorlevel 1 (
    del "%HOTFIX_RESPONSE%" 2>nul
    echo Hotfix API returned failure. Runtime code update is NOT confirmed.
    exit /b 1
)
del "%HOTFIX_RESPONSE%" 2>nul
echo Hotfix API reported success.
:done
echo File deployment completed. XML configuration may require a separate reload.
exit /b 0
"@
    [IO.File]::WriteAllText((Join-Path $package 'update.bat'), ($update -replace '\r?\n', "`r`n"), (New-Object Text.UTF8Encoding($false)))
    Write-Host "Added: $(@($changes | Where-Object Added).Count), Modified: $(@($changes | Where-Object { -not $_.Added }).Count)"
    Write-Host "Package: $package"
    Write-Host 'Default: update.bat deploys to D:\game:8080 and D:\game2:8081'
    Write-Host 'Or: update.bat 8080 "D:\game"'
    Write-Host 'Only added and modified files are packaged. Deletions are excluded; online baselines remain unchanged.'
    exit 0
} catch {
    [Console]::Error.WriteLine("Generation failed: " + $_.Exception.Message)
    exit 1
}