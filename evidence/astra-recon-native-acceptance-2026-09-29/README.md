# Astra Recon Execution 原生预验收｜2026-09-29

对象：Astra 交付的 Recon Execution client overlay，JAR SHA256 `feb7ad39cb137bda1ef105f6d449bbf1c5875bd9132c0b91790165a824a68f81`。
基底：KnowledgeBacked v0，JAR SHA256 `5741e241ce8ec1b921f36ed3274087609d0430f9281c44f6f62c096de84c5f54`。
引擎：冻结 Rusted Warfare 1.15 `game-lib.jar` SHA256 `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`。

这组结果由本机真正的原版 headless 引擎产生，不是 Astra 的模拟 fixture；用于在用户桌面实机前先确认候选能够进入原生引擎并运行。

## 已完成
- 1x Small Island smoke / roundtrip：PASS；2 条 move，15 次观察，两段均原生到达。
- 5x Small Island smoke / roundtrip：PASS；2 条 move，6 次观察，两段均原生到达。
- 5x Big Island、difficulty 1、600 game s Match：原生流程完整运行到时间预算，matchOutcome=ONGOING；Battle 报告 integrity issues=[]。
- 600s Match 中：47 个新战斗单位、15 损失、116/116 攻击命令确认、4 座新矿、2 座新厂；Recon 任务 1 个，Recon move queued 2 / observed 2 / resolved-after-observed-move 1。

## 需要正确理解的 FAIL
`match-5x-600-batch.json` 的 episode status 为 FAIL，是 `run_headless.py` 仍把 Battle 的 `PARTIAL / ONGOING` 当作流程非 PASS；不是引擎崩溃、报告损坏或命令桥失败。该局 `engineExitCode=0`，Battle `issues=[]`，并正常达到约 600 game s。

## 仍待用户桌面 E4
这组 headless 预验收没有自然触发 FRONTIER_SWEEP（frontierTasksCreated=0），所以不能证明 26 次 `ROUTE_ANCHOR_DRIFT_BEFORE_ASSIGNMENT` 在桌面自然局已消失。用户仍应做 1x / 5x 正常迷雾实机，并保留所有原始报告。
