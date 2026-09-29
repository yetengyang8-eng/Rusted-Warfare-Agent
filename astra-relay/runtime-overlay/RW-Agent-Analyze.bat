@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"
where py >nul 2>nul
if errorlevel 1 (
  python tools\analyze_reports.py rw-agent-reports --out analysis --strict %*
) else (
  py -3 tools\analyze_reports.py rw-agent-reports --out analysis --strict %*
)
set "result=%errorlevel%"
pause
exit /b %result%
