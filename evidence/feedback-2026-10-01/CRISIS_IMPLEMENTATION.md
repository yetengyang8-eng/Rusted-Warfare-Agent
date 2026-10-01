# 局部骚扰响应与控兵时隙：实现交接

2026-10-01。本轮从仓库 `82c8ff8` / Operations 候选继续；冻结基线未改变。生产源码仅在 `GitHub发布/Rusted-Warfare-Agent/agent`，此文件是交接说明，不是替代源码。root 统一合流、构建和原生验证；本子任务没有启停桌面游戏，没有修改旧环境、设置、存档、回放或共享 build/dist。

## 输入断点

反馈原文为 `C:/Users/Administrator/Desktop/反馈.txt`。输入分析 agent 流式核验新 Spain 长局原始报告：

- 行10931 / 2025918游戏毫秒，矿23579 `(6290,3750)` HP360→320；行10933可见 lightGunship31882，HP50，距矿约118。附近1000圈49名 ready 武装，最近重坦27850/24014各HP600；旧队此前行10916向这一个敌军调动24人。这是少量兼容单位有机会局部响应的证据，不能据此断言伤害或战损的唯一原因。
- 行12853 / 2294643游戏毫秒，cohort4存活26人全部原生 `orderType=null`，距行11929上次accepted133.295游戏秒。其间41次观察、40次action（19生产、16move、1build、4其他attack）。这支持队伍时隙被连续其他任务占据，不能直接归因为1秒全局节流。
- 精确完整窗口与raw身份由本轮 `metrics.json` / 输入交接记录控制；本文件不扩展输入样本的地图、难度、人工操作或原生验收结论。

## 本轮有限实现

新增 `LocalCrisisPolicy.java`，把人数、当前HP和距离的选择包络从执行器分开。它不使用价格衡量战力，也不预测胜率或保证矿存活。

局部响应仅从当前合法可见、可攻击的移动敌军触发；目标距 ready 自家commandCenter/矿450以内，同250范围可见armed小簇最多3个。候选是没有真实其他owner、没有撤回休息、非侦察待接管的 main，距资产1500以内且HP≥最大HP50%。每个候选必须通过原 Target Guard `COMPATIBLE`、战略地形负证据筛选和原生 engagement `COMPATIBLE + APPROACH_PATH_KNOWN`；UNKNOWN不作小股防御承诺。

最近候选按距离与ID稳定排序，选2..6人；选择时要求当前己方HP总和至少达到当前可见小簇HP的1.5倍。该数只是保守耐久下限，不能当成输出、武器范围或生存预测。仍保留至少6名可用main和每个现有cohort至少3人；达不到包络便不创建临时响应。没有调高hard cap。

实际执行仍由唯一CommandArbiter和原生端点控制。响应者以 `local-crisis:N` owner 整组claim；全部claim成功才建立任务，马上从主力cohort会员中移除。主动服务的目标/小簇被主力目标选择跳过，避免小组出动的同时大队也回头。真实工程师、两栖响应者、侦察归属没有被夺取。

生命周期：最多45游戏秒；可见目标离开资产600圈、骚扰扩大超过3人、己方低于25%HP、目标负证据或无进展拒绝，进入撤回；目标失联等待5游戏秒便撤回，明确记录UNKNOWN，不能称为消灭。撤回按已有原生attack-move以REGROUP语义回到借出时的集合点，观察到接近/已接受撤回后8秒/等待命令15秒后交还主力；结束后同目标12秒重试冷却。Controller结束时只释放owner并写配对日志，不额外发送游戏命令。

正常响应仍是8秒accepted节奏；明确目标移动或实际原生无指令时允许有边界的重新下令。原生拒绝不更新accepted时间。每一条命令都经过共享1游戏秒门，没有第二个actuator。

## 公平调度与诊断

原生低血主力撤退和局部危机先于公平调度，公平调度先于普通战略分配、经济、侦察和生产。已有两队及以上时，至少半队（且至少2人）原生没有命令，并且本队至少16游戏秒没有accepted，可争取一条公平时隙。全局公平时隙之间至少8秒，按稳定cohort轮转，拒绝不记成功时隙。

可见兼容目标还须离无指令成员180以上，避免把近距离原生自动开火误判为发呆；原有targetGuard、strategy.eligible、no-progress冷却照常生效。没有局部接触时沿已accepted且未过期前沿恢复；没有有效前沿时依原生planned/pathKnown方案前进，无前沿则保留已有REMOTE_VISIBLE_CONTACT后备。不会为了拿时隙跨越UNKNOWN武器域。

队伍会员、前沿距离改善、到达和20/75秒停滞期限每个decision观察，即使其他任务占gate也更新。正常控兵保留原8秒cadence；真实缺失原生指令可在至少2秒后有边界恢复，且恢复之间至少8秒。

每10游戏秒、最多4支队各1条 `local_army_diagnostic`，在所有lane执行后写入：members、unitsWithNoOrder、unitsWithNoOrderAwayFromGoal、lastAcceptedAgeMs、legalTarget、targetId、frontierAvailable、frontierPlanAgeMs、cooldownRemainingMs、globalGateReady、当tick gateOwner/gatePath。null-order只是原始事实，不能直接称为不战斗。`local_army_fairness_slot`记录实际成功而不是意向。

Ownership日志保持旧审计合同：每actor的唯一负数lease taskId，与正数crisisTaskId分开，acquire/release逐actor配对；实际owner仍是整组 `local-crisis:N`。全组crisis事件的taskId仍是N。accepted事件带requestId、确切actor集合和phase，用action/native receipt验真。

## 专项结果与边界

隔离目录 `_validation/feedback-20261001/crisis` 的首次有效源码编译通过。LocalArmy原372项合同、新LocalCrisis16项合同通过。控兵suite现12测试方法、14个HTTP合成时间线；原7场全部保留。新覆盖包括最近两人响应、main/cohort保留、真实specialist排除、UNKNOWN/潜水不兼容/native UNKNOWN拒绝、失联撤回释放、原生拒绝1秒后重试，以及双工厂持续生产60tick下两个无指令队伍轮流拿公平时隙。每份raw继续独立analyze_file并要求issues为空。

初次专项暴露并修正了公平探测无前沿时把lastPlanAt推迟、导致旧远处可见目标后备被跳过的回归；最终代码保留该后备。持续生产夹具的原生menu结构也按actions/queue合同修正。HTTP无原生引擎、不代表桌面收益，不声称击杀或胜率提升。

源码冻结后仅补了测试原生engagement夹具的targetX/targetY，使完整global审计能验收；新的时间线另存 `crisis/logs/http-v3`，上一批 `http` 保留。最终专项输出 `crisis/logs/local-army-tests-v3.txt` 和 `crisis/validation-summary.json` 由实际运行结果控制。JAR是该隔离源码快照，不继承root后续全合流或原生结果。

v3结果：12/12测试方法通过，14/14合成raw的报告解析issues为空，14/14旧global策略事件审计通过，无ownership/terrain违规。连续生产场公平实际授予为cohort1@2秒、cohort2@10秒、cohort1@18秒、cohort2@26秒，持续轮转至58秒；从未获得命令的队伍没有accepted年龄，允许首次调度，每队随后间隔16秒、全局公平间隔8秒。没有把三次专项运行相加当成新覆盖。

本轮没有修改纯全局urgency阻挡扩张的旧门槛：`considerInvestmentIntent` / `maintainInvestment`仍有该门；above-floor实际site只检查近威胁，完整builder-route可验证安全还不够。root授权可后续做，但本子任务按先冻结、验证核心的安排保留该限制，不以pathKnown当战斗安全。

合流只读审查未发现MineInvestmentPolicy、StrategyDirector矿块、EconomyBridge T3的必须生产修复：HP/转换重置、UNKNOWN风险拒绝、报价/ID/类型/queue/pending、保护资金加两笔普通报价和剩余回本窗口均保持；新T3需要旧全局审计器从硬编码T2适配为exact product匹配，已通知root处理。
