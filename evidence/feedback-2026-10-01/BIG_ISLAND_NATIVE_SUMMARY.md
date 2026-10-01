# big-island 隔离原生证据（2026-10-01）

原始报告 `G:\deepseek 工作台\_validation\feedback-20261001\native-big-island\run-20261001T075134-711ad7\episode-001\rw-agent-reports\battle-1790841115932-ef9fa504.jsonl`，SHA-256 `9906324b5a3fd763070c076b993814bc89a9ab13c9e968bbf6afa13c8b2b33cb`，55637671 bytes / 11216 行。原始文件未修改。候选 JAR `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`。

本局 `PARTIAL/ONGOING`，对战游戏时间 1802.160 秒；报告完成表示达到运行预算，不表示整局结算或胜利。运行设置依据 `runtime.json`：map=maps/skirmish/[p2]Big Island (2p).tmx，AI difficulty=1，requested speed=5.0，seed=819463964，seed 可复现性未验证。无需推断旧桌面局未知设置。

| 自然行为 | 实际计数 / 边界 |
| --- | --- |
| T2 / T3 selected | 5 / 2 |
| T2 / T3 observed ready | 5 / 2 |
| 危机开始 / 接受响应 / 接受撤回 | 7 / 9 / 6 |
| 响应小队人数 | {'minimum': 2, 'maximum': 2} |
| provider jet 下单 / ready / need 转交 | 4 / 4 / 4 |
| Dive accepted / current compatible observed / jet response | 3 / 3 / 3 |
| 同时 cohort 上限 / cohort accepted order / fairness grant | 4 / 149 / 49 |
| 诊断总数 / 有 accepted age 样本 | 284 / 245 |
| 最长诊断 accepted age / 空订单样本 age（秒） | 275.264 / 275.264 |
| 己方观察损失 / 末现金 | 96 / 61678.0 |

五项审计分别保存在 `suite-final.json`，四报告 feedback/operations/global/specialist 均检查各自约束；parser 为前三阶段 PASS、battle PARTIAL，无解析完整性异常。Operations 的新矿 deferral 仅检查 schema，明确把回本/安静窗/预留校验委托给 feedback audit；该局反馈审计的 triggered branches 没有 missing evidence。未触发分支不作为自然通过。

计数为零或 null 的实际含义：没有本队 accepted order 时 `lastAcceptedAgeMs` 可以为 null；初始化零不等于实测零空闲。单 cohort 不能证明多军队公平改善。隐藏潜艇目标返回 UNKNOWN/TARGET_NOT_CURRENTLY_VISIBLE 时不 Dive/attack 是观察边界，不能宣称自然反潜全链通过。相应完整链和 T3/fairness 见 `full-http-audit/SUMMARY.json` 的具体 fixture。

所有价格来自当局 native menu，回本属于披露的收入估计及预算窗；不预测未来存活。损失事件不归因到具体敌人。末现金和旧桌面局没有相同队伍/设置控制，不能声称现金或胜率因果改善。

最长 275.264 秒 diagnostic age 的解释已经核实：cohort6 的 local order 行6790@1234944到行8612@1519328，相隔284.384秒；但行6844@1241664进入 local_army_mode=false，行8473@1501072才重新active。期间仍有20条rule-main attack与27条queue。最大诊断行8546@1510208时30成员中仅4无订单且远离目标，目标366 COMPATIBLE、frontier planned，global gate由recon:9 move占用；距离重新active仅9.136秒。因此此值不是整队275秒没有行动，也不能归因公平机制失败。下一阶段统计应切分active-mode窗口，并计入main-controller实际命令及新补兵加入时间。精确字段见metrics-final.json.longestDiagnosticAgeInterpretation。
