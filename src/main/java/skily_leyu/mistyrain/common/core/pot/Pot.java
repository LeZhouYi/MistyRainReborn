package skily_leyu.mistyrain.common.core.pot;

import net.minecraft.item.BlockItem;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import skily_leyu.mistyrain.common.core.ItemUtils;

import javax.annotation.Nonnull;
import java.util.List;

public class Pot {

    private int slotSize; // 对应泥土可放格子数，可种植物数量
    private List<String> suitSoils; // 适合的土壤
    private List<String> suitSoilTypes; // 适合的土壤类型


    public void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
    }

    public void setSuitSoils(List<String> suitSoils) {
        this.suitSoils = suitSoils;
    }

    public void setSuitSoilType(List<String> suitSoilType) {
        this.suitSoilTypes = suitSoilType;
    }

    /**
     * 获取格子数
     */
    public int getSlotSize() {
        return this.slotSize;
    }

    /**
     * 判断当前物品是否是合适的土壤
     */
    public boolean isSuitSoil(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BlockItem) {
            if (this.suitSoils.contains(ItemUtils.getRegistryName(itemStack))) {
                return true;
            }
            ITagCollection<Block> allTags = BlockTags.getAllTags();
            BlockItem blockItem = ((BlockItem) itemStack.getItem());
            for (String soilType : this.suitSoilTypes) {
                ITag<Block> blockTag = allTags.getTag(new ResourceLocation(soilType));
                if (blockTag != null && blockTag.contains(blockItem.getBlock())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前物品是否是合适的液体
     */
    public boolean isSuitFluid(@Nonnull FluidStack fluidStack) {
        if (!fluidStack.isEmpty()) {
            ITagCollection<Fluid> allTags = FluidTags.getAllTags();
            for (String soilType : this.suitSoilTypes) {
                ITag<Fluid> fluidTag = allTags.getTag(new ResourceLocation(soilType));
                if (fluidTag != null && fluidTag.contains(fluidStack.getFluid())) {
                    return true;
                }
            }
        }
        return false;
    }

}
