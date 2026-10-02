package io.rwagent.client;

import java.util.Map;

/** One actor's execution phase. Requested transitions never prove native mode completion. */
public final class CapabilityUnitMode {
    public enum Phase { UNKNOWN, AIR_READY, MOVE_TO_WATER, DIVE_REQUESTED, SUBMERGED_READY, FLY_REQUESTED, REPOSITIONING }
    public final long unitId;
    private Phase phase=Phase.UNKNOWN;
    private String nativeMode="UNKNOWN",evidence="NEEDS_EVIDENCE";
    private Long witnessedAt;
    public CapabilityUnitMode(long unitId){if(unitId<0)throw new IllegalArgumentException("unitId");this.unitId=unitId;}
    public Phase phase(){return phase;}
    public String nativeMode(){return nativeMode;}
    public void approachingWater(){phase=Phase.MOVE_TO_WATER;}
    public void diveAccepted(){phase=Phase.DIVE_REQUESTED;}
    public void flyAccepted(){phase=Phase.FLY_REQUESTED;}
    public void repositioning(){if(phase!=Phase.FLY_REQUESTED)phase=Phase.REPOSITIONING;}
    /** Only a validated own native unit-modes packet or explicit own movement field is proof.
     * Compatibility/legacy "DIVE observed" labels do not identify current actor mode. */
    public boolean witness(Map<String,Object> response,Map<String,Object> actor,long now){
        Object time=response.get("gameTimeMs");
        if(!(time instanceof Number)||((Number)time).longValue()<now)return false;
        Object id=actor==null?response.get("unitId"):actor.get("unitId");
        if(!(id instanceof Number)||((Number)id).longValue()!=unitId)return false;
        Object submerged=actor==null?response.get("submergedWeaponAvailable"):null;
        String movement=actor==null?null:String.valueOf(actor.get("movementType"));
        String observed=Boolean.TRUE.equals(submerged)||"WATER".equals(movement)?"SUBMERGED":
                Boolean.FALSE.equals(submerged)||"AIR".equals(movement)?"AIR":"UNKNOWN";
        if("UNKNOWN".equals(observed))return false;
        nativeMode=observed;witnessedAt=((Number)time).longValue();
        evidence=actor==null?"NATIVE_OWN_SUBMERGED_WEAPON_FIELD":"NATIVE_OWN_MOVEMENT_FIELD";
        if("SUBMERGED".equals(observed)&&phase!=Phase.FLY_REQUESTED)phase=Phase.SUBMERGED_READY;
        if("AIR".equals(observed)&&phase!=Phase.DIVE_REQUESTED&&phase!=Phase.MOVE_TO_WATER)phase=Phase.AIR_READY;
        return true;
    }
    public Map<String,Object> snapshot(){return StrategyDirector.map("unitId",unitId,"phase",phase.name(),"nativeMode",nativeMode,
            "modeEvidence",evidence,"witnessedAtGameTimeMs",witnessedAt,"nativeModeEvidenceSemantics","LAST_EXPLICIT_NATIVE_WITNESS_NOT_CURRENT_PREDICTION","receiptIsNativeModeProof",false);}
}
