@echo off
setlocal
cd /d "%~dp0"

echo Starting Rusted Warfare 1.15 with RW Agent 0.07-alpha1...
echo The Agent API is local-only: http://127.0.0.1:47653

"jvm64\bin\java.exe" --add-modules jdk.httpserver -Xmx1000M -Dfile.encoding=UTF-8 -Drwagent.port=47653 -Drwagent.allowCommands=true "-javaagent:%~dp0rw-agent-bootstrap.jar" -Djava.library.path=. -cp "game-lib.jar;libs/*" com.corrodinggames.rts.java.Main -width 1280 -height 720 -log rw-agent-game.log

echo.
echo Rusted Warfare has exited. Check rw-agent-bootstrap.log and rw-agent-game.log if needed.
pause
