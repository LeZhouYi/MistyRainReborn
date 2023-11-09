package skily_leyu.mistyrain.common.pot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.common.plant.Plant;
import skily_leyu.mistyrain.common.plant.PlantStageType;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRSetting;
import skily_leyu.mistyrain.tileentity.TilePotBase;

public class PotPlantStage {

    private int nowStage; // 当前状态
    private final String plantKey; // 当前植物

    public PotPlantStage(int nowStage, String plantKey) {
        this.nowStage = nowStage;
        this.plantKey = plantKey;
    }

    /**
     * 执行植物生长的判定，消耗，状态更新
     */
    public void tick(TilePotBase tileEntity) {
        Plant plant = MRSetting.getPlantMap().getPlant(plantKey);
        World world = tileEntity.getLevel();
        // 是否满足生长到下一阶段
        if (plant != null && world != null && MRConfig.PotRule.canGrow(world.getRandom())) {
            this.nowStage = plant.getNextStage(nowStage, world.getRandom());
        }
    }

    /**
     * 获取当前植物的配置数据
     */
    public Plant getPlant() {
        return MRSetting.getPlantMap().getPlant(plantKey);
    }

    public int getState() {
        return nowStage;
    }

    public CompoundNBT save() {
        CompoundNBT plantTag = new CompoundNBT();
        plantTag.putInt("NowStage", nowStage);
        plantTag.putString("PlantKey", plantKey);
        return plantTag;
    }

    public static PotPlantStage load(CompoundNBT plantTag) {
        int nowStage = plantTag.getInt("NowStage");
        String plantKey = plantTag.getString("PlantKey");
        return new PotPlantStage(nowStage, plantKey);
    }

    @Override
    public String toString() {
        return String.format("stage:%d,plant:%s", this.nowStage, this.plantKey);
    }

    /**
     * 获取产物
     */
    @Nullable
    public List<ItemStack> getHarvest(Random random) {
        Plant plant = MRSetting.getPlantMap().getPlant(plantKey);
        if (plant != null) {
            Set<PlantStageType> transTypes = plant.getTransStageType(this.nowStage); // 获取可转变的状态
            if (!transTypes.isEmpty()) {
                PlantStageType transType = transTypes.toArray(new PlantStageType[0])[random.nextInt(transTypes.size())]; // 随机选取转变状态
                Map<String, Integer> resultStacks = plant.getHarvest(this.nowStage, transType); // 获取对应状态的产物表
                List<ItemStack> results = new ArrayList<>();
                for (Map.Entry<String, Integer> entry : resultStacks.entrySet()) {
                    int amount = (int) (entry.getValue() * (1.0)); // 计算增产/减产数量
                    if (amount > 0) {
                        ItemStack resultStack = new ItemStack(
                                ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getKey()))); // 获得物品
                        resultStack.setCount(Math.min(amount, resultStack.getMaxStackSize())); // 不超过物品最大堆叠数
                        results.add(resultStack);
                    }
                }
                this.nowStage = plant.getTransStage(transType, this.nowStage); // 转为收获后的状态
                return results;
            }
        }
        return null; // 没有可转变的状态，返回Null，标志当前动作没有执行成功
    }

}
