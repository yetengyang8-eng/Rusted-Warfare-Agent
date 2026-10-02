# G4 official full JAR: focused native acceptance

**五类验收全部通过，共 298 checks。** 五个独立 JVM 使用 root 统一 full gate 生成的同一 JAR；native agent 只复制 JAR、核对 SHA256 和编译测试 harness，没有重新编译产品或运行 full matrix。

官方 JAR：G:\deepseek 工作台\_validation\octopus-g4-20261002\final-source\agent\dist\rw-agent-bootstrap.jar

隔离副本：g4-full-final.jar

两者 SHA256：b3f6a344ddd2236f68828b3745ab2f150063c850b8483a0c9c946d9befa6b4cd

52 个官方冻结源码、资源与所用 harness 文件的 aggregate SHA256：dc2bf3cf0b548eb565cdefba05f7414d52fbe88173bfed4d48e6f070debcdbba。G4_FULL_NATIVE_MANIFEST.json 保存每文件 hash、aggregate 定义、23 个原始日志/JSON 工件 hash，以及五个场景的开关、结果和检查数。

| 场景 | G4 开关 | 结果 |
| --- | --- | --- |
| G4 force runtime | true | PASS 60 |
| G3 production + native construction | false | PASS 168 |
| G3 independent Dive/Fly | false | PASS 30 |
| G3 immediate caller competition | false | PASS 31 |
| Spain water-bridge boundary | false | PASS_BOUNDED_ACCESSORS 9 |

G4 使用完整初始化的原版引擎与真实原生单位、真实 RuntimeBridge HTTP、BattleClient 的 observe/collect/flush adapter、实际 G3 scheduler 和原版 command.k。两个相隔的六人编队分别获得 general:1/general:2 owner，并独立下达真实 attackMove。实际工厂队列在付款与队列观察之后，经明确记录的受控原版 producer update 生成 heavyTank201；后续 /state 才将它纳入 FREE/UNATTACHED，同出生 frame 的 requestJoin 被拒绝，两个 General 均不自动吸收它。

在实际 ForceController 的家园响应中，敌我 heavyTank 原生 HP 均为 600，既有 factor1.5 要求 900，所以一人不足，201+100 的 1200 刚好使最少所需人数为两人。201 来自 FREE，100 从 General 暂时脱离。正式 requestJoin 只给仍在 LocalResponse 的201增加 PENDING_JOIN。真实收集到的 General30/LocalResponse70 候选被 fixture 逆序后才交给实际 BC flush；剩余一个共享 token 仍由70的 LocalResponse 获得。此检查没有伪造 Proposal、transport 或 queued receipt。

目标在真实新鲜 combat observation 中消失后，实际 response reconciliation 将201转为 join:1/JOINING。GET 合法 state frame725 与实际 HTTP POST native receipt frame726 被故意区分，registry 正确保存726。即使同receipt frame726 的真实 own state 已读取到 fixture centroid 位置，仍不能 ATTACH；后续不变的远处位置仍 JOINING，最后显式原生位置到达加 later /state 才使其 ATTACHED/ASSIGNED、转回 general:1、递增 generation 并清 reservation。

对已经真实收集的 General1 proposal，fixture 使用实际 arbiter transfer 制造 owner ABA：101 generation2→4，owner 字符串回到 general:1。实际 BC flush 在 POST 前拒绝整笔旧 General1 proposal，不消耗 token；独立 General2 仍发出真实 native command。原始事件与 G1/G3/G4 日志保留具体 Intent、generation、取消原因和 later state witness。

旧 G3 四类在同一官方 G4 JAR、g4Forces=false 下保持绿灯。1600credits 的两笔800订单跨 gameTime 延迟原生执行时没有多放行；实际付款/队列及受控原生未完工施工 site effect 后才结算。两架飞机的 desired WATER、queued Dive/Fly 与 later 独立 unit-modes 的实际潜水能力继续分开。全部19个实际 spending attempts 均核对到真实先前 validated GET observationId 与完整 request path，不用 /state 充当价格来源。

证据分类始终为 E2_NATIVE_FIXTURE_WITH_REAL_HTTP / NO_NATURAL_MATCH。G4 测试通过反射进入真实私有 adapter，不声称运行完整自主主循环；root 的实际主循环 focused HTTP gate 是另一个检查层。时间、位置、目标 dead flag、producer progress 和候选输入顺序均是明确记录的 fixture 操作。自然行军、自然生产耗时、自然战术成效和开火伤害未获验证。Spain 地图来自附带 mods/maps，而非原版内置地图；water-bridge 边界及被动原生目标选择得到证明，自然潜水移动进入和实际开火仍为 NEEDS_EVIDENCE。

原生引擎 SHA256：8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9。Spain 原地图与隔离副本 SHA256 均为342db6d8a8b8320b6a271b9e3c8a4c29c96a203e206c4be690c44bbef5882746。引擎/libs 只读，没有启动、关闭或修改桌面游戏。所有构建和日志位于此隔离 native cwd。

原始日志为 g4-full-g4.log、g4-full-production.log、g4-full-modes.log、g4-full-competition.log、g4-full-spain.log；各同名目录保存 fixture JSONL、BattleClient JSONL 和 summary JSON。g4-full-harness-compile.log 记录测试编译。pre-log-compatibility/g4-full-launch-error.log 保留此前一次 PowerShell 参数引用错误。此次五个新版本 JVM 均直接启动成功。

旧 g4-iteration1.jar 及 g4-iteration1-receipt-failure.log 保留被强化验收发现的 source-frame/receipt-frame 缺陷；最终版本已修复并通过上述真实 N+1 检查。旧 G3 mode/1600-credit 失败工件也仍保留。

## Log compatibility 修正后的正式复验

root 将 battle_config 的 g4Forces / forcePriorityScope 两字段改为仅 G4 enabled 时输出，保留 G4 disabled 的旧事件形状。native agent 未修改控制逻辑或源码，也未运行完整 Windows gate。本轮直接复制更新后的 official full JAR，前后 hash 一致，测试 harness 只针对同一 JAR 编译。五个独立 JVM 再次通过60/168/30/31/9，共298checks。

此前官方 c13af09 版本的16项工件已通过单一 PowerShell，在验证原路径与目标路径都位于隔离 native 根内之后，原样移至 pre-log-compatibility。旧 JAR、五个目录与五个日志、测试classes、compilelog、launcherror、验收说明和manifest保留；旧manifest的路径是移动前历史路径，原内容没有改写。新 active manifest 与说明对应 b3f6a344 版本。本说明只确认 native gate，修正后的完整 Windows 回归结果由 root 报告。
