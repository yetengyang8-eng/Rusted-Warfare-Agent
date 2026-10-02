# Current State

Updated: 2026-10-02 (Asia/Shanghai). 当前工程断点为 **Octopus G2：WorldState + Event Adapter**，已完成隔离验证，未部署。

## 当前身份

- 分支：`codex/octopus-g2-world-state-20261002`；用户验收起点：`ff693c805c4069a62ed451e33cffb882245cea35`。
- 实施：`5e30a344f7a6f46e53765ba9b95e86df7b3f3691`；最终测试源码：`5dd76e41d95887ec6c8dc6647476491b9b5241a6`。之后的 docs 提交只更新交付，不改变已测试源码。
- 候选：**RW-CANDIDATE-2026-10-02-OCTOPUS-G2-v1 / NOT_DEPLOYED**。
- JAR SHA256：`1dbeb3084f9ee7dd154b7290daef6c9fc2fe780d9e033fd45539fb3aa01ac7d9`，362190 bytes；contentDigest：`9e51d99cc67e00833080ae1b1a23387015f7b8bafc460b70206bee9a4c03998b`，121 非 manifest 条目（包括目录）。
- 桌面仍为此前 capacity JAR `a392f692e010c8429a7a93072e60323c83a9deb1ed471bbcbddbd3bbbaf6adb6`。G1 的 `ab5b7fd1…` 保留为历史候选，不与 G2 混用。
- 冻结基线 `RW-BASELINE-2026-09-30-GS-v1` 与引擎 `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9` 仍是血统/兼容核对身份，不授权回滚当前源代码。

## 已完成的范围

新增不可变共享 WorldState 和 EventAdapter。每个 endpoint + 完整 requestPath 独立记录 authority、coverage、freshness、原生时钟与读取身份；当前事实与 LastObservation 分开。处理 session/player reset、frame/gameTime rollback、断档、重复、冲突及乱序。敌失联不变死亡，queue empty 不变 ready。事件发生时间保持 null，检测时间和原生采样时间独立。

BattleClient 在既有校验后双写和记录；旧策略继续读取原响应。主循环、命令路径及 55 份其他原 production/tools 文件保持原内容。未实现 G3、owner generation、Intent Scheduler、新的 General/FREE/Capability，未改变 command gate、吞吐、资金/slot。适配器 reset 合同不代表旧客户端新增跨局/回退自动恢复。

## 新鲜验证

**最终 Windows：60/60 步，31 Java，26 Python 套件/448 tests，failed steps: 0，exit 0；Python skip 0。** 原 ReportCommitHarness 的 Windows/POSIX 子项平台跳过保留。核心 73 checks，独立复核 14 checks。冻结 ff693c8 基线 58/58、439 Python tests，全绿。

五组正常输入三方比较（四代表路径及 native-stamps 变体）和四组 guard 三方比较，共 27 raw reports；G2 on/off/冻结 ff693c8 的 HTTP 请求/响应、命令及旧事件数据相同（只归一化 request UUID 和三项旧墙钟诊断）。G2 fixture 给缺失 teamId 对称补值，仅用于夹具适配；原未适配 G1 四路径另行验证。132 份源码/测试/资源/tools/runner 与最终隔离输入逐字一致；JAR 测试期间不变，保护输入哈希未变。

自然局/桌面 G2 验收 **NOT_RUN**。四路径日志体积约为关闭 G2 的 1.83–2.76 倍（双方仍开启 G1）；不是 CPU、延迟、自然轮询等价或战绩证据。旧矩阵中断、初次失败及 frozen G1 早期 report commit NPE 均保留，详情见交接。

## 下一任务入口

[G2 交接](../handoff/HANDOFF_Octopus_G2_2026-10-02.md)、[WorldState/Event 契约](../docs/OCTOPUS_G2_WORLD_STATE.md)、[证据](../evidence/octopus-g2-2026-10-02/README.md)、[manifest](../deliveries/octopus-g2-2026-10-02/candidate-manifest.json)。推荐下一断点为 G3，接口建议见 [NEXT_STAGE_PLAN](NEXT_STAGE_PLAN.md)，需另有用户授权。

后续子智能体优先 `gpt-6.1-sol / high`，不使用 Astra。`ASTRA_*` 仅保留历史导航文件名，不表示应使用该模型。
