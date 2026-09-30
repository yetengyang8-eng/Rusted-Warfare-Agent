# 专属单位生命周期候选与验证

候选 `RW-CANDIDATE-2026-09-30-SPECIALIST-LIFECYCLE-v1` 在最新 Capability Funding 增量上，修正了工程师被普通经济任务挪用、到点失联后持续原地下令，以及已回家仍反复返回的问题。源码提交 `db0f6eec006a4f61f85134c38adc5c0f88a99e8a`，父提交 `ea614b5fdf729a0a09b3b324252555a31d565ac5`。工程基线仍为 `RW-BASELINE-2026-09-30-GS-v1`，本候选未晋级，也未安装到用户桌面。

固定交付件为 [rw-agent-bootstrap.jar](../../deliveries/specialist-lifecycle-2026-09-30/rw-agent-bootstrap.jar)，SHA256 `b3e172de9ba54e9ebfd8da1f6787c72ad9f9f7a8702e42cdc63cc948bc57e71e`。完整身份与资源指纹见 [candidate-manifest.json](candidate-manifest.json)。

交付前再次只读核对 v1 的 106 个文件及环境，唯一差异为两轮已提交增量涉及的 9 个源码/工具文件，没有其他基线或环境身份失败；原始结果见 [post-change-baseline-verification.json](post-change-baseline-verification.json)。因此该核对的 exit=1/source_drift 为预期差异，不能据此回滚候选，也不冒称候选与冻结 v1 完全相同。

## 从新思想中落地的行为

- 工程师负责能力需求，不再接普通探矿、新矿、工厂、重坦施工。普通额外 builder 继续经济工作；明确移动能力缺口所需的 amphibiousJet 施工保留，并在观察到成品后将需求交给响应者。
- 工程师采购记录选择的需求。队列变空并不等于成品已经出现；未观察到可用工程师时，承诺保留最多 180 游戏秒。损失与超时释放承诺，并尊重每项需求的三次失败上限。观察到的角色分配不冒称该单位必定来自某个工厂。
- 需求绑定覆盖响应、调查和返家。距接近点不超过 75 世界单位，且新 engagement 明确目标不可见时，进入最多 15 游戏秒的调查；此期间不重复攻击移动。到期保留 UNKNOWN，不计击杀、解决或无进展失败。同一旧观察不触发再次出发或补产。
- 返回基地且冷却结束后，释放旧调查的需求绑定，工程师可以接新需求；旧目标出现较新的合法可见证据后也可以重启。低血单位在家保持待命；已在基地或仍执行相同原生返回命令时不重发，远处命令中断则恢复。

通用敌群聚类、最小护卫编队、全军重编组和完整修理体系尚未实现。75/15 秒是这轮明确的工程行为与测试边界，不代表已经找到地图通用最优参数。

## 输入与复现

[INPUT_ANALYSIS.md](INPUT_ANALYSIS.md) 与 [input-summary.json](input-summary.json) 对最近三份桌面报告逐单位复算。第二局为 14 名工程师被观察到、12 名确认损失，并非 14 名死亡；11 名没有接过响应任务。两局有工程师普通经济挪用、到点后失联重发与已到家重复返回的直接证据。

旧桌面环境的实际 JAR 已核对为上一候选 `41c52392…`。原始报告缺地图路径、难度及 Match 前人工操作记录，均保留 UNKNOWN。三份 raw 和两份参考原文保存于 [input-evidence.zip](input-evidence.zip)，逐文件来源和 SHA 见 [input-evidence-index.json](input-evidence-index.json)。这些历史轨迹用于定位缺陷，不构成控制全部条件的 A/B。

新增 `test_specialist_lifecycle.py` 的四个真实 BattleClient HTTP 场景，在冻结旧候选上全部按预期失败，在新候选上全部通过。分别覆盖经济隔离、到点失联、有新接触后重派、已到家静默与远处返回恢复。原有七个资金预留 HTTP 场景继续通过；策略契约检查从 117 增至 166 项，覆盖需求绑定、三次损失上限、成品缺失超时、旧需求释放后处理新目标和两栖响应者接手。

首次契约测试的普通 builder 用例失败，是夹具只有 500 可用资金而矿需 700；修正夹具资金后通过。原始失败与修复后日志保留在 raw-evidence.zip 的 targeted 目录，未改写为首轮全绿。

完整 Windows 为 42/42 步、21 Java runs、18 Python suites、292 Python tests、0 Python skipped、166 项策略契约检查，`failedSteps=0`，见 [windows-regression.json](windows-regression.json)。固定候选和完整回归重建的 88 个非 manifest 内容条目一致，比较见 [jar-lineage.json](jar-lineage.json)。Java 的 POSIX 专用句柄模拟按平台跳过，Windows 文件锁用例实际执行；本轮未重新执行 Linux 回归。

## 隔离原生结果

固定候选在 Big Island / difficulty=1 / 请求 5x / poll=500 ms 的独立无画面环境运行。Battle 共 1200.752 游戏秒，结果 PARTIAL/ONGOING，原因为预算到期；runner 原始 status=FAIL、exit=1 和 Match exit=2 如实保留。引擎 exit=0，清理后进程与监听端口均不存在，报告完整。

- 两笔明确需求采购、两名工程师 #925/#1105 被观察到，工程师损失事件 0。这里只报告本局观察，不能据此归因存活率提高。
- 四次响应均进入有界调查，四次到期、返家后释放需求；两名工程师各自接手了另一个需求。没有工程师普通探矿或施工，没有已到家重复返回命令，也没有这四次响应的 NO_OBSERVED_PROGRESS。
- 一次到点下令发生在调查识别前：行 2485、单位 #1105，距点 4.565，上一响应评估距今 2.528 秒、刚首次采样到达。保留为诊断，不把“调查中不重发”扩大为“全局所有到点命令为零”；调查期间和明确失联超出观察宽限后的重复命令均为零。
- 合法解决需求数 0，末尾四项需求仍 UNKNOWN。自然局没有触发调查中重新发现目标或两栖支援转交，这两项仅有自动测试覆盖。护送、修理、桌面接受和胜率收益未验证。

逐事件与原始行号见 [native-lifecycle-events.json](native-lifecycle-events.json)。[既有合法视野/ownership 审计](native-global-audit.json) 和 [新增生命周期审计](native-specialist-audit.json) 均通过；新政策审计与旧版历史合法性结论分开。完整运行身份、seed（未验证可重现性）、清理记录及原始 runner 状态见 [native-runner-result.json](native-runner-result.json) 与 [validation-summary.json](validation-summary.json)。

## 后续入口与复算

继续从本仓库当前提交及 [CURRENT_STATE.md](../../project-state/CURRENT_STATE.md) 开发，保留这轮增量。下一项优先处理已有低血响应中断证据所支持的小编队护送，具体边界见 [NEXT_STAGE_PLAN.md](../../project-state/NEXT_STAGE_PLAN.md)。

```text
python tools/audit_global_strategy.py <raw.jsonl> --out <global.json>
python tools/audit_specialist_lifecycle.py <raw.jsonl> --out <specialist.json> --enforce-policy
```

[raw-evidence.zip](raw-evidence.zip) 包含旧/新测试、完整回归、原生 metadata 和嵌套 match-evidence.zip 中的原始报告；逐文件 SHA 见 [raw-evidence-manifest.json](raw-evidence-manifest.json)。冻结引擎、原版资源、用户设置、存档、回放和桌面游戏均未修改。新包使用与回退见 [候选使用说明](../../deliveries/specialist-lifecycle-2026-09-30/README.md)。
