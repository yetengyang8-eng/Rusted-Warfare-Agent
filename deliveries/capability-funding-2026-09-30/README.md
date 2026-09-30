# Capability Funding 候选

候选：`RW-CANDIDATE-2026-09-30-CAP-FUNDING-v1`。

本目录 `rw-agent-bootstrap.jar` 是本轮针对性验证和独立无画面对局所用的同一固定文件，SHA256：

`41c52392d0d9cc7af39b4e726d6bab2537249ca146b5621d890fd2a8851a2141`

基于 `RW-BASELINE-2026-09-30-GS-v1`。变更、验证与结论边界见 [交付证据](../../evidence/capability-funding-2026-09-30/README.md)；后续工作入口见 [当前状态](../../project-state/CURRENT_STATE.md)。

仅在新建的隔离环境使用，采用既有 `tools/run_headless.py --agent-jar` 指定此文件即可。本目录没有自动安装动作；最新桌面环境仍使用基线 JAR。不要复制到原版、旧 developer 或覆盖正在运行的环境。

回退参照为 `astra-deliveries/global-strategy-2026-09-30/rw-agent-bootstrap.jar`，SHA256 `76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed`。当前未替换安装件，因此无需执行桌面回退。
