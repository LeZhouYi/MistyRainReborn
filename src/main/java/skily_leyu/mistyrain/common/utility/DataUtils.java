package skily_leyu.mistyrain.common.utility;

import java.util.Map;

public class DataUtils {

    /**
     * 检查Map的KeySet中,True=有以start为开始的Key,False则没有
     * @param map
     * @param start
     * @return
     */
    public static boolean checkStartWiths(Map<String,? extends Object> map,String start){
        for(String key:map.keySet()){
            if(key.startsWith(start)){
                return true;
            }
        }
        return false;
    }

}
