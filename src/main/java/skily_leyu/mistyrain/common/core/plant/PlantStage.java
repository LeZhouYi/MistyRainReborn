package skily_leyu.mistyrain.common.core.plant;

import java.util.Map;
import java.util.Random;

public class PlantStage {

    private PlantStageType nowStage;
    private Map<PlantStageType,Integer> nextStages; //PlantStageType为要转变的下个状态,Integer为此状态的权重
    private Map<PlantStageType,Map<String,Integer>> harvests; //PlantStageType为要转变的下个状态,String为获得的物品，Integer为基础数量

    public boolean isNowStage(PlantStageType stageType){
        return nowStage==stageType;
    }

    public PlantStageType getNowStageType(){
        return this.nowStage;
    }

    /**
     * 随机获取下一个生长状态类型
     * @param random
     * @return
     */
    public PlantStageType getNextStageType(Random random){
        if(this.nextStages!=null&&this.nextStages.size()>0){
            int weight = 0;
            for(int value:this.nextStages.values()){
                weight+=value;
            }
            int next = random.nextInt(weight);
            for(Map.Entry<PlantStageType,Integer> entry:this.nextStages.entrySet()){
                next-=entry.getValue();
                if(next<=0){
                    return entry.getKey();
                }
            }
        }
        return nowStage;
    }

}
