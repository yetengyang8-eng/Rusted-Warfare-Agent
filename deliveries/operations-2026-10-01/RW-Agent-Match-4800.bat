@echo off
setlocal
cd /d "%~dp0"
if not exist "jvm64\bin\java.exe" (
  echo This launcher belongs beside the installed game and agent JAR.
  pause
  exit /b 2
)
if not exist "rw-agent-bootstrap.jar" (
  echo Missing rw-agent-bootstrap.jar in this folder.
  pause
  exit /b 2
)
echo Match budget: 4800 GAME seconds, after opening and development.
echo Wall safety: 3600 seconds. Use 2x or faster; 5x is about 16 minutes of battle.
echo Native victory or defeat ends the run early. Reports: rw-agent-reports.
"jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -Drwagent.port=47653 -Drwagent.battleSafetyGameSeconds=7200 -Drwagent.battleSafetyWallSeconds=3600 -cp "rw-agent-bootstrap.jar" io.rwagent.client.MatchClient 4800
set "rw_exit=%errorlevel%"
echo.
echo Finished with exit code %rw_exit%. Reports are saved in rw-agent-reports.
pause
exit /b %rw_exit%
