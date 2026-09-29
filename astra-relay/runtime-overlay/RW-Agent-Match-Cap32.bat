@echo off
rem 输出19 §2 regression entry: run the Giant Island A/B baseline again at mobileUnitHardCap=32.
rem The development default is 40 now; this launcher only exists to reproduce the cap=32 control arm
rem (army>=32 heartbeat share 22.7%, lost 64) so 32/40 can be compared on equal footing.
rem Everything else stays frozen (activeArmyTarget=24, reserveTarget=8, mineTarget=3, landFactoryTarget=2,
rem unit scoring, production budget, builder recovery, single-army tactics).
setlocal
cd /d "%~dp0"
if not exist "jvm64\bin\java.exe" (
  echo Put these files in the original game folder beside game-lib.jar.
  pause
  exit /b 2
)
if "%~1"=="" (
  "jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -Drwagent.port=47653 -Drwagent.mobileUnitHardCap=32 -cp "rw-agent-bootstrap.jar" io.rwagent.client.MatchClient 900
) else (
  "jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -Drwagent.port=47653 -Drwagent.mobileUnitHardCap=32 -cp "rw-agent-bootstrap.jar" io.rwagent.client.MatchClient %*
)
set "rw_exit=%errorlevel%"
echo.
echo Reports are saved in rw-agent-reports. This run uses mobileUnitHardCap=32 (A/B control arm).
pause
exit /b %rw_exit%
