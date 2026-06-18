@echo off
setlocal
cd /d %~dp0

echo ====================================
echo    Batch Hotfix Script
echo    Changed classes: 14
echo ====================================
echo.

rem Check port parameter
if "%~1"=="" (
    echo Usage: hotfix_20260607_2029.bat [port]
    echo Example: hotfix_20260607_2029.bat 8080
    echo.
    pause
    exit /b 1
)

set PORT=%~1
echo Port: %PORT%
echo.

echo [hotfix] com.hawk.game.GsApp 
call .\hotfix-class.bat com.hawk.game.GsApp %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.battle.Battle 
call .\hotfix-class.bat com.hawk.game.battle.Battle %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.battle.BattleSoldier 
call .\hotfix-class.bat com.hawk.game.battle.BattleSoldier %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.lianmengxzq.march.IXZQMarch 
call .\hotfix-class.bat com.hawk.game.lianmengxzq.march.IXZQMarch %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.PlayerRechargeModule 
call .\hotfix-class.bat com.hawk.game.module.PlayerRechargeModule %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.plantfactory.PlayerPlantTechModule 
call .\hotfix-class.bat com.hawk.game.module.plantfactory.PlayerPlantTechModule %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.plantsoldier.advance.PlantSoldierAdvanceModule 
call .\hotfix-class.bat com.hawk.game.module.plantsoldier.advance.PlantSoldierAdvanceModule %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.module.plantsoldier.strengthen.PlayerPlantSoldierSchoolModule 
call .\hotfix-class.bat com.hawk.game.module.plantsoldier.strengthen.PlayerPlantSoldierSchoolModule %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.util.LoginUtil 
call .\hotfix-class.bat com.hawk.game.util.LoginUtil %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.world.WorldMarchService 
call .\hotfix-class.bat com.hawk.game.world.WorldMarchService %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.world.march.submarch.BasedMarch 
call .\hotfix-class.bat com.hawk.game.world.march.submarch.BasedMarch %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.world.march.submarch.ChristmasMarch 
call .\hotfix-class.bat com.hawk.game.world.march.submarch.ChristmasMarch %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.world.march.submarch.NianMarch 
call .\hotfix-class.bat com.hawk.game.world.march.submarch.NianMarch %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [hotfix] com.hawk.game.world.march.submarch.SuperWeaponMarch 
call .\hotfix-class.bat com.hawk.game.world.march.submarch.SuperWeaponMarch %PORT% 
timeout /t 1 /nobreak >nul 
echo. 

echo ====================================
echo Hotfix completed
echo ====================================
pause
