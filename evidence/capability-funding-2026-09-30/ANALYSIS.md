# Capability funding：Spain A/B 的已证资金断链

本次只读分析冻结 Global Strategy JAR `76711a8e…` 的两份历史桌面原始 Battle；不是新候选运行结果。两局均为 PARTIAL/ONGOING，且均在 Match 前人工生产一个 builder。时间均为 Battle 已运行游戏秒。

| 报告 | 文件 | raw SHA256 |
| --- | --- | --- |
| Spain A | `battle-1790742800972-4064cebd.jsonl` | `77e0b142246be8d4fe49f59dbab3cbae890a0071530c22e3f3961a3dae013bf8` |
| Spain B | `battle-1790743783098-ae01b65e.jsonl` | `a5a3e05c157b381616837b37a88b29af1dbb597137cc37ad870fa862d4ee1026` |

原始包与运行条件见 [桌面证据](../global-strategy-desktop-2026-09-30/README.md) 及其 `bundle_index.json`。机器可读结果为 [analysis-A.json](analysis-A.json)、[analysis-B.json](analysis-B.json)；每份保存实际 raw SHA、行号、完整需求生命周期和门槛窗口。

## 五项发现

1. **A 没有采样到买工程师的现金门槛。**全场 867 次 observation 最高 credits=3015（A 行 1499，+370.215s）；活跃需求期间最高 2141.5（行 397，+107.055s）。工程师真实菜单 cost=3500。A 并非已有充足资金却单纯错过空队列。
2. **A 的策略曾反复尝试评估。**137 次 `strategy_production_menu` 全有工程师动作，137 次 affordable=false；93 次至少一座工程师工厂空队列。首次菜单在行 4311（+896.560s），首次空队列菜单在行 4371（+905.285s）。B 对应 60 次菜单、30 次工程师可负担、9 次空队列且可负担。不能把 A 的零投资统一归因为“没有 T2”“工厂始终繁忙”或“策略从未获得时隙”。
3. **A 有长时间满足部分储蓄门槛的观察区间。**活跃需求、force≥6、基地 500 范围无可见武装威胁、至少一座已完成 T2 陆厂同时成立的最长区间为 +1168.580–1820.225s（651.645s；行 5986–9694），force=6–16，起始钱806，普通出兵71次/39250。较早区间 +893.835–1132.395s（238.560s；行 4285–5788），force=6–17，起始钱197.5，普通出兵28次/15200。两段模型收入分别51.04–63.11与63.11/游戏秒。它们是连续采样门槛，不是停止出兵后仍安全的证明。
4. **留存普通出兵款存在账面预算空间。**严格只加上当前 observation 行之前的普通出兵支出：最长窗口起点后43.135s，行6245，881.5+2650=3531.5；较早窗口后53.010s，行4586，445+3100=3545。若额外示例性留700，分别在54.495s/64.400s达到4200。前一窗口 heartbeat 曾记录builder reserve=500和待建矿；后一窗口采样到builder reserve=0、无buildPending。这不证明精确硬储备始终如此。
5. **需求去向与真正的投资时点可定位。**A 前两个空中需求 #749/#710 在行152/214建立，于行390/409因新的当前编队接近证据释放；之后六个潜艇需求见下表，预算终点仍未响应。B 最早战略投资是行2162（+548.925s）的额外builder，free=883.5、cost=500；首次工程师是行4500（+993.765s），force=73、free=8454、cost=3500。本轮只针对已证资金断链；更早战损与两局整体分化的根因仍未知。

| A 后续需求 | 创建行 | 创建时间 | 预算终点 |
| --- | ---: | ---: | --- |
| #21909 lightSub | 4271 | +890.945s | OPEN，无战略响应任务 |
| #23200 attackSubmarine | 4626 | +955.345s | 同上 |
| #23447 lightSub | 4629 | +955.345s | 同上 |
| #22210 lightSub | 4673 | +961.145s | 同上 |
| #22755 attackSubmarine | 4678 | +961.145s | 同上 |
| #25248 lightSub | 5168 | +1037.840s | 同上 |

## 复算

在仓库根运行；report 指向从既有原始包取得的文件，也可指向本机原始报告。工具流式读取，不修改输入，不运行游戏。

```text
python tools/analyze_capability_funding.py --report PATH_TO_SPAIN_A.jsonl --out evidence/capability-funding-2026-09-30/analysis-A.json
python tools/analyze_capability_funding.py --report PATH_TO_SPAIN_B.jsonl --out evidence/capability-funding-2026-09-30/analysis-B.json
```

## 结论边界

- 窗口只重建部分可观测门槛，不等同 `strategy.act` 的完整准入；没有完整重建响应者分配、尝试上限、在途购买、共享命令时隙与8秒冷却。A 没有战略任务分配/工程师投资，因此其需求无人响应；不能把这个事实套到 B。
- heartbeat 的 builderReserve/buildPending 是不完整采样；700 是单列的示例缓冲，不是从日志证明的精确硬储备。模型收入不冒充新实测收入。
- 留存上界固定原轨迹的收入和损失，把此前普通出兵款加回余额。真正停产会改变后续单位、损失、收入和军力门槛，因此这不是反事实对局、胜率证据或实施后验收。
- 最低force触及6，储蓄方案仍需可取消、有时限，继续保护既有恢复/施工资金，并由新候选测试决定是否能成活。本分析未授权放宽既有安全门槛。
- 原局0与52是全部战略投资订单口径；22条B `strategy_allocation`加30条战略施工形成52次投资，不能将52误称52次工程师购买。
