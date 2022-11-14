package skily_leyu.mistyrain.common.core.plant;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.common.core.anima.Anima;
import skily_leyu.mistyrain.common.core.soil.SoilType;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.common.utility.MathUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class Plant {

    private String name; //植物名
    private List<String> seeds; //种子或类种子物品名
    private List<PlantStage> stages; //植物的状态转换表
    private List<Anima> needAnimas; //所需灵气
    private List<Anima> genAnimas; //产生的灵气
    private List<String> likeSoils; //喜欢的土壤
    private List<SoilType> suitSoilTypes; //适合的土壤类型
    private String suitWater; //适合的水分
    private int needWater; //消耗的水分
    private int needFertilizer; //消耗的肥料值
    private List<TemperType> needTemper; //适宜的生长温度
    private int[] needLight; //适宜的光照

    /**
     * 检查当前温度是否合适
     * @param temperType
     * @return
     */
    public boolean isSuitTemper(float temperIn){
        TemperType temperType = TemperType.getTemperType(temperIn);
        return this.needTemper.contains(temperType);
    }

    /**
     * 判断光照是否合适
     * @param light
     * @return
     */
    public boolean isSuitLight(int light){
        return MathUtils.isBetween(needLight, light);
    }

    /**
     * 获取消耗水份的量
     * @return
     */
    public int getNeedWater(){
        return this.needWater;
    }

    /**
     * 获取该植物的BlockState,其中meta=0时，若状态为SeedDrop则为null，默认不渲染模型
     * @param meta
     * @return
     */
    public BlockState getBlockState(int meta){
        if(stages.size()<1){
            return null;
        }
        if(meta==0&&stages.get(0).isStage(PlantStageType.SEED_DROP)){
            return null;
        }
        Block plantBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(getName()));
        if(plantBlock!=null){
            return plantBlock.getStateDefinition().getPossibleStates().get(meta);
        }
        return null;
    }

    /**
     * 获取下一生长阶段
     * @param nowStage
     * @param random
     * @return
     */
    public int getNextStage(int nowStage,Random random){
        if(this.stages!=null&&nowStage<this.stages.size()){
            PlantStage nowPlantStage = this.stages.get(nowStage);
            PlantStageType nextStageType = nowPlantStage.getNextStageType(random);
            for(int i = 0;i<this.stages.size();i++){
                if(this.stages.get(i).isNowStage(nextStageType)){
                    return i;
                }
            }
        }
        return nowStage;
    }

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
                if(MRSetting.getSoilMap().contains(soilType, dirtStack)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取注册名
     * @return
     */
    public String getName() {
        return name;
    }

}
