# Astra 突破任务｜Global Strategy / Feasibility / Combat Engineer｜2026-09-30

这是一轮高自主性任务。不要机械覆盖清单，也不要只做局部参数修补；请围绕“让 Agent 在长局、大图、跨域目标下真正完成战略任务”主动选择最有价值的突破点。普通小决定无需停下来问用户。

## 1. 最新桌面 E4：Target Stability 修掉一种振荡后，暴露了更一般的问题

请先读取：`evidence/astra-target-stability-desktop-2026-09-30/`。
本局为 Big Island、正常迷雾、5x、1800 game-sec Battle 预算，候选 JAR SHA=`165bd3b2...acf245`。
运行整体稳定：440 commands，211/211 attack orders confirmed，35 own losses，62 new combat units；原生结果仍 ONGOING，因此合法 PARTIAL。

用户肉眼观察：上一轮潜艇记忆造成的反复掉头形式没有原样复现，但一个远端海军工厂触发了同质问题——陆军主力被反复调往自己实际上无法完成攻击的位置，途中又被其他目标拉走，再重新回来。
原始报告确认：`seaFactory #230 @ (290,70)` 被切入战略目标 20 次，产生 47 次 `OBSERVED_ENEMY` tactical intent；Guard 对它记录 145 次 `COMPATIBLE`、2 次 `UNKNOWN`。
这次不是“武器域不兼容”：seaFactory 是 SURFACE，重坦/坦克武器理论上能打，所以 Compatibility Guard 正确判为 COMPATIBLE；真正缺的是“这支 LAND 编队能否到达一个可以实际攻击该目标的位置”。
因此请把它视为 `compatibility != reachability != engagement feasibility` 的自然 E4 证据，而不是再给 seaFactory 写一个例外。

## 2. 希望你主动考虑：把 Combat Engineer 作为新的全局综合单位

用户提出“工程师”可能是解决长局经济溢出、跨域能力缺口和扩张瓶颈的关键单位。冻结 1.15 asset 中最匹配的是 `combatEngineer`；请自己再用原生 action menu/runtime 验证，不要只相信静态 INI。
静态 `assets/units/combat_engineer/combat_engineer.ini` 已确认：price=3500，techLevel=2，isBuilder=true，movementType=HOVER，builtFrom landFactory / experimentalLandFactory。
它可修建筑/单位、回收资源，并能建 heavyTank、AmphibiousJet、extractorT2 / extractor、landFactory、airFactory、mechFactoryT2、seaFactory、fabricator、repairBay、builder 与多类防御建筑。
它本身可攻击陆地与水下目标；水下武器是鱼雷，条件为 `self.overwater`。这与用户设想的“能补重坦、两栖、工厂、矿，并能自行反潜”的全局单位高度吻合。

不要仅做“能生产 combatEngineer”这一条功能。更有价值的是设计它进入现有 task ownership / arbitration 后的角色决策：何时值得生产、何时承担跨域响应、何时建造/扩张/修理、何时直接生产单位，以及怎样避免一个工程师被多个意图来回抢占。

## 3. 同一局已经把几个旧硬上限同时暴露出来

最终桌面状态约 gameTime=1894.276s、credits=134401；有 40 个 mobile armed 单位，恰好撞到 `mobileUnitHardCap=40`。
最终只有 1 个 builder；3 个 landFactory 已全部升 T2；有 10 个 `extractorT1`，没有矿升级到 T2。
Battle summary 同时给出：`sustainableSurplusPerGameSecond=147.6`、`productionConsumptionPerGameSecond=0.0`、`builderTarget=1`、`landFactoryTargetAtEnd=3`。
这已经不是缺钱，而是“经济无法继续转化成有效能力”。

请综合评估并在必要时直接重构以下旧限制，而不是默认它们必须保留：
- 固定 `builderTarget=1`：考虑按建设 backlog、地图尺度、投资储备、跨域需求动态增加建造能力；不要只用高 credits 单指标。
- 固定 `mobileUnitHardCap=40`：考虑把 active army / reserve / hard safety cap 分开，并让有效军力目标随地图面积、敌人数、收入、威胁与任务需求扩展。
- `MatchClient` 当前 Battle 时长参数硬限制 120..1800：长局/大图已经需要更长实验窗口，应把产品时限与测试安全上限解耦并可审计配置。
- 矿升级目前缺席：已知原版 extractor T1→T2 存在真实升级/投资路径；请把矿升级纳入经济投资系统，和新矿、工厂、军力、Combat Engineer 做边际收益竞争，而不是单独写死阈值。

这些方向彼此耦合：更多工程师/建造者会提高投资执行能力；动态军力上限会重新制造生产需求；矿升级提高长期收入；跨域响应会改变“该造什么”。如果你认为一个统一的 Capacity / Investment / Task Allocation 层比逐项补丁更合理，可以直接做。

## 4. 目标选择需要从 Compatibility 升级到 Feasibility

请优先思考一个更一般的层次：`Target Compatibility` 只回答“武器能不能伤害它”；战略选择还需要回答“当前可用力量是否能在合理成本/时间内到达并完成任务”。
seaFactory #230 是自然反例：目标可见、SURFACE、武器兼容，但 LAND 主力无法自然进入水域攻击位置，于是整个军队被一个理论可打、现实到不了的目标反复吸引。

你可以选择 Reachability Guard、reachable engagement position、movement-domain task group、跨域 responder、工程师/两栖转换、失败后的证据化 suppression，或更好的统一模型；不要求沿用我们现有命名。
唯一目标是：陆军不应因为“理论能伤害”就无限追一个当前战略上不可完成的目标，同时 Agent 应能意识到“需要另一种工具/单位”并产生解决方案，而不是永久忽略这个敌人。

## 5. 64 MiB 报告上限：不要靠降低采样率长期绕开

上一轮 5x/1800 headless 因高频采样撞到单报告 64 MiB 写入上限。仅在报告生成后做 ZIP 分卷不能解决这个问题，因为 `ReportFiles` 会在写入阶段先失败。
建议你评估真正的滚动报告：例如 `battle-...part001.jsonl / part002 / ...`，单片控制在约 48–60 MiB，并有 manifest 记录逻辑报告 ID、part 顺序、每片 SHA256、总字节/行数、session 与 candidate identity。
上层 `battle_reports / analyze / export_battle_replay / collect_reports` 最好透明地把分片视为一条连续事件流；最终收集时仍可以 ZIP/分卷压缩。
同时可考虑显式 `--poll-ms` / sampling profile，并把实际采样间隔写进 provenance，不要让 game speed 隐式决定无界日志密度。
这项基础设施如果会妨碍当前战略突破，可以留给后续普通工程模型；如果它正好阻塞你做长局验证，则可以直接解决。

## 6. 自主权与成功口径

用户希望这一轮“放开了干”。你可以跨模块修改 BattleClient、Economy、production、task arbitration、world model、test runner，只要修改有明确行为目的，并保留合法迷雾与危险 reflection 安全边界。
不要为了覆盖功能表而平均用力。优先解决能改变项目后续轨迹的结构性痛点：可完成的目标、跨域响应、经济向能力的转化、长局可持续性。

如果 Combat Engineer 不是正确突破点，请用证据否决并选择更好的路线；如果它确实能成为统一工具，就允许把它做成第一版完整战略角色，而不是一个孤立生产按钮。
允许主动新增原生 headless 场景、A/B、确定性 fixture、长局样本；E2/E3/E4 分级继续保持诚实。

完成时请交付：
1. 你认为本轮真正解决的系统性问题，而不是文件列表；
2. 源码候选与可运行 JAR、SHA/contentDigest/lineage；
3. 完整回归和至少一个真正能支持结论的原生行为证据；
4. 仍未解决或被新设计暴露的风险；
5. 给下一轮普通 ChatGPT / DeepSeek 可以低成本接手的明确边界。

不用等用户确认小步骤，直接推进。
