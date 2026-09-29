# Astra Target Stability 桌面 E4 补充证据｜2026-09-30

来源环境：`P1F-Astra-TargetStability-2026-09-30`
候选 JAR SHA256：`165bd3b2207f96cee41a2dc3c8e25c36ce3ae799dd46a268304fe8dd65acf245`
原版 game-lib.jar SHA256：`8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`

本局：Big Island，正常迷雾，5x，Match 预算 1800 game sec；原生结果仍为 ONGOING，因此报告为合法 PARTIAL。
Battle 原始 JSONL SHA256：`d4dfb753538bd0a2d11397f9cd43ccafc75aed9d56368b5a176efb481a9c2acd`。
压缩包 `battle-1790704878629-b0eb25fb.zip` SHA256：`a45bcb544b4693018f58316408b9ff48b75c91ac8139acaede8558fb9d23a553`。
HTML 为相同原始报告导出的 Agent-POV 离线战术视图，共 689 个 observation frame。

关键运行数据：440 commands；211/211 attack orders confirmed；35 own losses；62 new combat units。
最终状态约 gameTime=1894.276s，credits=134401；40 个 mobile armed 单位，正好撞到 `mobileUnitHardCap=40`。
最终有 1 builder、3 landFactory（均已升 T2）、10 个 extractorT1；没有矿升级到 T2。

用户肉眼观察：上一轮“潜艇记忆反复吸走主力”形式发生变化，但同质战略问题仍存在：远端海军工厂持续吸引陆军主力。
原始报告中 `seaFactory #230 @ (290,70)` 被切入为战略目标 20 次，并产生 47 次 OBSERVED_ENEMY tactical intent。
对 #230 的 Target Guard 为 145 次 COMPATIBLE、2 次 UNKNOWN；这说明 Domain Compatibility 正常工作，但没有回答 LAND 编队能否到达可攻击位置。
样本中陆军单位已经逼近海岸，例如 heavyTank #228 到 `(337,293)`，仍不能真正处理位于水域的工厂。

这批证据用于下一轮 Astra 的全局目标可行性、跨域响应、经济/军力容量与 Combat Engineer 方案研究；不要把它误写成已证明某个具体修复方案。
