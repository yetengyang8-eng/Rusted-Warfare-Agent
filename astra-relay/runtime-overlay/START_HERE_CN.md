# 0.07：自主完整对战原型

1. 退出旧版游戏，将升级包内同名文件覆盖到游戏目录。
2. 双击 `RW-Agent-Start.bat`，进入原版 1.15 的本地 1v1 遭遇战，开启正常战争迷雾。
3. 标准开局后双击 **`RW-Agent-Match.bat`**，保持游戏运行，勿同时手动操纵或运行其他控制脚本。
4. 程序先建厂、完成一座当前可见矿和七辆坦克的短开局，再进入寻敌、工厂升级、补兵与进攻。
5. 完成后双击 **`RW-Agent-Collect-Reports.bat`**，发送它提示的完整 ZIP。

**标准流程固定为 `Start → 进入对局 → Match`，只运行一条命令。** `RW-Agent-Match.bat` 内部按 `经济 → 发展 → 战斗` 顺序执行；**在同一局里不要再开 `RW-Agent-Battle.bat`**。`RW-Agent-Battle.bat` 只用于专项调试，或接管一个经济与军队都已备好的中局；它在裸开局里不会建第一座工厂，因此不能当作常规评测入口。

**任何一次 Match / Battle 若以 `FAIL` 结束（例如桥接 503、连接超时），本局立即作废**：不要在同一残局里补跑 Battle 或其他脚本，直接重开一局重新测试，否则这一局的开局条件已无法与其他样本比较。

**移动单位上限的当前开发默认值是 40**（`mobileUnitHardCap`）。常规评测直接用 `RW-Agent-Match.bat` 即可；报告里的 `battle_config` 与每次心跳都会写明实际 cap，无需手工记录。若要做 32/40 对照，用 **`RW-Agent-Match-Cap32.bat`**（与 Match 完全相同，只多 `-Drwagent.mobileUnitHardCap=32`）复现旧基线。其余旋钮（`activeArmyTarget=24`、`reserveTarget=8`、`mineTarget=3`、`landFactoryTarget=2`）保持不变。

已有闲置陆军工厂时，Match 会直接进入战斗。若工厂正在生产或建造，预检会提示先完成当前任务。

战斗默认最多运行 **900 游戏秒**；`RW-Agent-Match.bat 1800` 可延长到 1800 游戏秒。正常速度下，默认战斗阶段最多约 15 分钟，另加开局时间。暂停超过 30 现实秒、读取其他存档、联机或回放模式会停止。

`PASS / VICTORY` = 观察到原生胜利结算；`PASS / DEFEAT` = 观察到原生失败结算；`PARTIAL / ONGOING` = 时间到、仍未结算。**PASS 本身不等于获胜。**

只想复测已通过的经济侦察闭环，仍可用 `RW-Agent-Autopilot.bat`（新增 8 坦克、2 矿、最多 24 次侦察）。已经准备好经济、军队且要直接接管战斗，可用 `RW-Agent-Battle.bat`（专项/调试入口，不要与 Match 同局并用）。

报告包含 economy、development、battle 三类原始 JSONL；已有工厂直接接管时只产生 battle。仅发送汇总截图无法核对实际下令和结算，请发送收集后的 ZIP。

升级包里的 `docs/replays/small.html`、`docs/replays/twocold.html`、`docs/replays/ice-partial.html`、`docs/replays/normal-speed.html` 是可在本地浏览器打开的战术日志视图，可播放或拖动时间轴，查看真实记录的己方、可见敌军和关键事件。它们不包含游戏画面或隐藏敌军信息。

本轮实测、限制与项目成熟度见 `docs/DELIVERY_CN.md`。回退包位于 `rollback/Rusted-Warfare-Agent-0.06-Upgrade.zip`。
