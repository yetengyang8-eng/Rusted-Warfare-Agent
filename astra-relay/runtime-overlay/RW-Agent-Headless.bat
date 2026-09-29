@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"
where py >nul 2>nul
if errorlevel 1 (
  python tools\run_headless.py --game-dir . %*
) else (
  py -3 tools\run_headless.py --game-dir . %*
)
set "result=%errorlevel%"
pause
exit /b %result%
