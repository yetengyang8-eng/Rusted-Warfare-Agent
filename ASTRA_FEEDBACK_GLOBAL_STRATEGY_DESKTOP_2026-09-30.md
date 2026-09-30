# Astra 反馈｜Global Strategy 最终候选桌面长局｜2026-09-30

请先读 `evidence/global-strategy-desktop-2026-09-30/README.md`，再按需展开专项 audit 和压缩 raw reports。

候选身份不变：JAR SHA256 `76711a8ee2716af5d5b0b66d5c53b159ae28f4f744dcbb24cae492d96f0342ed`，contentDigest `8092b61130c3497b370e12ce249d3ec17e6331d502fefbcb50bbd95a32f03120`。

本轮新增的是 **Windows 桌面 E4**，不是新的代码候选。三场均 5x、Battle 约 2400 game sec，最终均 `PARTIAL / ONGOING`。

- Big Island 180×180：127 新战斗单位、64 损失、最高 88 armed；13 个 capability needs，至少 1 个按合法证据解决。自然出现 seaFactory 的 `MOVEMENT_APPROACH_GAP`，并观察到 response progress 与目标可见伤害。
- Spain 10p 400×370（run A）：237 新战斗单位、243 损失、最高 21 armed；8 个 capability needs，但没有形成战略投资或 response task，表现为持续高消耗路径。
- Spain 10p 400×370（run B）：139 新战斗单位、82 损失、最高 101 armed；2 个 capability needs；52 次 strategy investment orders、13 次矿升级、30 次战略施工，并对潜艇 response 观察到进展与伤害。

## Spain 测试的人工干预边界

西班牙地图该出生位原生开局只有基地、没有建造者，现有 Match 无法从该状态直接启动。因此操作者在 **Match 启动前人工生产 1 个 builder**，再启动 Match；两次 Spain 测试都是如此。

**除此之外没有人工干预。** Match 启动后没有手动移动/生产/建造、没有替 Agent 改目标，也没有人为救场。

因此请把 Spain 两局记为：`operator-bootstrapped start (one builder before Match), then autonomous Match`。不要表述为从地图原生出生状态零干预全自动起步；也不要把 Match 启动后的策略事件误归因为人工控制。

## 目前最值得解释的问题

同一个 400×370 Spain 地图、同一最终候选，出现了最高 21 armed 与 101 armed 的巨大轨迹差异。下一轮请优先从 raw events 解释：能力需求为什么有时没有进入投资/响应、早期损失和 urgency 如何改变投资窗口、builder/工程师/生产与任务 ownership 是否形成正反馈或崩塌链。

这三局不能证明胜率提升；它们的价值是证明最终 Global Strategy 已在真实 Windows 桌面自然运行，并暴露长局稳定性与轨迹方差问题。
