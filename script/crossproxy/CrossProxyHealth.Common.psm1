Set-StrictMode -Version Latest

function New-CrossProxyLogResult {
    param(
        [string]$Status,
        [string[]]$Reasons,
        [object]$Evidence,
        [object]$Counts,
        [object]$LatestMonitor,
        [string[]]$ProxyAddresses
    )

    [PSCustomObject]@{
        Status = $Status
        Reasons = @($Reasons)
        Evidence = $Evidence
        Counts = $Counts
        LatestMonitor = $LatestMonitor
        ProxyAddresses = @($ProxyAddresses | Sort-Object -Unique)
    }
}

function Get-CrossProxyTimestamp {
    param([string]$Line)

    if ($Line -match '^(?<time>\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2},\d{3})') {
        return [DateTime]::ParseExact($Matches.time, 'yyyy-MM-dd HH:mm:ss,fff', [Globalization.CultureInfo]::InvariantCulture)
    }

    return $null
}

function Get-CrossProxyField {
    param(
        [string]$Line,
        [string]$Name
    )

    $pattern = '\b' + [Regex]::Escape($Name) + ':\s*(?<value>[^,]+)'
    if ($Line -match $pattern) {
        return $Matches.value.Trim()
    }

    return $null
}

function Get-CrossProxyLogHealth {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory)]
        [string]$LogPath,
        [ValidateRange(0, 525600)]
        [int]$SinceMinutes = 0
    )

    $emptyCounts = [PSCustomObject]@{
        HealthyMonitor = 0
        RecoveringMonitor = 0
        HeartbeatValidated = 0
        RecoverySocketCreated = 0
        BlockedBusinessSend = 0
        RpcTimeout = 0
        TiberiumSourceCheckPassed = 0
        TiberiumPrepareTimeout = 0
        TiberiumPrepareCrossFail = 0
        TargetPrepareReceived = 0
    }
    $emptyEvidence = [PSCustomObject]@{
        LocalBusinessSendBlocked = $false
        SourceServerId = $null
        TargetServerId = $null
        TargetServerReceived = $null
        ExpectedProxyAddress = $null
        ExpectedProxyAddressMatched = $null
    }

    if (-not (Test-Path -LiteralPath $LogPath -PathType Leaf)) {
        return New-CrossProxyLogResult -Status 'LogUnavailable' -Reasons @("Log file not found: $LogPath") -Evidence $emptyEvidence -Counts $emptyCounts -LatestMonitor $null -ProxyAddresses @()
    }

    $events = New-Object System.Collections.Generic.List[object]
    $latestTimestamp = $null
    try {
        foreach ($line in [System.IO.File]::ReadLines($LogPath)) {
            $timestamp = Get-CrossProxyTimestamp -Line $line
            if ($timestamp -and ($null -eq $latestTimestamp -or $timestamp -gt $latestTimestamp)) {
                $latestTimestamp = $timestamp
            }

            if ($line -match 'crossProxy ten seconds|csproxy heartbeat validated|csproxy recovery socket created|csproxy send blocked, state:|csproxy rpc timeout|tiberium condtion check|tiberium prepare enter timeout|tiberium prepare cross fail|prepare cross server code') {
                $events.Add([PSCustomObject]@{ Timestamp = $timestamp; Line = $line })
            }
        }
    }
    catch {
        return New-CrossProxyLogResult -Status 'LogUnavailable' -Reasons @("Unable to read log file: $($_.Exception.Message)") -Evidence $emptyEvidence -Counts $emptyCounts -LatestMonitor $null -ProxyAddresses @()
    }

    $cutoff = $null
    if ($SinceMinutes -gt 0 -and $latestTimestamp) {
        $cutoff = $latestTimestamp.AddMinutes(-$SinceMinutes)
    }

    $counts = [PSCustomObject]@{
        HealthyMonitor = 0
        RecoveringMonitor = 0
        HeartbeatValidated = 0
        RecoverySocketCreated = 0
        BlockedBusinessSend = 0
        RpcTimeout = 0
        TiberiumSourceCheckPassed = 0
        TiberiumPrepareTimeout = 0
        TiberiumPrepareCrossFail = 0
        TargetPrepareReceived = 0
    }
    $evidence = [PSCustomObject]@{
        LocalBusinessSendBlocked = $false
        SourceServerId = $null
        TargetServerId = $null
        TargetServerReceived = $null
        ExpectedProxyAddress = $null
        ExpectedProxyAddressMatched = $null
    }
    $proxyAddresses = New-Object System.Collections.Generic.List[string]
    $latestMonitor = $null

    foreach ($event in $events) {
        if ($cutoff -and $event.Timestamp -and $event.Timestamp -lt $cutoff) {
            continue
        }

        $line = $event.Line
        if ($line -match 'crossProxy ten seconds') {
            $state = Get-CrossProxyField -Line $line -Name 'state'
            $activeAddress = Get-CrossProxyField -Line $line -Name 'activeAddr'
            $desiredAddress = Get-CrossProxyField -Line $line -Name 'desiredAddr'
            $proxyAddress = Get-CrossProxyField -Line $line -Name 'proxyAddr'
            foreach ($address in @($activeAddress, $desiredAddress, $proxyAddress)) {
                if ($address) {
                    $proxyAddresses.Add($address)
                }
            }
            if ($state -eq 'HEALTHY') {
                $counts.HealthyMonitor++
            }
            elseif ($state -eq 'RECOVERING') {
                $counts.RecoveringMonitor++
            }
            $latestMonitor = [PSCustomObject]@{
                Timestamp = $event.Timestamp
                State = $state
                ActiveAddress = $activeAddress
                DesiredAddress = $desiredAddress
                ProxyAddress = $proxyAddress
                MasterServer = Get-CrossProxyField -Line $line -Name 'masterServer'
                ConfiguredNodeCount = Get-CrossProxyField -Line $line -Name 'configuredNodeCount'
                KeepaliveAge = Get-CrossProxyField -Line $line -Name 'keepaliveAge'
                SentProtocolCount = Get-CrossProxyField -Line $line -Name 'sendProtocolNum'
                ReceivedProtocolCount = Get-CrossProxyField -Line $line -Name 'receivedProtocolNum'
            }
        }
        elseif ($line -match 'csproxy heartbeat validated') {
            $counts.HeartbeatValidated++
        }
        elseif ($line -match 'csproxy recovery socket created') {
            $counts.RecoverySocketCreated++
        }
        elseif ($line -match 'csproxy send blocked, state:') {
            $counts.BlockedBusinessSend++
            $evidence.LocalBusinessSendBlocked = $true
            $sourceId = Get-CrossProxyField -Line $line -Name 'from'
            $targetId = Get-CrossProxyField -Line $line -Name 'to'
            if ($sourceId) {
                $evidence.SourceServerId = $sourceId
            }
            if ($targetId) {
                $evidence.TargetServerId = $targetId
            }
        }
        elseif ($line -match 'csproxy rpc timeout') {
            $counts.RpcTimeout++
        }
        elseif ($line -match 'tiberium condtion check.*errorCode:0') {
            $counts.TiberiumSourceCheckPassed++
        }
        elseif ($line -match 'tiberium prepare enter timeout') {
            $counts.TiberiumPrepareTimeout++
        }
        elseif ($line -match 'tiberium prepare cross fail') {
            $counts.TiberiumPrepareCrossFail++
        }
        elseif ($line -match 'prepare cross server code') {
            $counts.TargetPrepareReceived++
            $evidence.TargetServerReceived = $true
        }
    }

    $reasons = New-Object System.Collections.Generic.List[string]
    $status = 'EvidenceInsufficient'
    if ($latestMonitor) {
        if ($latestMonitor.State -eq 'RECOVERING' -or $evidence.LocalBusinessSendBlocked) {
            $status = 'CrossProxyRecovering'
            $reasons.Add('CrossProxy is recovering or blocked a local business send.')
        }
        elseif ($latestMonitor.State -eq 'HEALTHY' -and $counts.HeartbeatValidated -gt 0) {
            $status = 'Healthy'
            $reasons.Add('Latest monitor state is HEALTHY and heartbeat validation is present.')
        }
        else {
            $reasons.Add('CrossProxy monitor records do not contain sufficient healthy or failure evidence.')
        }
    }
    else {
        $reasons.Add('No CrossProxy monitor records were found in the requested log window.')
    }

    return New-CrossProxyLogResult -Status $status -Reasons $reasons.ToArray() -Evidence $evidence -Counts $counts -LatestMonitor $latestMonitor -ProxyAddresses $proxyAddresses.ToArray()
}

function Resolve-CrossProxyRuntimeStatus {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory)]
        [bool]$TcpSucceeded,
        [Parameter(Mandatory)]
        [string]$LogStatus
    )

    if (-not $TcpSucceeded) {
        return 'TcpUnreachable'
    }
    if ($LogStatus -eq 'Healthy') {
        return 'Healthy'
    }
    if ($LogStatus -eq 'CrossProxyRecovering') {
        return 'TcpReachableButNoHeartbeat'
    }
    return $LogStatus
}

function Get-CrossProxyExitCode {
    [CmdletBinding()]
    param([Parameter(Mandatory)][string]$Status)

    switch ($Status) {
        'Healthy' { return 0 }
        'LogUnavailable' { return 2 }
        'EvidenceInsufficient' { return 2 }
        default { return 1 }
    }
}

function Write-CrossProxyHealthReport {
    [CmdletBinding()]
    param([Parameter(Mandatory)][object]$Result)

    Write-Host "CrossProxy status: $($Result.Status)"
    foreach ($reason in $Result.Reasons) {
        Write-Host "Reason: $reason"
    }
    Write-Host "Proxy addresses: $($Result.ProxyAddresses -join ', ')"
    Write-Host "Counts: healthy=$($Result.Counts.HealthyMonitor), recovering=$($Result.Counts.RecoveringMonitor), heartbeatValidated=$($Result.Counts.HeartbeatValidated), blockedSend=$($Result.Counts.BlockedBusinessSend), tiberiumTimeout=$($Result.Counts.TiberiumPrepareTimeout)"
    if ($Result.LatestMonitor) {
        Write-Host "Latest monitor: state=$($Result.LatestMonitor.State), activeAddr=$($Result.LatestMonitor.ActiveAddress), masterServer=$($Result.LatestMonitor.MasterServer), configuredNodeCount=$($Result.LatestMonitor.ConfiguredNodeCount), keepaliveAge=$($Result.LatestMonitor.KeepaliveAge)"
    }
    if ($Result.Evidence.LocalBusinessSendBlocked) {
        Write-Host "Local business send blocked: from=$($Result.Evidence.SourceServerId), to=$($Result.Evidence.TargetServerId)"
    }
}

function New-CrossProxyRuntimeResult {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory)][object]$LogResult,
        [Parameter(Mandatory)][bool]$TcpSucceeded,
        [Parameter(Mandatory)][string]$ProxyAddress,
        [Parameter(Mandatory)][int]$ProxyPort,
        [Nullable[int]]$TcpLatencyMilliseconds
    )

    $status = Resolve-CrossProxyRuntimeStatus -TcpSucceeded $TcpSucceeded -LogStatus $LogResult.Status
    $reasons = New-Object System.Collections.Generic.List[string]
    foreach ($reason in $LogResult.Reasons) {
        $reasons.Add($reason)
    }
    if ($TcpSucceeded) {
        $reasons.Add("TCP connection to $ProxyAddress`:$ProxyPort succeeded. This does not prove a ZeroMQ heartbeat or target-server availability.")
    }
    else {
        $reasons.Add("TCP connection to $ProxyAddress`:$ProxyPort failed from this source server.")
    }

    return [PSCustomObject]@{
        Status = $status
        Reasons = $reasons.ToArray()
        ProxyAddress = $ProxyAddress
        ProxyPort = $ProxyPort
        TcpSucceeded = $TcpSucceeded
        TcpLatencyMilliseconds = $TcpLatencyMilliseconds
        Log = $LogResult
    }
}

function Write-CrossProxyRuntimeReport {
    [CmdletBinding()]
    param([Parameter(Mandatory)][object]$Result)

    Write-Host "CrossProxy runtime status: $($Result.Status)"
    Write-Host "TCP endpoint: $($Result.ProxyAddress):$($Result.ProxyPort), succeeded=$($Result.TcpSucceeded), latencyMs=$($Result.TcpLatencyMilliseconds)"
    foreach ($reason in $Result.Reasons) {
        Write-Host "Reason: $reason"
    }
    Write-CrossProxyHealthReport -Result $Result.Log
}

function Export-CrossProxyHealthReport {
    [CmdletBinding()]
    param(
        [Parameter(Mandatory)][object]$Result,
        [Parameter(Mandatory)][string]$ReportPath
    )

    $parent = Split-Path -Parent $ReportPath
    if ($parent -and -not (Test-Path -LiteralPath $parent -PathType Container)) {
        throw "Report directory does not exist: $parent"
    }
    $Result | ConvertTo-Json -Depth 6 | Set-Content -LiteralPath $ReportPath -Encoding UTF8
}

Export-ModuleMember -Function Get-CrossProxyLogHealth, Resolve-CrossProxyRuntimeStatus, Get-CrossProxyExitCode, Write-CrossProxyHealthReport, New-CrossProxyRuntimeResult, Write-CrossProxyRuntimeReport, Export-CrossProxyHealthReport
