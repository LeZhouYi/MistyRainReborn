package skily_leyu.mistyrain.common.core.potplant;

import java.util.List;

import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class PotPlant {

    private String name; //植物名
    private List<String> seeds; //种子或类种子物品名
    private List<PlantStage> stages; //植物的状态转换表
    private List<Anima> needAnimas; //所需灵气
    private Anima genAnima; //产生的灵气
    private List<String> likeSoils; //喜欢的土壤
    private List<SoilType> suitSoilTypes; //适合的土壤类型
    private String suitWater; //适合的水分
    private int needWater; //消耗的水分
    private int needFertilizer; //消耗的肥料值
    private int[] needTemper; //适宜的生长温度
    private int[] needLight; //适宜的光照

    /**
     * 判断当前物品是否为该植物的种子
     * @param itemStack
     * @return
     */
    public boolean containSeed(ItemStack itemStack){
        return seeds.contains(ItemUtils.getRegistryName(itemStack));
    }

    /**
     * 检查当前物品是否为该植物合适生长的土壤
     * @param dirtStack
     * @return
     */
    public boolean isSuitSoil(ItemStack dirtStack) {
        String dirtName = ItemUtils.getRegistryName(dirtStack);
        if(likeSoils.contains(dirtName)){
            return true;
        }else{
            for(SoilType soilType:suitSoilTypes){
                if(getSoilMap().contains(soilType, dirtStack)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 指向配置文件获取的数据
     * @return
     */
    public SoilMap getSoilMap(){
        return MRSetting.soilMap;
    }

    /**
     * 获取注册名
     * @return
     */
    public String getName() {
        return name;
    }

}
