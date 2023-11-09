package skily_leyu.mistyrain.common.pot;

import java.util.Map;

import javax.annotation.Nullable;

public class PotMap {

    protected Map<String,Pot> map;

    public void setMap(Map<String, Pot> map) {
        this.map = map;
    }

    @Nullable
    public Pot getPot(String name){
        if(this.map.containsKey(name)){
            return this.map.get(name);
        }
        return null;
    }

}
