@echo off
rem 输出25 A/B isolation, arm A: the same candidate with the whole reachability sampling switched OFF.
rem This is the control arm for the LIVE_DIAGNOSTIC_SIDE_EFFECT investigation: if units no longer vanish at
rem full HP here, and the default (group A only) arm is also clean, the side effect lives in the banned
rem engine-call groups B-E. Nothing else differs from RW-Agent-Match.bat.
setlocal
cd /d "%~dp0"
if not exist "jvm64\bin\java.exe" (
  echo Put these files in the original game folder beside game-lib.jar.
  pause
  exit /b 2
)
if "%~1"=="" (
  "jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -Drwagent.port=47653 -Drwagent.reachabilitySample=false -cp "rw-agent-bootstrap.jar" io.rwagent.client.MatchClient 900
) else (
  "jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -Drwagent.port=47653 -Drwagent.reachabilitySample=false -cp "rw-agent-bootstrap.jar" io.rwagent.client.MatchClient %*
)
set "rw_exit=%errorlevel%"
echo.
echo Reports are saved in rw-agent-reports. This run has reachability sampling DISABLED (control arm).
pause
exit /b %rw_exit%
