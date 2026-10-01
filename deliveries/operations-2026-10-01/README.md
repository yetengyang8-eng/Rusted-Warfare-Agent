# 运营与队伍调动候选

**RW-CANDIDATE-2026-10-01-OPERATIONS-v1**，从 Specialist Lifecycle v1 继续，固定基线仍为 `RW-BASELINE-2026-09-30-GS-v1`。

本目录 JAR SHA256：`28668be7c49a2b5d8f7f326fbc3f365a2179d4c1b92a169959c58213ec44ecb7`；contentDigest：`5805037b3d360966e161c2fcfba82ec527f84ab340509a5dd298912898b83c93`。

新增自动补建造者后重新预检、多队独立目标/前沿/轮转、少量重炮富余支出和接近军力目标时的升矿约束。原专属生命周期、资金预留、单owner与合法视野继续保留。实现与验证见 [交接](../../handoff/HANDOFF_Codex_Operations_2026-10-01.md)、[候选清单](../../evidence/operations-2026-10-01/candidate-manifest.json)、[证据](../../evidence/operations-2026-10-01/README.md)。

Windows完整47/47步通过，342 Python用例；最终报告解析器追加25项及7场联动，合并覆盖352个不同Python用例。普通原生复核整轮PASS/VICTORY；无builder受控夹具自动起步，Battle预算到期PARTIAL。新候选尚无用户桌面验收，不能将限时或单局结果当作胜率提升。

## 使用与4800入口

当前目录是固定交付，不是游戏安装。无画面验证用仓库 `tools/run_headless.py --agent-jar` 指向本JAR，输出选新的隔离目录。桌面使用应新建独立游戏环境、使用匹配的原版资源和现有启动套件，再复制本JAR及 `RW-Agent-Match-4800.bat`；本轮没有替换任何桌面JAR。不要在运行中的目录覆盖程序。

用户追加的4800入口也已经放到现有 `游戏环境/P1F-Astra-SpecialistLifecycle-2026-10-01/Astra验收-2开始Match-4800.bat`，可直接使用，但它运行该目录现有的 `b3e172de…` 候选。原900/1200/2400入口保留；使用本轮功能需要把新JAR安装到新的独立环境。

4800是 **Battle游戏秒**，启动/经济/发展阶段另计。原生胜负会提前结束；墙钟安全上限3600秒，推荐5x（Battle约16分钟），至少2x适用。参数合同4项已通过，实际4800长局尚未运行。入口不会启动或关闭游戏，由操作者打开合法单机对局后再使用。

## 回退

上一候选 [Specialist Lifecycle JAR](../specialist-lifecycle-2026-09-30/rw-agent-bootstrap.jar) SHA256 `b3e172de9ba54e9ebfd8da1f6787c72ad9f9f7a8702e42cdc63cc948bc57e71e`；冻结基线 JAR 位于 `astra-deliveries/global-strategy-2026-09-30`，SHA256 `76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed`。本轮未替换桌面JAR，当前无需桌面回退。新增4800入口是独立文件，移除它不改变原入口或设置。
