# 铁锈战争 Agent 离线报告汇总

结果由事件核对。INVALID 表示报告不完整或计数异常，即使原始 outcome 为 PASS 也不计入通过。

| 报告 | 版本 | 任务 | 判定 | 现实秒数 | 指令 | 观察 | 新矿 | 新厂 | 新坦克 | 建矿时完成坦克 |
| --- | --- | --- | --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| battle-1790741949748-c01f222f.jsonl | 0.07-alpha1 | battle | PARTIAL | 480.463 | 703 | 882 | 11 | 2 | 0 | 0 |
| battle-1790742800972-4064cebd.jsonl | 0.07-alpha1 | battle | PARTIAL | 480.377 | 631 | 867 | 4 | 1 | 0 | 0 |
| battle-1790743783098-ae01b65e.jsonl | 0.07-alpha1 | battle | PARTIAL | 480.47 | 648 | 844 | 13 | 3 | 0 | 0 |
| development-1790741937274-7d1c4895.jsonl | 0.07-alpha1 | development | PASS | 12.456 | 7 | 27 | 1 | 0 | 6 | 2 |
| development-1790742788591-990b02bb.jsonl | 0.07-alpha1 | development | PASS | 12.363 | 7 | 27 | 1 | 0 | 6 | 2 |
| development-1790743770680-5e51fd16.jsonl | 0.07-alpha1 | development | PASS | 12.4 | 7 | 27 | 1 | 0 | 6 | 2 |
| economy-1790741927149-0bc234be.jsonl | 0.07-alpha1 | economy | PASS | 9.8 | 2 | 22 | 0 | 1 | 1 | 0 |
| economy-1790742779539-61447a69.jsonl | 0.07-alpha1 | economy | PASS | 8.735 | 2 | 20 | 0 | 1 | 1 | 0 |
| economy-1790743761630-ec51c708.jsonl | 0.07-alpha1 | economy | PASS | 8.733 | 2 | 20 | 0 | 1 | 1 | 0 |

现实耗时从首个有效事件到最后一个有效事件计算，不是游戏内时间。建矿出兵重叠仅指日志观察区间：同一矿的 extractor_started 到 extractor_completed 之间出现 tank_completed；不等于性能或胜率证明。

| 对战报告 | 原生结算 | 对战游戏秒数 | 新观察战斗单位 | 己方损失 | 已确认进攻指令 | 工厂升级 |
| --- | --- | ---: | ---: | ---: | ---: | ---: |
| battle-1790741949748-c01f222f.jsonl | ONGOING | 2402.295 | 127 | 64 | 264 | 4 |
| battle-1790742800972-4064cebd.jsonl | ONGOING | 2401.865 | 237 | 243 | 240 | 2 |
| battle-1790743783098-ae01b65e.jsonl | ONGOING | 2402.32 | 139 | 82 | 227 | 4 |

对战 PASS 只表示已观察到原生结算。VICTORY 才是获胜；DEFEAT 是完整败局；PARTIAL 是仍在进行的限时样本。

## battle-1790741949748-c01f222f.jsonl

**阶段** battle；**扩张** —

Game-time budget reached without native result

原始 summary

```json
{
  "outcome": "PARTIAL",
  "reason": "Game-time budget reached without native result",
  "phase": "battle",
  "matchOutcome": "ONGOING",
  "commands": 703,
  "observations": 882,
  "ownLosses": 64,
  "newCombatUnits": 127,
  "attackOrders": 289,
  "attackOrdersConfirmed": 264,
  "retreatOrders": 21,
  "upgradesCompleted": 4,
  "completedMines": 11,
  "newMines": 11,
  "mineTarget": 3,
  "minesBeyondFloor": 9,
  "expansionRefusals": 148,
  "expansionBlocked": 156,
  "completedFactories": 2,
  "newFactories": 2,
  "landFactoryTarget": 4,
  "factoryBlocks": 0,
  "builderTarget": 1,
  "builderRecoveries": 0,
  "builderOrders": 0,
  "battleGameTimeMs": 2402295,
  "gameSecondsPerWallSecond": 5.0,
  "noProgressWindowMs": 20000,
  "noProgressCooldownMs": 30000,
  "targetNoProgressTriggers": 0,
  "targetRetries": 0,
  "targetSuppressionsStarted": 13,
  "targetSuppressionsReleased": 0,
  "targetSuppressedSelections": 2173,
  "targetSuppressionsAtEnd": 13,
  "targetSwitches": 101,
  "effectiveDecisionIntervalGameMs": 2920,
  "noProgressAttentionGapMs": 8760,
  "maxObservedGameTimeJump": 3055,
  "observationCount": 882,
  "spendTotal": 151350,
  "spendByCategory": {
    "FACTORY_UPGRADE": {
      "orders": 4,
      "cost": 8000
    },
    "UNIT_PRODUCTION": {
      "orders": 123,
      "cost": 85350
    },
    "NEW_MINE": {
      "orders": 9,
      "cost": 6300
    },
    "NEW_FACTORY": {
      "orders": 2,
      "cost": 1400
    },
    "STRATEGIC_CAPABILITY": {
      "orders": 7,
      "cost": 15500
    },
    "STRATEGIC_CONSTRUCTION": {
      "orders": 18,
      "cost": 18000
    },
    "MINE_UPGRADE": {
      "orders": 12,
      "cost": 16800
    }
  },
  "measuredIncomePerGameSecond": 164.64,
  "investmentIntentions": 15,
  "investmentCompletions": 2,
  "investmentCancellations": 13,
  "investmentReserveAtEnd": 0,
  "investmentPendingAtEnd": false,
  "finalUrgency": "CONTESTED",
  "reconEnabled": true,
  "reconTasksCreated": 94,
  "reconOrdersQueued": 123,
  "reconOrdersObserved": 1,
  "reconMovesCompletedBetweenSamples": 122,
  "frontierTasksSatisfiedByTeamVision": 75,
  "reconResolvedAfterObservedMove": 1,
  "reconReacquired": 2,
  "reconSitesCleared": 0,
  "reconBlocked": 0,
  "reconTaskPendingAtEnd": false,
  "reconFrontierEnabled": true,
  "frontierTasksCreated": 92,
  "frontierTasksRefreshed": 0,
  "frontierTasksAdvanced": 9,
  "frontierTasksBlocked": 5,
  "frontierTasksPreempted": 3,
  "expendableTransfers": 4,
  "expendableScoutsAtEnd": 1,
  "factoryTargetIncreases": 2,
  "factoryTargetIncreaseBlocks": 3,
  "landFactoryTargetAtEnd": 4,
  "factoryTargetCommittedAtEnd": false,
  "landFactoryTargetMax": 5,
  "factorySaturationPct": 6.87,
  "factorySaturationMinPct": 80,
  "economyBaseIncome": 26.9,
  "economyIncomePerMine": 12.07,
  "economyModelSource": "MEASURED_4_MATCHES_2_SPEEDS_T1_R2_0.999_T2_T3_ESTIMATED_FROM_FROZEN_GENERATION_RATIOS",
  "minesReadyAtEnd": 11,
  "productionConsumptionPerGameSecond": 26.7,
  "sustainableSurplusPerGameSecond": 175.2,
  "strategy": {
    "enabled": true,
    "armyTarget": 78,
    "activeTarget": 66,
    "reserveTarget": 12,
    "hardSafetyCap": 128,
    "builderTarget": 1,
    "capabilityNeedsAtEnd": 13,
    "needsResolvedByLegalEvidence": 1,
    "investmentOrders": 37,
    "mineUpgradesObserved": 12,
    "constructionCompletionsObserved": 17
  }
}
```

## battle-1790742800972-4064cebd.jsonl

**阶段** battle；**扩张** —

Game-time budget reached without native result

原始 summary

```json
{
  "outcome": "PARTIAL",
  "reason": "Game-time budget reached without native result",
  "phase": "battle",
  "matchOutcome": "ONGOING",
  "commands": 631,
  "observations": 867,
  "ownLosses": 243,
  "newCombatUnits": 237,
  "attackOrders": 241,
  "attackOrdersConfirmed": 240,
  "retreatOrders": 66,
  "upgradesCompleted": 2,
  "completedMines": 4,
  "newMines": 4,
  "mineTarget": 3,
  "minesBeyondFloor": 0,
  "expansionRefusals": 111,
  "expansionBlocked": 145,
  "completedFactories": 1,
  "newFactories": 1,
  "landFactoryTarget": 2,
  "factoryBlocks": 0,
  "builderTarget": 1,
  "builderRecoveries": 3,
  "builderOrders": 3,
  "battleGameTimeMs": 2401865,
  "gameSecondsPerWallSecond": 5.0,
  "noProgressWindowMs": 20000,
  "noProgressCooldownMs": 30000,
  "targetNoProgressTriggers": 0,
  "targetRetries": 0,
  "targetSuppressionsStarted": 12,
  "targetSuppressionsReleased": 3,
  "targetSuppressedSelections": 68,
  "targetSuppressionsAtEnd": 9,
  "targetSwitches": 184,
  "effectiveDecisionIntervalGameMs": 2825,
  "noProgressAttentionGapMs": 8475,
  "maxObservedGameTimeJump": 3040,
  "observationCount": 867,
  "spendTotal": 147100,
  "spendByCategory": {
    "FACTORY_UPGRADE": {
      "orders": 2,
      "cost": 4000
    },
    "NEW_MINE": {
      "orders": 5,
      "cost": 3500
    },
    "UNIT_PRODUCTION": {
      "orders": 237,
      "cost": 137400
    },
    "NEW_FACTORY": {
      "orders": 1,
      "cost": 700
    },
    "BUILDER_RECOVERY": {
      "orders": 3,
      "cost": 1500
    }
  },
  "measuredIncomePerGameSecond": 60.33,
  "investmentIntentions": 0,
  "investmentCompletions": 0,
  "investmentCancellations": 0,
  "investmentReserveAtEnd": 0,
  "investmentPendingAtEnd": false,
  "finalUrgency": "CONTESTED",
  "reconEnabled": true,
  "reconTasksCreated": 23,
  "reconOrdersQueued": 32,
  "reconOrdersObserved": 24,
  "reconMovesCompletedBetweenSamples": 8,
  "frontierTasksSatisfiedByTeamVision": 0,
  "reconResolvedAfterObservedMove": 8,
  "reconReacquired": 17,
  "reconSitesCleared": 0,
  "reconBlocked": 2,
  "reconTaskPendingAtEnd": true,
  "reconFrontierEnabled": true,
  "frontierTasksCreated": 3,
  "frontierTasksRefreshed": 0,
  "frontierTasksAdvanced": 0,
  "frontierTasksBlocked": 1,
  "frontierTasksPreempted": 2,
  "expendableTransfers": 2,
  "expendableScoutsAtEnd": 0,
  "factoryTargetIncreases": 0,
  "factoryTargetIncreaseBlocks": 1,
  "landFactoryTargetAtEnd": 2,
  "factoryTargetCommittedAtEnd": false,
  "landFactoryTargetMax": 5,
  "factorySaturationPct": 41.38,
  "factorySaturationMinPct": 80,
  "economyBaseIncome": 26.9,
  "economyIncomePerMine": 12.07,
  "economyModelSource": "MEASURED_4_MATCHES_2_SPEEDS_T1_R2_0.999_T2_T3_ESTIMATED_FROM_FROZEN_GENERATION_RATIOS",
  "minesReadyAtEnd": 2,
  "productionConsumptionPerGameSecond": 38.3,
  "sustainableSurplusPerGameSecond": 12.7,
  "strategy": {
    "enabled": true,
    "armyTarget": 35,
    "activeTarget": 27,
    "reserveTarget": 8,
    "hardSafetyCap": 128,
    "builderTarget": 1,
    "capabilityNeedsAtEnd": 6,
    "needsResolvedByLegalEvidence": 0,
    "investmentOrders": 0,
    "mineUpgradesObserved": 0,
    "constructionCompletionsObserved": 0
  }
}
```

## battle-1790743783098-ae01b65e.jsonl

**阶段** battle；**扩张** —

Game-time budget reached without native result

原始 summary

```json
{
  "outcome": "PARTIAL",
  "reason": "Game-time budget reached without native result",
  "phase": "battle",
  "matchOutcome": "ONGOING",
  "commands": 648,
  "observations": 844,
  "ownLosses": 82,
  "newCombatUnits": 139,
  "attackOrders": 242,
  "attackOrdersConfirmed": 227,
  "retreatOrders": 17,
  "upgradesCompleted": 4,
  "completedMines": 13,
  "newMines": 13,
  "mineTarget": 3,
  "minesBeyondFloor": 11,
  "expansionRefusals": 111,
  "expansionBlocked": 130,
  "completedFactories": 3,
  "newFactories": 3,
  "landFactoryTarget": 4,
  "factoryBlocks": 0,
  "builderTarget": 1,
  "builderRecoveries": 1,
  "builderOrders": 1,
  "battleGameTimeMs": 2402320,
  "gameSecondsPerWallSecond": 5.0,
  "noProgressWindowMs": 20000,
  "noProgressCooldownMs": 30000,
  "targetNoProgressTriggers": 0,
  "targetRetries": 0,
  "targetSuppressionsStarted": 2,
  "targetSuppressionsReleased": 0,
  "targetSuppressedSelections": 34,
  "targetSuppressionsAtEnd": 2,
  "targetSwitches": 140,
  "effectiveDecisionIntervalGameMs": 2940,
  "noProgressAttentionGapMs": 8820,
  "maxObservedGameTimeJump": 3610,
  "observationCount": 844,
  "spendTotal": 172150,
  "spendByCategory": {
    "FACTORY_UPGRADE": {
      "orders": 4,
      "cost": 8000
    },
    "NEW_MINE": {
      "orders": 12,
      "cost": 8400
    },
    "UNIT_PRODUCTION": {
      "orders": 136,
      "cost": 99350
    },
    "NEW_FACTORY": {
      "orders": 3,
      "cost": 2100
    },
    "STRATEGIC_CAPABILITY": {
      "orders": 9,
      "cost": 13500
    },
    "STRATEGIC_CONSTRUCTION": {
      "orders": 30,
      "cost": 22100
    },
    "MINE_UPGRADE": {
      "orders": 13,
      "cost": 18200
    },
    "BUILDER_RECOVERY": {
      "orders": 1,
      "cost": 500
    }
  },
  "measuredIncomePerGameSecond": 156.33,
  "investmentIntentions": 18,
  "investmentCompletions": 5,
  "investmentCancellations": 13,
  "investmentReserveAtEnd": 0,
  "investmentPendingAtEnd": false,
  "finalUrgency": "CONTESTED",
  "reconEnabled": true,
  "reconTasksCreated": 51,
  "reconOrdersQueued": 65,
  "reconOrdersObserved": 22,
  "reconMovesCompletedBetweenSamples": 43,
  "frontierTasksSatisfiedByTeamVision": 34,
  "reconResolvedAfterObservedMove": 5,
  "reconReacquired": 5,
  "reconSitesCleared": 2,
  "reconBlocked": 0,
  "reconTaskPendingAtEnd": true,
  "reconFrontierEnabled": true,
  "frontierTasksCreated": 43,
  "frontierTasksRefreshed": 1,
  "frontierTasksAdvanced": 2,
  "frontierTasksBlocked": 5,
  "frontierTasksPreempted": 1,
  "expendableTransfers": 2,
  "expendableScoutsAtEnd": 0,
  "factoryTargetIncreases": 2,
  "factoryTargetIncreaseBlocks": 2,
  "landFactoryTargetAtEnd": 4,
  "factoryTargetCommittedAtEnd": false,
  "landFactoryTargetMax": 5,
  "factorySaturationPct": 0.0,
  "factorySaturationMinPct": 80,
  "economyBaseIncome": 26.9,
  "economyIncomePerMine": 12.07,
  "economyModelSource": "MEASURED_4_MATCHES_2_SPEEDS_T1_R2_0.999_T2_T3_ESTIMATED_FROM_FROZEN_GENERATION_RATIOS",
  "minesReadyAtEnd": 16,
  "productionConsumptionPerGameSecond": 0.0,
  "sustainableSurplusPerGameSecond": 256.2,
  "strategy": {
    "enabled": true,
    "armyTarget": 92,
    "activeTarget": 80,
    "reserveTarget": 12,
    "hardSafetyCap": 128,
    "builderTarget": 2,
    "capabilityNeedsAtEnd": 2,
    "needsResolvedByLegalEvidence": 0,
    "investmentOrders": 52,
    "mineUpgradesObserved": 13,
    "constructionCompletionsObserved": 30
  }
}
```

## development-1790741937274-7d1c4895.jsonl

**阶段** tank_observe；**扩张** LIMIT_REACHED

Tank target completed; mines 1/1; expansion LIMIT_REACHED; all task units alive

原始 summary

```json
{
  "outcome": "PASS",
  "reason": "Tank target completed; mines 1/1; expansion LIMIT_REACHED; all task units alive",
  "phase": "tank_observe",
  "commands": 7,
  "observations": 27,
  "completedMines": 1,
  "completedTanks": 6,
  "expansionStatus": "LIMIT_REACHED",
  "scoutMoves": 0,
  "scoutArrivals": 0,
  "scoutBlocked": 0,
  "scoutedMines": 0,
  "scoutRetreats": 0,
  "escortsAssigned": 0,
  "escortsConfirmed": 0,
  "newlyExploredTiles": 0
}
```

## development-1790742788591-990b02bb.jsonl

**阶段** tank_observe；**扩张** LIMIT_REACHED

Tank target completed; mines 1/1; expansion LIMIT_REACHED; all task units alive

原始 summary

```json
{
  "outcome": "PASS",
  "reason": "Tank target completed; mines 1/1; expansion LIMIT_REACHED; all task units alive",
  "phase": "tank_observe",
  "commands": 7,
  "observations": 27,
  "completedMines": 1,
  "completedTanks": 6,
  "expansionStatus": "LIMIT_REACHED",
  "scoutMoves": 0,
  "scoutArrivals": 0,
  "scoutBlocked": 0,
  "scoutedMines": 0,
  "scoutRetreats": 0,
  "escortsAssigned": 0,
  "escortsConfirmed": 0,
  "newlyExploredTiles": 0
}
```

## development-1790743770680-5e51fd16.jsonl

**阶段** tank_observe；**扩张** LIMIT_REACHED

Tank target completed; mines 1/1; expansion LIMIT_REACHED; all task units alive

原始 summary

```json
{
  "outcome": "PASS",
  "reason": "Tank target completed; mines 1/1; expansion LIMIT_REACHED; all task units alive",
  "phase": "tank_observe",
  "commands": 7,
  "observations": 27,
  "completedMines": 1,
  "completedTanks": 6,
  "expansionStatus": "LIMIT_REACHED",
  "scoutMoves": 0,
  "scoutArrivals": 0,
  "scoutBlocked": 0,
  "scoutedMines": 0,
  "scoutRetreats": 0,
  "escortsAssigned": 0,
  "escortsConfirmed": 0,
  "newlyExploredTiles": 0
}
```

## economy-1790741927149-0bc234be.jsonl

**阶段** tank_1_produce；**扩张** —

New landFactory completed; its queue was observed active then empty; one new own tank appeared nearby

原始 summary

```json
{
  "outcome": "PASS",
  "reason": "New landFactory completed; its queue was observed active then empty; one new own tank appeared nearby",
  "phase": "tank_1_produce",
  "commands": 2,
  "observations": 22,
  "completedBuildings": 1,
  "completedTanks": 1
}
```

## economy-1790742779539-61447a69.jsonl

**阶段** tank_1_produce；**扩张** —

New landFactory completed; its queue was observed active then empty; one new own tank appeared nearby

原始 summary

```json
{
  "outcome": "PASS",
  "reason": "New landFactory completed; its queue was observed active then empty; one new own tank appeared nearby",
  "phase": "tank_1_produce",
  "commands": 2,
  "observations": 20,
  "completedBuildings": 1,
  "completedTanks": 1
}
```

## economy-1790743761630-ec51c708.jsonl

**阶段** tank_1_produce；**扩张** —

New landFactory completed; its queue was observed active then empty; one new own tank appeared nearby

原始 summary

```json
{
  "outcome": "PASS",
  "reason": "New landFactory completed; its queue was observed active then empty; one new own tank appeared nearby",
  "phase": "tank_1_produce",
  "commands": 2,
  "observations": 20,
  "completedBuildings": 1,
  "completedTanks": 1
}
```
