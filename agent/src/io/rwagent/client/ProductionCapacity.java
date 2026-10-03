package io.rwagent.client;

import java.util.Map;
import static io.rwagent.client.StrategyDirector.*;

/** Small diagnosis shared by the existing military-target and factory-target controllers.
 * Persistent income/demand evidence and utilisation are supplied by BattleClient's windows.
 * UNKNOWN idle time is not evidence of an operational or producer bottleneck.
 */
final class ProductionCapacity {
    /** Tech conversion uses the current own force and the price of its legal replacement route.
     * A productive army can fund a native upgrade before an arbitrary ten-unit savings fence.
     * The quote authorizes only the upgrade; it never proves a future product or throughput. */
    static final class TechInvestment {
        final boolean selected,bank;final String reason;
        final Map<String,Object> evidence;
        TechInvestment(boolean selected,boolean bank,String reason,Map<String,Object> evidence){
            this.selected=selected;this.bank=bank;this.reason=reason;this.evidence=evidence;
            evidence.put("selected",selected);evidence.put("bank",bank);evidence.put("reason",reason);
        }
    }
    static TechInvestment techInvestment(Map<String,Object> upgrade,int armed,int healthy,double credits,
            double reserved,double replacementCost,double income,double consumption,boolean recovery){
        double cost=number(upgrade,"cost",Double.NaN);
        double required=cost+reserved+replacementCost;
        double surplus=income-consumption;
        double fundingSeconds=Double.isFinite(required)&&surplus>0?Math.max(0,required-credits)/surplus:Double.NaN;
        Map<String,Object> evidence=map("nativeUpgradeCost",Double.isFinite(cost)?cost:null,
            "ordinaryReplacementNativeCost",Double.isFinite(replacementCost)?replacementCost:null,
            "armed",armed,"healthyArmed",healthy,"credits",credits,"allReserved",reserved,
            "incomeEstimate",income,"productionConsumptionPerGameSecond",consumption,
            "protectedRequiredCredits",Double.isFinite(required)?required:null,
            "fundingEstimateGameSeconds",Double.isFinite(fundingSeconds)?fundingSeconds:null,
            "quoteSource","LEGAL_NATIVE_MENU","futureProductStatus","UNKNOWN",
            "fundingModel","ESTIMATED_INCOME_MINUS_ACCEPTED_PRODUCTION_CONSUMPTION",
            "fundingEstimateStatus","MODEL_ESTIMATE_NOT_NATIVE_THROUGHPUT");
        String reason=null;
        if(upgrade==null||!"upgrade".equals(upgrade.get("type"))||!(upgrade.get("actionId") instanceof String)
                ||!Double.isFinite(cost)||cost<=0)reason="NATIVE_UPGRADE_UNKNOWN";
        else if(!Double.isFinite(credits)||!Double.isFinite(reserved)||reserved<0
                ||!Double.isFinite(replacementCost)||replacementCost<=0
                ||!Double.isFinite(income)||income<=0||!Double.isFinite(consumption))reason="CONVERSION_BUDGET_UNKNOWN";
        else if(recovery)reason="RECOVERY_PROTECTED";
        else if(armed<6||healthy<6||healthy<armed*.6)reason="HEALTHY_FORCE_RECOVERY";
        if(reason!=null)return new TechInvestment(false,false,reason,evidence);
        if(Boolean.TRUE.equals(upgrade.get("affordable"))&&credits>=required)
            return new TechInvestment(true,false,"OWN_FORCE_SUPPORTED_NATIVE_TECH_CONVERSION",evidence);
        // Do not stand idle saving from cash alone. A bounded, income-funded wait is permitted
        // after the useful force exists; protected funds and one replacement are kept intact.
        boolean bank=surplus>0&&Double.isFinite(fundingSeconds)&&fundingSeconds<=30;
        return new TechInvestment(false,bank,bank?"BOUNDED_INCOME_FUNDED_TECH_WAIT":"PRODUCTION_BEFORE_UNFUNDED_TECH",evidence);
    }
    enum Bottleneck { MONEY_LIMITED, RESERVE_PROTECTED, NO_USEFUL_DEMAND, ARMY_CAPACITY_LIMIT,
        HARD_SAFETY_CAP, PRODUCER_THROUGHPUT_LIMIT, PRODUCER_TECH_LIMIT, ROUTE_UNAVAILABLE,
        CAPACITY_EXPANSION_COMMITTED, UNKNOWN }
    static Bottleneck classify(int committed,int target,int hard,String demand,int routes,
            boolean techBlocked,boolean budgetKnown,double credits,double cost,double reserved,
            boolean recovery,boolean expansion,boolean sustained,double utilization,int saturation){
        if(committed>=hard)return Bottleneck.HARD_SAFETY_CAP;
        if("NONE".equals(demand))return Bottleneck.NO_USEFUL_DEMAND;
        if("ROUTE_UNAVAILABLE".equals(demand))return Bottleneck.ROUTE_UNAVAILABLE;
        if(!"KNOWN".equals(demand))return Bottleneck.UNKNOWN;
        if(routes==0)return techBlocked?Bottleneck.PRODUCER_TECH_LIMIT:Bottleneck.ROUTE_UNAVAILABLE;
        if(!budgetKnown)return Bottleneck.UNKNOWN;
        if(credits<cost)return Bottleneck.MONEY_LIMITED;
        if(recovery||credits-cost<reserved)return Bottleneck.RESERVE_PROTECTED;
        if(expansion)return Bottleneck.CAPACITY_EXPANSION_COMMITTED;
        if(committed>=target)return Bottleneck.ARMY_CAPACITY_LIMIT;
        if(sustained&&utilization>=saturation)return Bottleneck.PRODUCER_THROUGHPUT_LIMIT;
        return Bottleneck.UNKNOWN;
    }
}
