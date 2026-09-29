# Astra 项目总评任务｜2026-09-29

这一次**只做架构/路线评审，不改代码，不生成补丁**。上一轮公开中转审计的交接和 patch 已放在：
`astra-relay/previous-astra-review-2026-09-29/`。

先读：`ASTRA_START_HERE.md`、`project-state/CURRENT_STATE.md`、`project-state/HANDOFF_FOR_CODEX.md`、`docs/ROADMAP_CN.md`、最新 KnowledgeBacked 交接，再参考上一轮 Astra 交接。

## 1. 项目最终目标

最终目标不是把当前规则脚本继续堆大，而是在**原版 Rusted Warfare PC 1.15** 上形成一个可长期演进的竞技 Agent 系统：

- 固定并可复现的 1v1 规则、地图池和版本基线；
- 严格遵守战争迷雾，只用合法可见/合法记忆的信息；
- APM、反应速度、控制权限等公平约束可记录、可复核；
- 能从裸开局独立完成经济、侦察、战斗、恢复和结算；
- 在九图与不同开局上具备稳定竞技能力，先明显强于原版 AI，再进入真人基线测试；
- 能把真人 replay 转成合法 observation/action 数据，并进行学习与留出评估；
- 能进行 Agent-vs-Agent 自我博弈、历史对手池与批量实验；
- 最终形成“数据/回放学习 + 自我博弈 + 评测 + 迭代”的持续改进闭环，而不是只能人工加规则。

工程里程碑仍沿用 0.1 / 0.3 / 0.5 / 0.7 / 0.8 / 0.9 的能力含义；版本号 0.07 不表示完成度 7%。`r`n`r`n## 2. 当前已经具备的主要能力

不要把以下已完成工作重新列为“下一步从零实现”：

- 裸开局 → 建矿/建厂/升级/生产/寻敌/进攻/补兵 → 原生胜负结算的完整原型；
- Builder recovery、投资预留、动态陆厂、长期经济基础、5x speed readiness；
- NO_PROGRESS_TARGET_HANDLING，避免长期压不可伤/无进展目标；
- World Model：合法可见敌情历史、LOST_CONTACT/CLEARED；
- Recon v0.2：RECHECK_INTEL、FRONTIER_SWEEP、残血单位转 expendable scout、主力保护；
- KnowledgeBacked v0：Target Compatibility Guard、Terrain Semantic Memory、movement-aware Recon；
- 两实例隔离 headless runner、crash recovery、固定条件 A/B、可续跑 campaign；
- 报告、证据分级、谱系、合法视野与危险反射保护已有较成熟工程纪律。

当前候选仍是 KnowledgeBacked v0；上一轮 Astra 的 public-relay patch **尚未并入用户本地主工程**，因此评审时请把它视为待吸收的工程修复，不要假设已经部署。

## 3. 待实现/待收尾功能预设：20 项

计数口径：只算对最终 Agent 能力或训练闭环有独立意义的预设；纯日志字段、审计、单个测试缺项不计。若你认为应合并/删除/拆分，请明确提出。

### A. 对局行为与世界模型（14项）
1. Recon 计划→执行活性收尾：解决 route anchor drift、到点未合法刷新等失效率。
2. Enemy Motion Belief v0：失联移动敌军的时效位置/方向/可能区域推断。
3. Threat + Vision Field：把可见威胁、记忆威胁、视野覆盖与过期统一成可查询场。
4. TARGET_REACHABILITY_GUARD：在安全语义成立后判断“能否真正接战”，而不只判断能否造成伤害。
5. Cross-Domain Recovery：陆/水/空/跨海目标导致主力卡死时的恢复策略。
6. LOW_VALUE_TARGET_PURSUIT_LIMIT：限制低价值目标把主力长期拖走。
7. Unit Role / Task Group Manager：MAIN_FORCE / LOCAL_GUARD / PURSUIT / WOUNDED_RESERVE / BUILDER_ESCORT 等多任务编组。
8. 修理/重建：战损建筑、受损单位、关键生产设施的恢复逻辑。`r`n9. 越基线经济损失再评估：矿/厂被摧毁后，压力解除时重新判断当前最佳投资。
10. 经济资本配置补全：矿/厂升级、intentional banking、机会成本与更完整的 Budget/Investment Manager。
11. Builder scaling：是否/何时增加第二建造者及后续建造能力扩容。
12. Dynamic army cap / map-scale adaptation：军力上限随地图规模、敌人数和经济能力调整，而不是固定 40。
13. Scout lifecycle：专职侦察单位的生产、补充、替换与必要时的高风险 probe，而不是长期依赖残血坦克。
14. 海军/登陆/跨域作战：真正处理水域、岛屿、登陆和不同 movement domain 的战略调度。

### B. 自对弈与自动化基础设施（3项）
15. 4-instance 或更高并发扩展：在两实例已稳定的基础上扩规模，同时保留隔离、回收和证据身份。
16. 限制规则 same-game self-play：Agent-0 vs Agent-1 在同一局、固定地图/位置/设置/单位规则下真正对弈。
17. 长期自对弈编排：自动复位、批量运行、历史对手池、故障恢复、参数/策略版本对比。

### C. 学习与竞技评估（3项）
18. 真人 Replay → 合法 observation/action 数据管线：解析、时间对齐、迷雾过滤、动作归属、训练/留出拆分。
19. 学习闭环：先允许离线模仿/策略参数学习，再与 self-play/A-B 结合，证明统计上持续改进，而不是“多跑几百局规则就会自己学”。
20. 竞技泛化评测：九图、多开局、多风格真人/AI基线、公平 APM/反应约束，形成 0.5→0.8 的可复算门槛。

不计入20项但仍是工程前置/技术债：Diagnostic Lab 的 Reachability B/C/D/E、祖先 owner override 风险、capability air/ground 映射等。它们只有在对应主线需要时才应投入，避免再次让诊断工作吞掉产品推进。`r`n`r`n## 4. 最新桌面实机反馈（尚未完全写入公开 CURRENT_STATE）

用户已用当前候选在多张全新地图打了多局；最后一局为 10 人超大地图、1800 游戏秒压力测试。可从 `evidence/last_1800_spain_10p.html` 复盘 Agent 合法视角。

这局的重要现象：
- 400×370 格，1800s，ONGOING；Agent 仍持续经济、生产、进攻，没有卡死。
- 新战斗单位 77，己方损失 50，确认进攻命令 209；完成约 11 座矿、2 座陆厂。
- 结束时约 40 个战斗单位，但余额已堆到约 119,958；固定 mobile cap=40 在这种尺度上明显成为经济转军力的瓶颈信号。
- Target Compatibility Guard 在实机中大量自然触发；混编对空时 heavyTank 保留、c_tank 排除，整体方向符合设计。
- Recon 40 个任务中 34 个 FRONTIER_SWEEP，但大量被阻断；31 个 frontier 阻断里 26 个是 `ROUTE_ANCHOR_DRIFT_BEFORE_ASSIGNMENT`。这表明当前更像“会规划但计划到执行衔接不稳”，不是 Terrain 语义本身失效。

请把这些当作**问题信号，不当作因果性能证明**。

## 5. 请你做的项目评价

请站在外部资深 RTS Agent / AI 系统工程负责人视角，直接评价当前项目，不要迎合我们的既有路线，也不要现在动手施工。

请回答：
1. 以最终目标为尺度，当前架构最强的地方和最危险的地方分别是什么？
2. 当前规则型 Agent 路线还能健康推进到哪个阶段？从哪一步开始必须为 learned policy / replay learning 重构接口？
3. `BattleClient` 逐渐承载大量策略逻辑，这种形态是否已接近架构拐点？如果是，建议怎样拆边界，但不要为了“整洁”做无收益重构。
4. 上述20项中，哪些应合并、删除、推迟或提前？请给出你的理由，而不是照单全收。
5. 如果未来只有有限的高能力工程模型额度，最值得把额度花在哪些问题上？哪些工作普通模型/脚本/用户实机即可完成？
6. 现阶段应该更优先“补齐更多对局能力”，还是“尽快打通 same-game self-play / replay-learning 基础设施”？请说明转折条件。
7. 给出你认为最关键的 5 个结构性风险，以及每个风险最小的缓解方式。
8. 用 0.1 / 0.3 / 0.5 / 0.7 / 0.8 / 0.9 的原始含义逐段评价当前证据到哪里；不要把版本号当成熟度，也不要给虚构完成百分比。

最后请输出一个**建议路线图草案**，但只到“阶段/依赖/验收标准”的粒度，不写具体补丁。下一轮我们会由 ChatGPT 参考你的意见，再给你正式工作方案。