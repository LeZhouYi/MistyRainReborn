package skily_leyu.mistyrain.common.core.plant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.common.core.FluidUtils;
import skily_leyu.mistyrain.common.core.ItemUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class Plant {

    private final String name; // 植物名
    private List<String> seeds; // 种子或类种子物品名
    private List<PlantStage> stages; // 植物的状态转换表
    private List<String> likeSoils; // 喜欢的土壤
    private List<String> suitSoilTypes; // 适合的土壤类型

    public Plant(String name) {
        this.name = name;
    }

    public void setSeeds(List<String> seeds) {
        this.seeds = seeds;
    }

    public void setStages(List<PlantStage> stages) {
        this.stages = stages;
    }

    public void setSoil(List<String> likeSoils, List<String> suitSoilTypes) {
        this.likeSoils = likeSoils;
        this.suitSoilTypes = suitSoilTypes;
    }

    /**
     * 获取该植物的BlockState,其中meta=0时，若状态为SeedDrop则为null，默认不渲染模型
     */
    @Nullable
    public BlockState getBlockState(int meta) {
        if (stages.isEmpty()) {
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
     */
    public boolean containSeed(ItemStack itemStack) {
        return seeds.contains(ItemUtils.getRegistryName(itemStack));
    }

    /**
     * 检查当前物品是否为该植物合适生长的土壤
     */
    public boolean isSuitSoil(ItemStack dirtStack) {
        String dirtName = ItemUtils.getRegistryName(dirtStack);
        if (likeSoils.contains(dirtName)) {
            return true;
        }
        if (dirtStack.getItem() instanceof BlockItem) {
            ITagCollection<Block> allTags = BlockTags.getAllTags();
            BlockItem blockItem = ((BlockItem) dirtStack.getItem());
            for (String soilType : this.suitSoilTypes) {
                ITag<Block> blockTag = allTags.getTag(new ResourceLocation(soilType));
                if (blockTag != null && blockTag.contains(blockItem.getBlock())) {
                    return true;
                }
            }
        }
        FluidStack fluidStack = FluidUtils.getFluidStack(dirtStack);
        if(!fluidStack.isEmpty()){
            ITagCollection<Fluid> allTags = FluidTags.getAllTags();
            for(String soilType: this.suitSoilTypes){
                ITag<Fluid> fluidTag = allTags.getTag(new ResourceLocation(soilType));
                if(fluidTag!=null && fluidTag.contains(fluidStack.getFluid())){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取注册名
     */
    public String getName() {
        return name;
    }

    /**
     * 获取当前状态被收获后要转换的状态
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
     */
    @Nonnull
    public Map<String, Integer> getHarvest(int nowStage, PlantStageType transType) {
        if (this.stages != null && nowStage < this.stages.size() && nowStage >= 0) {
            return this.stages.get(nowStage).getHarvest(transType);
        }
        return new HashMap<>();
    }
}
