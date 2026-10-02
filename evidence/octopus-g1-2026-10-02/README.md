# 八爪鱼 G1 本轮证据

实施提交 `5cc3e3f`，起点 `be7ba94`，分支 `codex/octopus-g1-clock-trace-20261002`。本目录是本轮新执行结果，不继承旧自然对局或56/56记录。

## 最终矩阵

`full-windows-regression.txt` 是完整原始输出的字节副本，SHA256 `a12cce9c14dc27a864c03ba5b0c23cca21518a952f06f24c6994b9c35830788f`；结构化步骤见 `final-validation-helper.json`。

**58/58 steps，30 Java runs，25 Python suites /439 tests，failed steps: 0，exit 0。** Python没有skip/failure。Java ReportCommit 原 Windows POSIX displacement simulation 跳过保留，其余检查运行。

实际完整矩阵 JAR SHA256 `ab5b7fd1fe97a4e4ae0b8cb59cfd7d189c492e8a8e99a8400ce1d3eb93e7287a`，精确身份见 `../../deliveries/octopus-g1-2026-10-02/candidate-manifest.json`。原 engine、libs、Java13来自既有本地隔离游戏环境，只读；原版assets复制到新的测试cwd。完整源码、tools、docs与fixture输入均在 `_validation/octopus-g1-20261002/final-source/`，不在桌面游戏目录执行。

标准命令为固定JAVA_HOME/PATH/UTF-8后，`powershell -NoProfile -ExecutionPolicy Bypass -File agent/test-win.ps1 GAME_JAR LIBS`。G1套件设置 `RW_G1_BASELINE_JAR` 指向immutable be7ba94重建，`RW_G1_EVIDENCE_DIR` 指向 `final-full-four-path`。

## 四条等价与因果链

`four-path-equivalence.json` 登记矩阵中12变体的完整报告hash、命令/旧event一致性、导出体积及链计数；完整报告保留在本地 `_validation/octopus-g1-20261002/final-full-four-path/`。新JARTrace on/off与旧be7ba94命令参数、顺序、owner/gameTime及旧event/data/outcome相同；仅归一化UUID及3个墙钟诊断字段。

`trace-chain-examples.json` 从最终完整矩阵的实际日志逐条选出每路径一个命令、其读取上下文、native receipt及后续witness，没有编造样例：Recon、LocalCrisis、provider成品匹配、ordinary忙后空。它是精选索引，不替代完整输出。inputObservationIds只是近期读取上下文，不能声称完整/独占数据因果。

Focused合同输出：`focused-GameClock-Trace.txt` 41 checks，`focused-engineer.txt` 61，`focused-strategy.txt`169，`focused-four-path.txt`4tests。最终矩阵又在精确ab5b…JAR上重复覆盖四路径。源码冻结前后清单106份全部一致。sidecar failure stderr/stdout检查为0。

## 原失败与身份限制

`baseline-initial-regression.txt` 首轮不可变基线55/56；`StrategyNativeHarness`因隔离cwd缺assets/translations/Strings.properties失败。补齐独立资源后 `baseline-native-environment-recheck.txt` 原生51checks通过，有效56/56。未用产品改动消除环境失败。完整详情为 `baseline-validation-helper.json`。

首次基线重复采集比较器没有去除requestWallTimeMs而失败；后来只明确归一化该墙钟诊断，不隐藏命令/游戏时间/行为数据。原日志位于本地baseline-four-path.log，限制在helper中登记。

先前focusedJAR整包SHA未及时登记，full重建覆盖后不追溯补认；保留的104class与最终class内容一致，`focused-build-identity-limit.json`说明限制。最终完整矩阵的重跑不受该限制。

## 保护与边界

`G1_INPUT_IDENTITY.json`登记两份用户DOCX、G0输入和受保护文件；`policy-preservation-checks.json`登记原主循环与14机制文件未变。`PROTECTED_FINAL_VERIFICATION.json`核验受保护文件与输入附件未变。

自然桌面/自主headless比赛均NOT_RUN，未部署新JAR；native记录仅fixture。日志增加墙钟与体积成本，确定性策略等价不等于自然采样间隔或胜负等价。G2具体输入/非目标见 `../../docs/OCTOPUS_G1_TRACE.md` 与交接。
