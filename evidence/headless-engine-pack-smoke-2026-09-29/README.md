# headless-engine-1.15.zip 本机自检

2026-09-29 在空目录中使用 `tools/prepare_headless_engine.py` 解压并逐文件校验 `astra-relay/headless-engine-1.15.zip`：1209/1209 文件通过，`game-lib.jar` SHA256 匹配冻结基线。

随后使用 main 当前已部署 KnowledgeBacked v0 JAR（SHA256 `5741e241ce8ec1b921f36ed3274087609d0430f9281c44f6f62c096de84c5f54`）执行：

```text
python tools/run_headless.py --game-dir <fresh extracted pack> --agent-jar <stable jar> --mode smoke --speed 4 --timeout 120
```

结果：`PASS`；Small Island (2p) 成功加载，preflight exit 0，roundtrip exit 0，2 条移动命令与 7 次观察通过离线报告校验；引擎最终退出码 0，没有残留进程。

本证据只证明这个公开 headless 资源包足以启动本项目原生 1.15 无画面实验，不证明 Astra 最新 ReconExecution 候选已经通过 E4；其桌面/完整 match 验收仍需单独进行。
