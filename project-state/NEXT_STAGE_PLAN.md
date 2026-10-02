# Next Stage Plan

Updated: 2026-10-02 after Octopus G2. G2 已交付；下列是后续接口规划，不是本轮继续施工授权。

## 推荐下一断点：G3

先读 [G2 交接](../handoff/HANDOFF_Octopus_G2_2026-10-02.md)与 [WorldState/Event 契约](../docs/OCTOPUS_G2_WORLD_STATE.md)。输入是合法 Observation + 原响应，输出是不可变 WorldState、范围覆盖/新鲜度和窄语义事件。事件 occurredAtGameTimeMs 未知；WorldState epoch 不是 owner generation。旧命令路径仍独立工作。

建议 G3 先迁移现有 lane 的控制权与执行准入，保持既有战争策略。顺序：显式 owner generation 与过期 Intent 拒绝；影子收集现有命令；校准 actor 冲突、lease/commitment；再逐条切换执行。迁移需要同时审查 command gate 和上游 produce/main caller 的 early return，不能只放宽 gate。

若 G3 任务授权调度/吞吐，再建立以明确时钟计量的有界预算、确定性排序、批内 effectiveCredits/slot 及真实 native receipt；已付款但队列未出现的 ghost 占位必须保留。任何 WorldState UNKNOWN、失效/断档来源、失联敌或 queue empty 都不能变成额外可用资金、空闲 slot、确认击杀或产品交付。

验收建议包括：旧 generation 拒绝、同 actor 冲突、跨 owner 撤销、lease 到期、拒绝/失败后的预算与占位、paid-before-queue-visible、不同 endpoint 覆盖、断档/重置后的执行安全；影子期先与旧命令输出等价，切换后明确列出授权的行为差异。新调度不得把 G2 事件当作原生同步总线。

## 继续后置的工程层

G3.5 可先迁移已有工程师 → amphibiousJet → Dive/Fly 链，保留原 funding、Purchase/paidConstruction、first-match 歧义和合法模式/路径证据。G2 的 ready/类型标签不提供生产血缘或 Specialist Pool 匹配。跨水/水下任务完成、严格 Pool 生命周期仍需单独证明。

G4 的 General/FREE/Join，G5 的 Combat/Crisis/ThreatTask，G6 的新增 Capability 搜索/规模化均需独立任务，不从本计划自动取得授权。确认敌损和施伤归因仍为 NEEDS_EVIDENCE；不要套用旧 HP/cohort 常量擅自设计新战争策略。

## 验证与成本边界

G2 的完整矩阵 60/60、448 Python tests、73 core/14 independent 已通过。四代表路径的 G2 on/off 日志字节比为 2.759028、2.310343、2.272116、1.828950，双方 G1 Trace 都开启；仅为导出体积。自然局、实际 CPU/延迟/轮询成本尚未验收。后续如授权性能优化，先测量拷贝/差分/JSON/flush，再在不改事实契约的前提下压缩或控制导出；不要更改战术 gate 来掩盖成本。

冻结 ff693c8 已复现第二次 state guard 时 report commit NPE；这是原有报告健壮性问题，可单独安排小任务，不与调度迁移混做。初版失败与 superseded full 已归档，不能当成最终绿灯证据。

后续子智能体优先 `gpt-6.1-sol / high`，不使用 Astra。
