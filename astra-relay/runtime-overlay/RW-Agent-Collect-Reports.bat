@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"
where py >nul 2>nul
if errorlevel 1 (
  python tools\collect_reports.py %*
) else (
  py -3 tools\collect_reports.py %*
)
set "result=%errorlevel%"
pause
exit /b %result%
