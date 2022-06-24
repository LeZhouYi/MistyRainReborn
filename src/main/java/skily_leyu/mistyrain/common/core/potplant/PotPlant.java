package skily_leyu.mistyrain.common.core.potplant;

import java.util.List;

public class PotPlant {

    private String name; //植物名
    private String seed; //种子或类种子物品名
    private List<PlantStage> stages; //植物的状态转换表
    private List<Anima> needAnimas; //所需灵气
    private Anima genAnima; //产生的灵气

}
