package io.rwagent.client;
import java.util.Map;
/** Fresh opening or existing idle factory -> bounded battle. Child reports remain independent. */
public final class MatchClient {
    public static void main(String[] args){System.exit(run(args));}
    static int run(String[] args){
        try{
            int seconds=BattleBudget.seconds(args);
            int port=Integer.getInteger("rwagent.port",47653);
            AgentClient.Response r=AgentClient.request("GET","http://127.0.0.1:"+port+"/economy/preflight");
            if(r.status!=200)throw new IllegalStateException("Preflight: "+r.body);
            Map<?,?> p=(Map<?,?>)Json.parse(r.body);String recommendation=(String)p.get("recommendation");
            if(!Boolean.TRUE.equals(p.get("commandsAllowed")))throw new IllegalStateException("Preflight: "+r.body);
            if("RUN_ECONOMY_OR_OPENING".equals(recommendation)){
                int code=new EconomyClient().run(new String[0],false);if(code!=0)return code;
                code=new DevelopmentClient(false).run(new String[]{"6","1"});if(code!=0)return code;
            }else if(!"RUN_DEVELOP".equals(recommendation))throw new IllegalStateException("Preflight: "+recommendation);
            return new BattleClient().run(new String[]{String.valueOf(seconds)});
        }catch(Exception e){System.err.println(e);return 1;}
    }
}
