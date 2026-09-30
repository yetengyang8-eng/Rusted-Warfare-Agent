# 专属单位生命周期：本轮输入分析

2026-09-30；源码入口为最新 capability-funding 增量。这里的旧样本是**新政策的负对照**，不改写旧版合法视野/ownership 审计结论，也不是控制全部变量的 A/B。

三份原始报告均在 `G:\deepseek 工作台\游戏环境\P1F-Astra-CapabilityFunding-2026-09-30\rw-agent-reports`。流式 SHA、完整逐单位记录、事件行号、原始 summary 和来源身份见 [input-summary.json](input-summary.json)。复算入口是 [audit_specialist_lifecycle.py](../../tools/audit_specialist_lifecycle.py)。

| 输入 | 原始文件 | SHA256 |
| --- | --- | --- |
| Run 1 | battle-1790757006698-4a8d52d7.jsonl | cedbeda1eb0c65f28a2f08c9dd3c565df3ea9087a4c9bd778be3a23a74fcc2bf |
| Run 2 | battle-1790757300274-44abfe25.jsonl | dce31552573039866c48b5d833e5d5e979de7333c990e6bc72a4775bd3a3d16c |
| Run 3（原有 run1/run2 汇总未覆盖） | battle-1790765936935-dbcd93c4.jsonl | 2074f5c5c712ad197d9b15548b60c73f04ebd8f475b702fd9a34b42c9eea4ef6 |

三局 raw 自证 JAR `41c52392d0d9cc7af39b4e726d6bab2537249ca146b5621d890fd2a8851a2141`、冻结引擎 `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`、Java 13、poll=500 ms。Run 1/2 地图尺寸 180×180、team:0；Run 3 尺寸 400×370、team:1。实测游戏秒/墙钟秒为 5.00 / 5.00 / 4.99。原始报告没有 mapPath、difficulty、seed、请求倍速和 Match 前人工操作字段，全部保持 UNKNOWN，不能从尺寸或实测倍速补猜。三局均预算到期 PARTIAL/ONGOING，Battle 游戏秒为 1200.160 / 2402.110 / 2401.820。

## 类型和损失核对

| 指标（所有工程师均为 combatEngineer） | Run 1 | Run 2 | Run 3 |
| --- | ---: | ---: | ---: |
| 唯一工程师观察数 | 0 | 14 | 3 |
| 工程师 own_loss 事件 / 唯一单位数 | 0 | 12 / 12 | 1 / 1 |
| 接到 RESPONSE 的工程师数 / 总分配次数 | 0 / 0 | 3 / 5 | 3 / 4 |
| 从未接 RESPONSE 的工程师数 | 0 | 11 | 0 |
| 工程师 PROSPECT 命令 | 0 | 20 | 14 |
| 工程师普通矿施工 extractorT1 | 0 | 8 | 8 |
| 工程师 landFactory 施工 | 0 | 0 | 0 |
| 工程师 heavyTank 施工 | 0 | 10 | 15 |
| 响应到接近点后相同坐标重复命令 | 0 | 12 | 18 |
| 其中明确失联且超过调查切换宽限的新政策反例 | 0 | 8 | 14 |
| RETURN 状态已在基地半径内仍回家命令 | 0 | 2 | 32 |

Run 2 **不是 14 名工程师死亡**：12 个工程师 own_loss 在 raw 行 6280、7351、8185、9214、10373、10691、11702、11940、12575、13133、13658、13728。#11092/#11183 在末次观察行 14940 仍存在，HP 为 104.490 / 113.795。只有 #3055（3 次）、#3490（1 次）、#4612（1 次）接到响应；其余 11 名未接响应而参与普通勘探/施工。`own_loss` 表示己方可观察状态中的单位损失；不能据此断言敌方击杀方式或将损失因果归于某一种任务。

Run 3 的 #17427 于行 5420 记录 own_loss、行 5425 任务丢失；#20487/#22175 在末次观察行 12699 仍在，HP 为 1000 / 722.079。两者分别有 7 次 PROSPECT；#20487 施工 4 座矿、7 辆 heavyTank，#22175 施工 4 座矿、8 辆 heavyTank。施工事实指接受的 `strategy_construction_ordered`，不在此重复宣称全部成品及收益。

## 可复现的执行断点

Run 3 的 #20487 行 8585 接目标 #11213、接近点 (810,3830)。行 8725 已到 12.9 像素以内；行 8782 在 (813.879,3834.379)，距点 5.8、orderType=null。目标 HP 曾在行 8709 从 72 降到 27，随后失联。原地响应重复命令在行 8739 / 8808 / 8863 / 8939 / 8988，行 8993 被记 NO_OBSERVED_PROGRESS。

同一单位行 9209 再接同一目标，接近点 (810,3670)；行 9464 到 (810.529,3665.317)，距点 4.7。行 9487 / 9571 / 9636 / 9692 / 9764 再发相同原地命令，行 9785 再超时。#22175 对 #9561 也在行 8782 到点 6.4 像素后停止，行 8994 超时。这三次是“到点后目标失联”，不是无法走到接近点。

Run 2 的 #3490 在行 5635 到点 13.7 像素，行 6122 约 62.79 秒后超时；另外四次响应是低血中断，包含真实沿途/驻点受损，不应全部归为同一到点问题。旧原生 #2290/#3223 样本也有同型现象，但本轮输入统计只使用上面三份更新桌面 raw。

旧控制逻辑只把距离缩短或可见目标掉血视为进展；到点后的原生命令自然为 null，又触发周期性重发。`CombatBridge` 的 CLEARED 仅适用于建筑旧址；移动目标丢失接触必须保持 UNKNOWN，不能通过“失联即击杀”修复统计。因此本轮政策将抵达后的有限调查、真正运动停滞和合法需求解决分开，并将专业单位保留给明确能力需求。

RETURN 的正式计数只包括从事件重建为 RETURN、命令目的地等于观察到的基地、单位已经在 180 世界单位内的 move。Run 2 两条分别为 #3055 行 7787、#5044 行 8529。Run 3 为 #20487 一条（行 9185）及 builder #35688 的 31 条；后者末段 HP=21.036/170、距基地37.893不变。普通 PROSPECT 在基地附近的 move 已排除，不能把先前宽口径 near-home 数直接当 RETURN。

## 复算与判据

```text
python tools/audit_specialist_lifecycle.py <raw.jsonl> --out <audit.json>
python tools/audit_specialist_lifecycle.py <new-candidate-raw.jsonl> --out <audit.json> --enforce-policy
```

默认命令输出事实和新政策对照；仅 `--enforce-policy` 会因新政策不符而返回失败。报告格式损坏/缺 summary 始终失败。输出把 `reportIntegrityIssues`、`newPolicyViolations` 和旧合法视野审计明确分开：本轮新政策包括工程师不做普通 PROSPECT/矿/landFactory/heavyTank、调查中不发攻击移动、到家不反复下相同命令。到点重发先作为诊断统计；只有最新响应评估明确失联、最新 combat observation 未显示目标、连续采样到点至少12秒、首次失联评估超过10秒，才列为新政策反例。可见目标驻点作战和 UNKNOWN 保留诊断，不据此判违规；也不依赖旧代码不存在的 INVESTIGATE 自报事件才能检出旧缺陷。Run 3 的18条到点重发中，17条最新响应评估为失联、1条仍为可见；其中14条满足完整时序宽限判据。Run 1 的零违反同时也是零专项覆盖，不能算收益证据。

新事件统计支持 `strategy_worker_committed/commitment_released`、`strategy_purchase_committed`、`strategy_investigation_started/exhausted/contact_restored`、`strategy_support_construction/transferred`。审计跟踪绑定释放，但需求释放不等同 ownership 释放，仍在执行的支持 BUILD 也不能被记为完成。绑定事件只代表观察到的可用角色承接需求，不宣称单位的隐藏工厂血缘。原有 `audit_global_strategy.py` 仍负责合法视野/ownership 等契约，专项审计不能替代它。新候选原生结果另行记录；这里不承诺胜率或损失收益。
