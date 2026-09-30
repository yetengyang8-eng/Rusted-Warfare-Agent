# 基线验收：RW-BASELINE-2026-09-30-GS-v1

日期：2026-09-30（Asia/Shanghai）。本轮整理了接手入口、固定了代码和运行件身份、核验了原始证据，并补做 Windows 隔离构建与离线回归。没有修改产品实现或部署程序，没有新启动桌面游戏或原生对局。

## 身份和可追溯性

- 生产源码与既有证据起点：`6214f073d1b76429c0b827268db918e1e90e9f5f`。
- 最终交付、原始解压交付、桌面验收安装 JAR：SHA256 `76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed`。
- 本次 Windows 重建 JAR：SHA256 `ba8bbf85709ed7b3975512603534367eab44ac3f3dac72081b5acb1cb9c111f5`。
- 四份程序的 **87 个非 manifest ZIP 条目完全一致**，contentDigest 均为 `8092b61130c3497b370e12ce249d3ec17e6331d502fefbcb50bbd95a32f03120`。完整归档 SHA 因归档/构建元数据不同而不同，不能据此判为功能变更。
- 生产代码与交付 overlay 的 16 个 agent 文件逐项相同；源码 patch 在 agent/tools 范围的反向检查通过。原始声明提交 `2f310ec` 在本地不存在，未证明 Git ancestry；本次可重建的是已导入的 `6214f07` 源码。

证据：[jar-lineage.json](windows/jar-lineage.json)、[integrity-summary.json](integrity-summary.json)。

## Windows 验证结果

结果为 **PASS_COMBINED_MATRIX**，不能改写成“首轮全绿”。

| 项目 | 实际结果 |
| --- | --- |
| 首轮完整入口 | 40 步，39 通过，1 失败；原始退出码 1 / failed steps: 1 |
| 失败原因 | 本次隔离快照遗漏 test_reports 依赖的历史 docs/acceptance JSONL fixtures；不是产品代码变更 |
| 补正 | 从同一冻结提交补入测试数据，不改源码；只重跑受影响 test_reports，16/16 通过 |
| 最终组合矩阵 | 3 构建 + 21 Java runs + 16 Python suites，40 个步骤均有通过证据 |
| Python | 281/281 通过，0 skipped |
| Windows 文件锁 | 2 个 FILE_SHARE_DELETE 测试实际通过，补齐此前 Linux 的跳过项 |
| Java 平台差异 | 仅 POSIX 持句柄改名模拟按设计在 Windows 跳过；不是所有 Java 检查无跳过 |
| 长报告 | 72 MiB 完整性与有界内存检查通过 |

首轮完整 transcript、每步日志及受影响套件补跑均保留。机器摘要逐步标明采用哪次结果：[validation-summary.json](windows/validation-summary.json)。完整复现说明见 [Windows 验证 README](windows/README.md)。

本次实际运行环境：PowerShell 7.6.5、Liberica OpenJDK 17.0.20.1、Python 3.12.14、Windows build 26200。历史交接“没有 PowerShell 7”和旧 JDK/旧套件数已不作为当前环境事实。完整路径与参数见 [environment.json](windows/environment.json)。以后隔离快照应同时带入 agent、tools、docs 中的测试依赖，避免重复本次准备错误。

## 原始证据完整性

- Global Strategy raw-evidence.zip 的外层 SHA 及内部 **76/76** 文件大小/SHA 校验通过。
- 最新桌面 SHA256SUMS 清单 **11/11** 文件通过。
- 桌面三个 ZIP 内 economy/development/battle 原始报告 **9/9** 文件大小/SHA 通过。
- 三场桌面审计对应同一最终 JAR；均 `pass=true`、`violations=[]`，但对局仍是 **PARTIAL/ONGOING**。
- 使用的原版 game-lib 和 20 个 libs 文件再次校验均未改变。工作区的最新候选、旧冒烟件、旧 developer/dist 和冻结根 JAR 分别登记，未互相覆盖。

校验通过只证明材料与其既有索引一致，不扩大材料本身的行为/因果结论。最终、中间和预最终运行件保持区分；原始 manifest 的历史“未部署”字段保留原文，后补桌面证据说明现状。

## 文档与后续检查

权威入口是 [BASELINE](../../project-state/BASELINE.md)、[CURRENT_STATE](../../project-state/CURRENT_STATE.md) 与 [NEXT_STAGE_PLAN](../../project-state/NEXT_STAGE_PLAN.md)。根目录和助手交接/evidence 仅导航到这些文件。旧入口原文及 SHA 保存于 [历史归档](../../project-state/archive/2026-09-30-pre-baseline/README.md)。

旧证据按实际 LF/CRLF 逐文件固定 Git checkout 规则；本轮原始日志和归档禁止文本转换。这样跨系统取得基线时不会因自动换行改变已登记 SHA。既有证据正文及原始 ZIP 未改写。换行核查清单见 [line-ending-audit.json](line-ending-audit.json)。

仓库根运行 `python project-state/verify_baseline.py` 可只读核对生产源码和登记材料；附加 `--workspace "G:\deepseek 工作台"` 可核对本机环境身份。该工具不会构建、安装、启动游戏或重置改动。若后续源码变化，它应报告偏离 v1，而非将新代码伪装成原基线。

基线本身不是稳定胜率认证。最新桌面三局尚无终局、Spain 两局前置人工 builder、缺少地图路径/难度/seed 的完整自证，均继续作为限制。下一阶段先分析可行窗口、投资门槛、任务归属和损失时间线，再决定单项代码改进。
