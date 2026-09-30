# Windows 基线离线验证｜2026-09-30

冻结源码提交：`6214f073d1b76429c0b827268db918e1e90e9f5f`。

结果：**PASS_COMBINED_MATRIX**。首轮完整回归共 40 步，原始脚本 `failed steps: 1`、退出码 `1`；唯一预期可补正问题是隔离快照漏取历史 JSONL fixtures。补齐同一冻结提交的 fixtures 后，受影响的 `test_reports.py` 单独重跑 16 项通过。40 个步骤各自的最终证据构成通过矩阵；不能把首轮描述成零失败。

- 矩阵：3 构建步骤、21 Java harness 运行、16 Python 套件。
- Python：281 项执行、0 项跳过、281 项通过。
- Windows 与 Linux 测试入口的 Java/Python 集合分别一致；Windows 的 2 项 FILE_SHARE_DELETE 专项补足既有 Linux 跳过项。ReportCommitHarness 的 POSIX 持句柄改名模拟按设计在 Windows 跳过，其余封存与 72 MiB 有界内存检查照常执行。实际平台差异见 `validation-summary.json` 的 skipLines。
- Windows 重建、仓库最终交付、解压交付、独立桌面验收安装 JAR 的 87 个非 manifest 归档项完全一致。contentDigest：`8092b61130c3497b370e12ce249d3ec17e6331d502fefbcb50bbd95a32f03120`。重建 whole SHA：`ba8bbf85709ed7b3975512603534367eab44ac3f3dac72081b5acb1cb9c111f5`；交付 whole SHA：`76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed`。
- 运行环境与具体可执行文件路径见 `environment.json`，本次使用 PowerShell 7.6.5、Liberica OpenJDK 17.0.20.1、Python 3.12.14，Windows build 26200。

## 隔离与复现

审计目录：`G:\deepseek 工作台\_baseline_audit\2026-09-30-gs-v1`。通过 `git archive` 从冻结提交提取 `agent`、`tools`，并补入 `docs/acceptance-0.02-alpha2.jsonl`、`docs/acceptance-0.03-alpha2.jsonl`、`docs/acceptance-0.04`；所有生成物位于该审计目录。独立 source 目录内运行原样 `agent/test-win.ps1 <original-game-lib.jar> <original-libs-directory>`。设置 `JAVA_HOME` 指向记录的 JDK、在 PATH 前加记录的 Python 目录，TEMP/TMP 指向审计目录下 temp，开启 Python UTF-8。完整参数在 `environment.json`；依赖文件哈希在 `dependency-hashes.json`。

本次只执行原版对象夹具、HTTP 客户端时间线、报告与编排 mock 回归，没有启动桌面游戏或原生对局，也没有改动主仓库源码、交付 JAR、原版库、设置、存档或回放。该矩阵不代表胜率、实战策略优越性或新增桌面验收。

## 证据

- `validation-summary.json`：首轮、补正重跑、最终逐步结果与可移植原始日志索引。
- `windows-regression-transcript.txt` / `raw-logs/`：保留首轮失败的完整原始输出。
- `test_reports-fixtures-restored.log`：同提交 fixtures 补齐后的 16/16 通过证据。
- `jar-lineage.json`：whole SHA、contentDigest 与归档项差异。
- `runner-coverage.json`：两个平台入口测试集合的实际解析结果。
