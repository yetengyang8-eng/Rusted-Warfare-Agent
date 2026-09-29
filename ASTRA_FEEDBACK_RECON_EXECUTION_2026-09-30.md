# Astra Recon Execution 桌面验收反馈｜2026-09-30

本文件是对 `astra/recon-execution-20260929` / 候选 JAR `feb7ad39...` 的用户桌面原版 1.15 验收反馈。
完整证据在 `evidence/astra-recon-desktop-acceptance-2026-09-29/`，其中包含 raw reports ZIP、机器汇总、Recon 关键事件、两个 Agent-POV HTML 及实际浏览器渲染截图。

## 核心结论

你的 Recon Execution 方向已经得到一局很强的自然 E4 正证据，建议保留这一机制，不要回到“放宽 anchor drift 容差”的旧思路，也不需要为了证明自己而重写成功部分。

1x Big Island、正常迷雾、900s 参数局在约 664 game s 原生 VICTORY：
- 9 个 `FRONTIER_SWEEP`，0 blocked，0 `ROUTE_ANCHOR_DRIFT_BEFORE_ASSIGNMENT`；
- 11 个 Recon move 全部具有执行证据：10 `ACTIVE_ORDER` + 1 `ARRIVED_AFTER_ACCEPTANCE`；
- 7 个 strict `recon_frontier_refreshed`，1 个独立 `recon_frontier_satisfied`，1 个 defense preemption；
- `recon_frontier_satisfied` 正确保持非严格因果语义，没有冒充 refreshed；
- 0 `command_rejected`；主战链正常，137/137 attack orders confirmed。

这已经覆盖了本轮最重要的自然链：ownership → takeover/plan → native receipt → execution witness → legal visibility outcome。

## 5x 长局的边界

5x Big Island、1800s 参数局完整跑到约 1802 game s，Battle 为合法 `PARTIAL / ONGOING`，没有引擎崩溃、报告损坏或 command rejection。
但它没有创建 FRONTIER_SWEEP，因此**不能**算 5x frontier 执行闭环已验收。

这局有两个值得你判断是否需要小修后再转向下一突破的问题：
- 两次 frontier plan 都以 `SEARCH_BLOCKED_BY_KNOWN_THREATS` 结束，BFS `reachableTiles=1 / dangerBlockedTiles=4`；
- 两次 takeover 候选在约 2.6 game s 后 `SCOUT_LOST`。请判断这是合理的高压战场结果，还是 threat/scout selection 过度保守或过于脆弱。

不要为了这一局机械调参数；只有证据显示它是稳定系统性阻塞时才值得改。

## 一个小的 ownership 记账异常

1x 局 taskId=2 / unit411 只出现一次 `task_ownership_acquired`，随后却出现两次 release：
1. `DEFENSE_PREEMPTION` at 409858 game ms；
2. `LEGAL_VISIBLE_CONTACT` at 410894 game ms。

从源码看，non-frontier `releaseReconActor()` 会先 `releaseOwnership()` 但保留 ReconTask，之后 `closeRecon()` 又会再次 release；CommandArbiter 的 release 本身是幂等的，所以当前没有观察到命令冲突，但事件记账不是一 acquire 对一 release。请判断是否值得让 release 只在真实持有时记录，以便未来 PlayerContext / task ownership 审计更干净。

## 我额外做的工程验收

- 两份桌面 battle 都通过 `analyze_reports.py`，整组 6 个 economy/development/battle 报告为 5 PASS + 1 合法 PARTIAL，分析器 exit=0。
- `RW-Agent-Collect-Reports` 产物完整，raw JSONL、reports.json/md、metadata 和 SHA sums 都在 bundle 中。
- 两局均已转换为离线 Agent-POV HTML；我用 Edge headless 实际加载并截图，确认不是只生成了文件而是可正常渲染。
- 从当前 main fresh Windows worktree 集成你的改动后，完整 `test-win.ps1` 最终 **20 Java harness + 15 Python suites / 0 failed steps**，约 453 s；详见 evidence 中 `full-regression.md`。

全回归过程中还发现一个 Git 中转站可复现性缺口：Windows `core.autocrlf=true` 会把 Unit Catalog 从 LF 改为 CRLF，从而改变你我都用于身份判断的 SHA。main 已用 `.gitattributes` 将该资源固定为 LF。用户桌面已测的 `feb7ad39...` overlay 本来就继承正确资源，没有受这个 fresh-clone 问题影响。

## 建议下一步（你仍有较大自主权）

把这轮 Recon Execution 视为已取得一个实质突破，而不是继续无限收尾。先审计上面的 double-release 与 5x threat/scout 现象：如果只是低风险记账/合理战场结果，做最小处理或记录后直接进入你此前提出的 **PlayerContext / same-game 双玩家最小实验**；如果你发现它们揭示更深的 command ownership 缺陷，再在进入双玩家前修根因。

不要为了追求单局胜率去调整经济/军力 cap；5x 局末 18 万资金与长期军力规模问题是另一个战略层课题。

请优先消费最新 main 和 `evidence/astra-recon-desktop-acceptance-2026-09-29/`。下一轮如果可以直接写 GitHub，请从最新 main 建自己的 `astra/...` 分支并 push；否则继续交付 patch/JAR/evidence。
