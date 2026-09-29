# Astra Recon Execution 集成全回归｜2026-09-30

为了避免只验证部署 overlay，本机又从当前 GitHub main 建立 fresh Windows worktree，将 Astra `28e4655` 改动集成后运行完整 `agent/test-win.ps1`。

最终结果：**20 个 Java harness + 15 个 Python suite，failed steps = 0**，总运行约 453.26 s。
其中包括 TargetCompatibility、TerrainMemory、Reachability sandbox、Battle、Recon、Recon Frontier、parallel/headless、A/B、campaign 与 Astra 新增 ExecutionContractHarness（32 checks）。

## 中途发现并修复的 relay 可复现性问题

第一次直接 fresh-checkout Astra 原分支时有 3 个失败，并非桌面候选逻辑退化：
- `py:test_reports`：原 Astra 分支基于较早 main，缺后来补入的历史 acceptance fixtures；
- `TargetCompatibilityHarness` 和 `py:test_target_compatibility`：Windows `core.autocrlf=true` 将知识资源 LF 改成 CRLF，导致 catalog SHA 从 accepted `263cdaaa...` 变成 `ec0b43dd...`，Guard 正确降级 UNKNOWN。

main 已新增 `.gitattributes`：
`agent/resources/knowledge/UNIT_CATALOG_CANDIDATES.json text eol=lf`
fresh worktree 再检出后 catalog SHA 恢复 `263cdaaa3e923e8307c37f059e50bee529b8ecd7c3e215937ce2adf615a0e236`，完整回归全绿。

这不会改变用户已测试的 `feb7ad39...` overlay：该 JAR 原本继承冻结基底中的正确知识资源；修复的是未来 Windows fresh clone / full rebuild 的可复现性。
