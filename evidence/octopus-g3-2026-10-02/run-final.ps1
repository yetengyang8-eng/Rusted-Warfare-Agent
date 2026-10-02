$v = $PSScriptRoot
$source = Join-Path $v 'final-source'
$evidence = Join-Path $v 'final-full'
$game = 'G:\deepseek 工作台\游戏环境\P1F-GPTSol61-ProductionCapacity-2026-10-02'
$env:JAVA_HOME = Join-Path $game 'jvm64'
$pyDir = 'C:\Users\Administrator\.cache\codex-runtimes\codex-primary-runtime\dependencies\python'
$env:PATH = "$env:JAVA_HOME\bin;$pyDir;$env:PATH"
$env:TEMP = Join-Path $v 'temp'
$env:TMP = $env:TEMP
$env:PYTHONIOENCODING = 'utf-8'
$env:RW_G1_BASELINE_JAR = 'G:\deepseek 工作台\GitHub发布\Rusted-Warfare-Agent\deliveries\production-capacity-2026-10-01\rw-agent-bootstrap.jar'
$env:RW_G1_EVIDENCE_DIR = Join-Path $evidence 'g1-four-path'
$env:RW_G2_BASELINE_JAR = 'G:\deepseek 工作台\_validation\octopus-g2-20261002\baseline-frozen\rw-agent-bootstrap.jar'
$env:RW_G2_EVIDENCE_DIR = Join-Path $evidence 'g2-four-path'
$env:RW_G3_BASELINE_JAR = 'G:\deepseek 工作台\_validation\octopus-g2-20261002\fixed-candidate\rw-agent-bootstrap.jar'
$env:RW_G3_EVIDENCE_DIR = Join-Path $evidence 'g3-enabled'
Set-Location -LiteralPath $source
Start-Transcript -LiteralPath (Join-Path $evidence 'transcript.txt') -Force
& (Join-Path $source 'agent\test-win.ps1') -GameJar (Join-Path $game 'game-lib.jar') -LibsDir (Join-Path $game 'libs')
$code = $LASTEXITCODE
Stop-Transcript
Set-Content -LiteralPath (Join-Path $evidence 'exit-code.txt') -Value $code -Encoding utf8
exit $code
