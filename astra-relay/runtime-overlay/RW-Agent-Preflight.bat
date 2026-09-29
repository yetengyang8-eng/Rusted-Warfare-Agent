@echo off
setlocal
chcp 65001 >nul
cd /d "%~dp0"
"jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -cp rw-agent-bootstrap.jar io.rwagent.client.PreflightClient
set "result=%errorlevel%"
pause
exit /b %result%
