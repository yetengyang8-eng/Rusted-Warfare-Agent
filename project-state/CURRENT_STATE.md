# 当前状态

当前基线：**RW-BASELINE-2026-09-30-GS-v1**，建立于 2026-09-30（Asia/Shanghai）。

## 唯一入口

先读 [BASELINE.md](BASELINE.md) 和 [baseline-manifest.json](baseline-manifest.json)，再读 [NEXT_STAGE_PLAN.md](NEXT_STAGE_PLAN.md)。本文件只记录基线建立后的增量状态，不再叠加多个“当前版本”。

## 已固定的起点

- 默认源码：本仓库 agent/、tools/；生产代码及既有证据起点为 6214f073d1b76429c0b827268db918e1e90e9f5f。
- 最新功能：Global Strategy / Feasibility / Combat Engineer。
- 不可变交付 JAR：SHA256 76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed。
- 最新桌面环境：工作区 游戏环境/P1F-Astra-GlobalStrategy-2026-09-30；程序与原版引擎哈希已核对。
- 旧 P1F-冒烟环境 与旧 developer/dist 仍为 5741e241…，保留历史身份，不能据此判断最新候选未部署。
- 本次只整理基线、归档旧入口、核验已有证据和在隔离目录做离线构建/回归；不修改产品实现、不部署新程序。

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

按 [阶段 1 规划](NEXT_STAGE_PLAN.md) 对三份最新 raw battle 做可复算的需求、投资、响应和损失时间线。先解释 Spain A 的第一处分歧与未进入投资的门槛，再决定单一机制修复。不得先把军力上限继续翻倍来替代诊断。

## 历史记录

整理前全部状态见 [archive/2026-09-30-pre-baseline](archive/2026-09-30-pre-baseline/README.md)。旧交付材料中的“未部署桌面”是当时状态，保留原始证据不改写；最新桌面证据覆盖其现状措辞。
