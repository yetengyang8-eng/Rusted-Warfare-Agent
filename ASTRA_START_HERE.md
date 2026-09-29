# Astra 中转入口

本仓库是 `G:\deepseek 工作台` 的公开工程中转镜像，供无法访问用户本机文件系统的 Astra / Codex / DeepSeek 使用。

## 当前任务与候选
1. `ASTRA_BREAKTHROUGH_MISSION_2026-09-29.md` — 高自主性突破任务。
2. `ASTRA_PROJECT_REVIEW_RESPONSE_2026-09-29.md` — 上一轮项目总评。
3. `handoff/HANDOFF_Astra_ReconExecution_2026-09-29.md` — Astra 最新 Recon 执行层交付说明。
4. 分支 `astra/recon-execution-20260929` — 最新待原生验收源码候选，commit `28e4655`；不要把它误认为已部署 main。
5. `astra-deliveries/recon-execution-2026-09-29/` — Astra 原始交付 ZIP 与 handoff 归档。

## 当前已部署基线
- KnowledgeBacked v0 Agent JAR SHA256: `5741e241ce8ec1b921f36ed3274087609d0430f9281c44f6f62c096de84c5f54`
- 兼容 1.15 `game-lib.jar` SHA256: `8a550a37e2d8a5430866090d4e7d5892f9010b47f52a5a09350fc66c620deec9`
- `project-state/CURRENT_STATE.md`、`project-state/HANDOFF_FOR_CODEX.md` 是状态速查。
- `evidence/README.md` 索引桌面 raw battle 与 KnowledgeBacked / Recon 证据。

## 现在可以自己跑原生 headless
`astra-relay/headless-engine-1.15.zip` 提供 `tools/run_headless.py` 真正需要的 `game-lib.jar + assets + libs + res`；无需 G: 盘。
先读 `astra-relay/HEADLESS_ENGINE_README.md`，再运行：
```text
python tools/prepare_headless_engine.py --out .engine/rw115
python tools/run_headless.py --game-dir .engine/rw115 --agent-jar YOUR_AGENT.jar --mode smoke --speed 4 --timeout 120 --out headless-runs/smoke
```
## 工程历史与外部资料
- `project-history/raw-dialogue/` — 用户明确授权公开的本项目原始 `对话N / 输出N / HANDOFF_对话N`，约 0.6 MiB。只作历史检索，不能覆盖最新证据。
- `knowledge/` — Knowledge Packet、静态核验与来源清单。
- `THIRD_PARTY_REFERENCES.md` — 7 个主要公开第三方仓库及用途；不需要重复镜像进本仓库。
- `REPLAY_CORPUS_README.md` / `replay-corpus-manifest.json` — 本机九图真人 Replay 语料的存在、规模和边界；全量语料未镜像。

## 1.15 引擎身份
`HEADLESS_ENGINE_MANIFEST.json` 对 headless 包的 1209 个文件逐个记录 SHA256。冻结 `game-lib.jar` 的 Git blob SHA1 为 `4d4034ac49311bfcb8870f49a56d4d01d622b3ec`，与公开 `TapeRTS/Tape/1.15/game-lib.jar` 完全相同。

本机已经从这个 ZIP 解压到空目录并运行了一次 `run_headless.py --mode smoke --speed 4`：原生引擎启动、Small Island 加载、roundtrip、报告校验、退出均 PASS。因此“远程模型没有原版引擎依赖”不再是 headless 验证的结构性阻塞。

## 不变的安全边界
战争迷雾、UNKNOWN、控制权限和证据等级继续执行；不要因获得完整静态资源而把未探索地图/隐藏动态敌情直接喂给策略。不要重新启用已隔离的危险反射探针。

最新 Astra ReconExecution 仍是 **待原生验收候选**；E2 通过不等于桌面 E4 或胜率提升。若从该候选继续工作，请优先使用它自己的分支，不要直接覆盖 main 的稳定基线。

## 直接回写
若 Astra 环境已经连接 GitHub 并获得本仓库写权限，按 `ASTRA_WRITEBACK.md` 在 `astra/<task>-YYYYMMDD` 分支提交，不直接覆盖稳定 main。没有写凭据时继续交付 patch/ZIP，由中转端落分支。
