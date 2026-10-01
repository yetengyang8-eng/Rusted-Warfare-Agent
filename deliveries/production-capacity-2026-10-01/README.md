# Production Capacity v1 候选

候选 ID：`RW-CANDIDATE-2026-10-01-PRODUCTION-CAPACITY-v1`。身份及验证结果见 [candidate-manifest.json](candidate-manifest.json)，设计、原始分析和证据边界见 [工程证据](../../evidence/production-capacity-2026-10-01/README.md) 与 [交接](../../handoff/HANDOFF_Codex_ProductionCapacity_v1_2026-10-01.md)。

该候选在现有军力目标/工厂目标控制器上加入可解释容量判断：军力战略目标满但 hard cap 有空间时，持续合法需求与经济安全证据允许每次最多8个 slot；军力 slot 已空余、producer 长窗口饱和时，才允许工厂目标加一。hard cap、builder/investment/capability reserve 和 native legality 在执行前再次检查。

这是工程验收候选。合成 fixture 的容量闭环与自然 headless 结果分别记录，没有桌面 Spain 验收，也没有“现金下降/胜率提高”的结论。原 FEEDBACK-v1 固定候选与原版引擎保持不变。

## 使用与回退

本目录是交付件，不是安装目录。本轮没有替换桌面 JAR 或操作桌面游戏。

1. 使用新的独立游戏验收环境。只在该环境游戏未运行时，备份环境内 `rw-agent-bootstrap.jar`，然后复制本目录 JAR 与需要的 Match 入口到环境根目录（与 `jvm64` 同级）。原版与历史冻结目录不可作为覆盖目标。
2. 确认复制后的 JAR SHA 与 manifest 一致，确认该环境原版 `game-lib.jar` SHA 为 `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`。
3. 操作者打开合法本地单机局并启用该环境 Agent，再运行 Match 入口连接默认端口47653。入口只启动客户端，不启动/关闭游戏引擎。保留该局地图、难度、预算、速度、候选 SHA 和报告。
4. 4800入口的 Battle 预算为4800游戏秒；起步/经济/发展另计，native胜负可提前结束。报告位于运行目录 `rw-agent-reports`。
5. 回退只对新验收环境，在游戏未运行时恢复该环境备份或 [FEEDBACK-v1 固定件](../feedback-2026-10-01/rw-agent-bootstrap.jar)；其 SHA 为 `0116e7c67f6fbd778d6956a9770205b31a458365c196d4063caa625f643c0cc7`。保留新报告、存档和回放，不覆盖原版或当前用户环境。

无画面验证可使用仓库 `tools/run_headless.py --game-dir DISPOSABLE_ENGINE --agent-jar THIS_JAR --java JAVA13 --mode match --episodes 1 --out NEW_DIRECTORY`；游戏资源用 `tools/prepare_headless_engine.py` 解压到新临时路径并验证。不得把合成/native fixture 的订单接收说成桌面完成验收。
