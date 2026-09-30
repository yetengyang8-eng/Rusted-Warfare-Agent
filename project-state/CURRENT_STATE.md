# 当前状态

当前基线：**RW-BASELINE-2026-09-30-GS-v1**，建立于 2026-09-30（Asia/Shanghai）。

## 唯一入口

先读 [BASELINE.md](BASELINE.md) 和 [baseline-manifest.json](baseline-manifest.json)，再读 [NEXT_STAGE_PLAN.md](NEXT_STAGE_PLAN.md)。本文件只记录基线建立后的增量状态，不再叠加多个“当前版本”。

## 最新增量：工程师有界资金预留

- 候选 ID：`RW-CANDIDATE-2026-09-30-CAP-FUNDING-v1`，基于基线提交 `6214868`，实现提交 `fd8572849388d2e7fe04a5d446154602b54d3118`。
- 固定候选 JAR SHA256：`41c52392d0d9cc7af39b4e726d6bab2537249ca146b5621d890fd2a8851a2141`；contentDigest：`28bca05142179dbf2640451682ca11b5d4ff440bc7f95321e3dafce356392f06`。
- 已实现：真实需求及菜单报价驱动的单项资金预留，预计缺口不超过 60 游戏秒才建立，90 游戏秒到期，取消/失败后冷却 60 游戏秒。普通生产和新可选经济支出尊重预留；既有恢复和施工优先级保留。
- 已证断点：Spain A 的 137 次工程师菜单均不可负担，93 次有空队列；普通出兵持续消耗收入。A 的 8 个需求去向、SHA、行号和可复跑工具见 [专项分析](../evidence/capability-funding-2026-09-30/ANALYSIS.md)。更早战损和整局分化根因仍未知。
- 针对性验证：117 项策略契约检查、7 个真实 HTTP 客户端场景全部通过。旧基线的有限收入场景失败，最终候选在开始预留后 48 游戏秒接受工程师订单；也验证紧急取消、超时、冷却和旧经济支出旁路。
- 完整 Windows 回归：41/41 步通过，21 Java runs、17 Python suites、288 Python tests、0 Python skipped、failed steps=0；Java 的 POSIX 专用文件句柄模拟按平台跳过，Windows 文件锁用例实际通过。固定交付与完整回归重建的 88 个非 manifest 归档项完全一致。
- 独立原生局：Big Island / difficulty=1 / 请求5x / 1202.416 Battle 游戏秒，预算到期 PARTIAL/ONGOING；3 次预留全部释放，2 次在约33秒后购买成功，1 次因需求已服务取消。新工程师 #2290/#3223 均观察到成品、认领和响应任务；两者后续均出现 NO_OBSERVED_PROGRESS，合法解决需求数0。引擎正常退出，原始报告完整，现有专项审计零违规。
- 固定交付件见 [候选目录](../deliveries/capability-funding-2026-09-30/README.md)，完整结果见 [交付证据](../evidence/capability-funding-2026-09-30/README.md) 和 [验证摘要](../evidence/capability-funding-2026-09-30/validation-summary.json)。原生局的第二次购买关联需求与成品首次接手目标不同，后续分析以逐单位来源记录为准。
- 该候选尚未晋级新基线，也未部署到桌面环境。后续保留本仓库增量源码，不因 v1 校验器报告源码差异而重置。

## 已固定的起点

- 默认源码：本仓库 agent/、tools/；生产代码及既有证据起点为 6214f073d1b76429c0b827268db918e1e90e9f5f。
- 最新功能：Global Strategy / Feasibility / Combat Engineer。
- 不可变交付 JAR：SHA256 76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed。
- 最新桌面环境：工作区 游戏环境/P1F-Astra-GlobalStrategy-2026-09-30；程序与原版引擎哈希已核对。
- 旧 P1F-冒烟环境 与旧 developer/dist 仍为 5741e241…，保留历史身份，不能据此判断最新候选未部署。
- 基线建立轮只整理和验证；其后的产品增量见上节，不回写旧基线结果。

## 已验证

- 最终交付件已有 Linux 完整回归、原生 E2 和单局无画面 VICTORY。
- 同一最终交付件已有三场 Windows 桌面长局：约 2400 游戏秒、5x，三场均 PARTIAL/ONGOING，专项审计均无违规。
- Big Island / Spain A / Spain B 峰值移动武装单位为 88 / 21 / 101。
- Spain 两局均在 Match 前人工生产一个 builder，之后自主运行；不是原生出生零干预启动。
- 本轮进一步核对 raw-evidence 内 76/76 文件、桌面 SHA 清单 11/11 文件、桌面压缩包内 9/9 原始报告。
- Windows 隔离验证为 PASS_COMBINED_MATRIX：首轮 39/40，补齐遗漏的同提交测试数据后受影响套件 16/16；最终 40 步、281 Python 全通过，Windows 文件锁项已覆盖。87 个非 manifest 条目与最终交付完全一致。平台跳过与原始失败保留在 [基线验收记录](../evidence/baseline-2026-09-30/VALIDATION.md)。

## 未验证与待解决

- Spain A/B 方差没有根因结论；两局不构成控制全部条件的 A/B。
- 经济积压、响应者生存/成组执行、终局清理仍未解决。
- 缺地图路径、难度等完整运行上下文；无 builder 原生开局尚不支持。
- 没有地图级胜率提升或因果收益证明；修理/回收、完整海军、同局双玩家仍在后续范围。
- 原始 Astra source commit 2f310ec 未进入本地对象库。已有交付 overlay/patch 对照，不能声称完整 Git ancestry 已验证。

## 下一项工作

资金链与任务建立已在独立自然局触发。下一项优先沿新工程师 #2290/#3223 的接近目标、命令、运动和进展判定追踪 NO_OBSERVED_PROGRESS，按证据修一个响应执行断点。取消后的真实军力收益仍待自然样本。完整早期战损归因及通用运行上下文扩展按需推进，不阻塞已有证据支持的单项施工；不继续扩大军力上限来替代诊断。

## 历史记录

整理前全部状态见 [archive/2026-09-30-pre-baseline](archive/2026-09-30-pre-baseline/README.md)。旧交付材料中的“未部署桌面”是当时状态，保留原始证据不改写；最新桌面证据覆盖其现状措辞。
