@echo off
setlocal
cd /d %~dp0

echo ====================================
echo    Batch Hotfix Script
echo    Changed classes: 18
echo ====================================
echo.

rem Check port parameter
if "%~1"=="" (
    echo Usage: hotfix_20260730_1924.bat [port]
    echo Example: hotfix_20260730_1924.bat 8080
    echo.
    pause
    exit /b 1
)

set PORT=%~1
echo Port: %PORT%
echo.


echo [hotfix] com.hawk.game.crossproxy.CrossProxy 
call .\hotfix-class.bat com.hawk.game.crossproxy.CrossProxy %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.crossproxy.CrossService 
call .\hotfix-class.bat com.hawk.game.crossproxy.CrossService %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.crossproxy.ProxyHelper 
call .\hotfix-class.bat com.hawk.game.crossproxy.ProxyHelper %PORT% 
timeout /t 1 /nobreak >nul 
echo. 

echo ====================================
echo Hotfix completed
echo ====================================
pause
