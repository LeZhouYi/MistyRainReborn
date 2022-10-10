package skily_leyu.mistyrain.common.core.potplant;

import java.util.Map;

import skily_leyu.mistyrain.common.core.time.MRTimeSpan;

public class PlantStage {

    private PlantStageType nowStage;
    private Map<PlantStageType,Integer> nextStages; //PlantStageType为要转变的下个状态,Integer为此状态的权重
    private MRTimeSpan timeSpan;
    private Map<PlantStageType,Map<String,Integer>> harvests; //PlantStageType为要转变的下个状态,String为获得的物品，Integer为基础数量

    public boolean isStage(PlantStageType type){
        return nowStage==type;
    }

}
