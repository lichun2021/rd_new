@echo off
setlocal
cd /d %~dp0

echo ====================================
echo    批量热更新脚本
echo    差异类数: 5
echo ====================================
echo.

rem 检查端口号参数
if "%~1"=="" (
    echo 用法: hotfix_20260518_2258.bat [端口号]
    echo 示例: hotfix_20260518_2258.bat 8080
    echo.
    pause
    exit /b 1
)

set PORT=%~1
echo 使用端口: %PORT%
echo.

echo [热更新] com.hawk.game.GsApp 
call .\hotfix-class.bat com.hawk.game.GsApp %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [热更新] com.hawk.game.module.PlayerRechargeModule 
call .\hotfix-class.bat com.hawk.game.module.PlayerRechargeModule %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [热更新] com.hawk.game.module.plantsoldier.advance.PlantSoldierAdvanceModule 
call .\hotfix-class.bat com.hawk.game.module.plantsoldier.advance.PlantSoldierAdvanceModule %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [热更新] com.hawk.game.module.plantsoldier.strengthen.PlayerPlantSoldierSchoolModule 
call .\hotfix-class.bat com.hawk.game.module.plantsoldier.strengthen.PlayerPlantSoldierSchoolModule %PORT% 
timeout /t 1 /nobreak >nul 
echo. 
echo [热更新] com.hawk.game.util.LoginUtil 
call .\hotfix-class.bat com.hawk.game.util.LoginUtil %PORT% 
timeout /t 1 /nobreak >nul 
echo. 

echo ====================================
echo 热更新完成！
echo ====================================
pause
