# spain 隔离原生证据（2026-10-01）

原始报告 `G:\deepseek 工作台\_validation\feedback-20261001\native-spain\run-20261001T073550-151146\episode-001\rw-agent-reports\battle-1790840173749-2178e924.jsonl`，SHA-256 `9cead6aca2db590cfd4c2ff476e6cf4c0a3bcb1787b2bd36f65f9f342beb746a`，72662935 bytes / 16861 行。原始文件未修改。候选 JAR `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`。

本局 `PARTIAL/ONGOING`，对战游戏时间 2401.328 秒；报告完成表示达到运行预算，不表示整局结算或胜利。运行设置依据 `runtime.json`：map=maps/[10p] 10p 西班牙混战_by_MP97.tmx，AI difficulty=1，requested speed=5.0，seed=310313847，seed 可复现性未验证。无需推断旧桌面局未知设置。

| 自然行为 | 实际计数 / 边界 |
| --- | --- |
| T2 / T3 selected | 2 / 0 |
| T2 / T3 observed ready | 2 / 0 |
| 危机开始 / 接受响应 / 接受撤回 | 24 / 30 / 24 |
| 响应小队人数 | {'minimum': 2, 'maximum': 5} |
| provider jet 下单 / ready / need 转交 | 6 / 6 / 6 |
| Dive accepted / current compatible observed / jet response | 0 / 0 / 0 |
| 同时 cohort 上限 / cohort accepted order / fairness grant | 1 / 0 / 0 |
| 诊断总数 / 有 accepted age 样本 | 160 / 0 |
| 最长诊断 accepted age / 空订单样本 age（秒） | None / None |
| 己方观察损失 / 末现金 | 208 / 580.0 |

五项审计分别保存在 `suite-final.json`，四报告 feedback/operations/global/specialist 均检查各自约束；parser 为前三阶段 PASS、battle PARTIAL，无解析完整性异常。Operations 的新矿 deferral 仅检查 schema，明确把回本/安静窗/预留校验委托给 feedback audit；该局反馈审计的 triggered branches 没有 missing evidence。未触发分支不作为自然通过。

计数为零或 null 的实际含义：没有本队 accepted order 时 `lastAcceptedAgeMs` 可以为 null；初始化零不等于实测零空闲。单 cohort 不能证明多军队公平改善。隐藏潜艇目标返回 UNKNOWN/TARGET_NOT_CURRENTLY_VISIBLE 时不 Dive/attack 是观察边界，不能宣称自然反潜全链通过。相应完整链和 T3/fairness 见 `full-http-audit/SUMMARY.json` 的具体 fixture。

所有价格来自当局 native menu，回本属于披露的收入估计及预算窗；不预测未来存活。损失事件不归因到具体敌人。末现金和旧桌面局没有相同队伍/设置控制，不能声称现金或胜率因果改善。

原始首次strictparser的18条 receipt mismatch/noIntent已保留在parsed.json；首例RETURN行2204 intent/2205 action Y=6291.5715，而行2206 native receipt Float32→3位字符串为6291.571，requestId=20432e57-ca6b-4d5e-970b-9b2f7a121114，actors5775/2699一致。parser随后以原生Float32+3位输出规则严格核对，parsed-final/reports.json和suite-final.json均无issues；未宽放任意坐标，也未修改raw。feedback.json初始22 omitted-owner gaps、operations.json初始137旧schema误报同样保留，final以明确Recon旧owner与新payback schema契约复核。

六个ready支援jet只完成provider转交；前四个13858/14935/15358/23845在首次response行3855/4111/4235/6629都targetVisible=false，native actor compatibility/status=UNKNOWN、reason=TARGET_NOT_CURRENTLY_VISIBLE。相应UNKNOWN查询计数253/237/166/239，未用隐藏目标推断可反潜或允许开火。后两个60397/64924在局末转交，没有这类response query证据。
