@echo off
setlocal
cd /d "%~dp0"
echo This test only works in a loaded local single-player match.
"jvm64\bin\java.exe" -Dfile.encoding=UTF-8 -Drwagent.port=47653 -cp "rw-agent-bootstrap.jar" io.rwagent.client.AgentClient move-first
pause
