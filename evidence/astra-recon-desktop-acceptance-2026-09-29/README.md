# Astra Recon Execution 桌面实机验收｜2026-09-29

对象：`astra/recon-execution-20260929` 候选，部署 JAR SHA256 `feb7ad39cb137bda1ef105f6d449bbf1c5875bd9132c0b91790165a824a68f81`。
引擎：Rusted Warfare 1.15，`game-lib.jar` SHA256 `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`。
这两局由用户桌面原版游戏真实运行；属于 E4 行为证据，不是 HTTP fixture 或 headless 替代。

## 1x / Big Island / 900s 参数局
- 原始 battle SHA256：`469136cf52d8fcaef82d0d83f2a5d0dc578bf9c677a71207b813f6bf751bdb2d`。
- 实际约 663.874 game s 结束，原生 `VICTORY`；275 commands、1284 observations。
- 69 个新战斗单位、39 损失、137/137 attack orders confirmed。
- Recon tasks 11；其中 FRONTIER_SWEEP 9。
- Recon move queued 11；10 次 `ACTIVE_ORDER` 观察，1 次 `ARRIVED_AFTER_ACCEPTANCE`，所以 11/11 都有执行证据。
- FRONTIER_SWEEP：7 strict `recon_frontier_refreshed`、1 `recon_frontier_satisfied`、1 defense preemption、0 blocked。
- `ROUTE_ANCHOR_DRIFT_BEFORE_ASSIGNMENT`：0；整份 raw battle 中 `DRIFT` 字符串计数也是 0。
- `command_rejected`：0。

这局自然触发了 Astra 本轮最关键的链：任务所有权 → takeover/规划 → move receipt → 执行观察 → 合法视野结果。
`recon_frontier_satisfied` 那一例来自采样间完成移动，witness=`ARRIVED_AFTER_ACCEPTANCE`；系统没有把它伪报成 strict refreshed，而是记录 `TEAM_REFRESH_WITHOUT_STRICT_ACTOR_ORDERING`。

## 5x / Big Island / 1800s 参数局
- 原始 battle SHA256：`3d6ba9bd309e52eb55366509c46fedd780efcdb6658fd93e03b838028eb92cb3`。
- 达到约 1802 game s 时间预算，`PARTIAL / ONGOING`，报告完整有效。
- 409 commands、691 observations；55 个新战斗单位、21 损失、301 attack orders / 300 confirmed。
- FRONTIER_SWEEP 没有创建，因此这局不能作为 5x frontier 执行闭环的正例。
- 两次 frontier 规划均返回 `SEARCH_BLOCKED_BY_KNOWN_THREATS`，BFS reachableTiles=1；随后两次 takeover 的候选侦察单位都在约 2.6 game s 后 `SCOUT_LOST`。
- `ROUTE_ANCHOR_DRIFT_BEFORE_ASSIGNMENT`：0；`command_rejected`：0。
- 结尾约 181842.5 credits，12 ready mines；这再次暴露长期经济/军力规模瓶颈，但不是本轮 Recon 修复的直接失败证据。

## 一个待 Astra 判断的小异常
1x 局 taskId=2 只 acquire 一次，却出现两条 ownership release：先 `DEFENSE_PREEMPTION`，约 1.0 game s 后又 `LEGAL_VISIBLE_CONTACT`。这可能只是幂等清理/迟到事件，也可能意味着 release 记账还能收紧；未观察到由此造成的命令冲突或卡死。

## 附件
- `desktop-raw-reports-2runs.zip`：两次 Match 的 economy/development/battle 全部原始 JSONL + 汇总/哈希。
- `recon-events.jsonl`：从两份 battle 中抽出的 Recon/ownership/command 关键事件，便于远程快速审计。
- `desktop-summary.json`：机器可读汇总与原始 report SHA。
- `01_1x_900_BigIsland_VICTORY.html`、`02_5x_1800_BigIsland_ONGOING.html`：离线 Agent-POV 战术网页回放。
- 同名 `.png`：用 Edge headless 实际加载网页后的截图；两份 HTML 均已确认可渲染，而非只完成文本导出。

结论：1x 自然局给出了本轮 Recon Execution 机制的强 E4 正证据；5x 长局证明整体运行稳定，但没有自然覆盖 frontier 执行，因此不应把它包装成 5x frontier 已验收。
