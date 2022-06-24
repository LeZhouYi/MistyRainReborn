package skily_leyu.mistyrain.common.core.potplant;

import java.util.List;

public class PotPlant {

    private String name; //植物名
    private String seed; //种子或类种子物品名
    private List<PlantStage> stages; //植物的状态转换表
    private List<Anima> needAnimas; //所需灵气
    private Anima genAnima; //产生的灵气
    private String likeSoil; //喜欢的土壤
    private String suitSoilType; //适合的土壤类型
    private String suitWater; //适合的水分
    private int needWater; //消耗的水分
    private int needFertilizer; //消耗的肥料值
    private int[] needTemper; //适宜的生长温度
    private int[] needLight; //适宜的光照

}
