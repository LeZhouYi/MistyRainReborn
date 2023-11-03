package skily_leyu.mistyrain.common.core.plant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.common.core.soil.SoilType;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class Plant {

    private String name; // 植物名
    private List<String> seeds; // 种子或类种子物品名
    private List<PlantStage> stages; // 植物的状态转换表
    private List<String> likeSoils; // 喜欢的土壤
    private List<SoilType> suitSoilTypes; // 适合的土壤类型

    /**
     * 获取该植物的BlockState,其中meta=0时，若状态为SeedDrop则为null，默认不渲染模型
     *
     * @param meta
     * @return
     */
    @Nullable
    public BlockState getBlockState(int meta) {
        if (stages.size() < 1) {
            return null;
        }
        if (meta == 0 && stages.get(0).isNowStage(PlantStageType.SEED_DROP)) {
            return null;
        }
        Block plantBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(getName()));
        if (plantBlock != null) {
            return plantBlock.getStateDefinition().getPossibleStates().get(meta);
        }
        return null;
    }

    /**
     * 获取当前生长状态
     *
     * @param nowStage
     * @return
     */
    @Nonnull
    public PlantStageType getPlantStage(int nowStage) {
        if (this.stages != null && nowStage < this.stages.size() && nowStage >= 0) {
            return this.stages.get(nowStage).getNowStageType();
        }
        return PlantStageType.NULL;
    }

    /**
     * 获取对应状态类型具体的状态值，不存在则返回nowStage
     *
     * @param nowStage
     * @return
     */
    public int getTransStage(PlantStageType transType, int nowStage) {
        if (this.stages != null) {
            for (int i = 0; i < this.stages.size(); i++) {
                if (this.stages.get(i).getNowStageType() == transType) {
                    return i;
                }
            }
        }
        return nowStage;
    }

    /**
     * 获取下一生长阶段
     *
     * @param nowStage
     * @param random
     * @return
     */
    public int getNextStage(int nowStage, Random random) {
        if (this.stages != null && nowStage < this.stages.size() && nowStage >= 0) {
            PlantStage nowPlantStage = this.stages.get(nowStage);
            PlantStageType nextStageType = nowPlantStage.getNextStageType(random);
            for (int i = 0; i < this.stages.size(); i++) {
                if (this.stages.get(i).isNowStage(nextStageType)) {
                    return i;
                }
            }
        }
        return nowStage;
    }

    /**
     * 判断当前物品是否为该植物的种子
     *
     * @param itemStack
     * @return
     */
    public boolean containSeed(ItemStack itemStack) {
        return seeds.contains(ItemUtils.getRegistryName(itemStack));
    }

    /**
     * 检查当前物品是否为该植物合适生长的土壤
     *
     * @param dirtStack
     * @return
     */
    public boolean isSuitSoil(ItemStack dirtStack) {
        String dirtName = ItemUtils.getRegistryName(dirtStack);
        if (likeSoils.contains(dirtName)) {
            return true;
        }
        for (SoilType soilType : suitSoilTypes) {
            if (MRSetting.getSoilMap().contains(soilType, dirtStack)) {
                return true;
            }
        }
        FluidStack fluidStack = FluidUtils.getFluidStack(dirtStack);
        if (fluidStack.isEmpty()) {
            return false;
        }
        String fluidName = FluidUtils.getFluidName(fluidStack);
        if (fluidName == null) {
            return false;
        }
        if (likeSoils.contains(fluidName)) {
            return true;
        }
        for (SoilType soilType : suitSoilTypes) {
            if (MRSetting.getSoilMap().contains(soilType, fluidStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取注册名
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取当前状态被收获后要转换的状态
     *
     * @param nowStage
     * @return
     */
    @Nonnull
    public Set<PlantStageType> getTransStageType(int nowStage) {
        if (this.stages != null && nowStage < this.stages.size() && nowStage >= 0) {
            return this.stages.get(nowStage).getTransStageType();
        }
        return new HashSet<>();
    }

    /**
     * 获取当前状态对应转换状态所能收获的内容
     *
     * @param nowStage
     * @param transType
     * @return
     */
    @Nonnull
    public Map<String, Integer> getHarvest(int nowStage, PlantStageType transType) {
        if (this.stages != null && nowStage < this.stages.size() && nowStage >= 0) {
            return this.stages.get(nowStage).getHarvest(transType);
        }
        return new HashMap<>();
    }
}
