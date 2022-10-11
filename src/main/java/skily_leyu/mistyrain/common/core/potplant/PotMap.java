package skily_leyu.mistyrain.common.core.potplant;

import java.util.Map;

import javax.annotation.Nullable;

public class PotMap {

    private Map<String,Pot> potMap;

    @Nullable
    public Pot getPot(String name){
        if(this.potMap.containsKey(name)){
            return this.potMap.get(name);
        }
        return null;
    }

}
