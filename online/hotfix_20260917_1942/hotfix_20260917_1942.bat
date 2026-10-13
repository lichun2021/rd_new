@echo off
setlocal
cd /d %~dp0

echo ====================================
echo    Batch Hotfix Script
echo    Changed classes: 8
echo ====================================
echo.

rem Check port parameter
if "%~1"=="" (
    echo Usage: hotfix_20260917_1942.bat [port]
    echo Example: hotfix_20260917_1942.bat 8080
    echo.
    pause
    exit /b 1
)

set PORT=%~1
echo Port: %PORT%
echo.

echo [hotfix] com.hawk.game.battle.BattleSoldier 
call .\hotfix-class.bat com.hawk.game.battle.BattleSoldier %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.battle.BattleSoldier_3 
call .\hotfix-class.bat com.hawk.game.battle.BattleSoldier_3 %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.battle.effect.impl.hero1120.Hero1120Rules 
call .\hotfix-class.bat com.hawk.game.battle.effect.impl.hero1120.Hero1120Rules %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.config.ConstProperty 
call .\hotfix-class.bat com.hawk.game.config.ConstProperty %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.dayazhizhan.battleroom.player.DYZZPlayerEffect 
call .\hotfix-class.bat com.hawk.game.module.dayazhizhan.battleroom.player.DYZZPlayerEffect %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.dayazhizhan.battleroom.player.rogue.DYZZRogueCollection 
call .\hotfix-class.bat com.hawk.game.module.dayazhizhan.battleroom.player.rogue.DYZZRogueCollection %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.lianmengtaiboliya.player.TBLYPlayerEffect 
call .\hotfix-class.bat com.hawk.game.module.lianmengtaiboliya.player.TBLYPlayerEffect %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.lianmengXianquhx.player.XQHXPlayerEffect 
call .\hotfix-class.bat com.hawk.game.module.lianmengXianquhx.player.XQHXPlayerEffect %PORT% /Q
timeout /t 1 /nobreak >nul 
echo. 

echo ====================================
echo Hotfix completed
echo ====================================
pause
