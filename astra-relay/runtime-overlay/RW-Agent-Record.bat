@echo off
setlocal
cd /d "%~dp0"
if not exist "jvm64\bin\java.exe" (
  echo Put these files in the original game folder beside game-lib.jar.
  pause
  exit /b 2
)
"jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -Drwagent.port=47653 -cp "rw-agent-bootstrap.jar" io.rwagent.client.ControlLoop record %*
set "rw_exit=%errorlevel%"
echo.
echo Reports are saved in rw-agent-reports.
pause
exit /b %rw_exit%
