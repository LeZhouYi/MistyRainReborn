package skily_leyu.mistyrain.common.core.pot;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import skily_leyu.mistyrain.common.core.FluidUtils;
import skily_leyu.mistyrain.common.core.ItemUtils;
import skily_leyu.mistyrain.common.core.soil.SoilType;
import skily_leyu.mistyrain.data.MRSetting;

import javax.annotation.Nonnull;
import java.util.List;

public class Pot {

    private int slotSize; // 对应泥土可放格子数，可种植物数量
    private List<String> suitSoils; // 适合的土壤
    private List<SoilType> suitSoilType; // 适合的土壤类型


    public void setSlotSize(int slotSize) {
        this.slotSize = slotSize;
    }

    public void setSuitSoils(List<String> suitSoils) {
        this.suitSoils = suitSoils;
    }

    public void setSuitSoilType(List<SoilType> suitSoilType) {
        this.suitSoilType = suitSoilType;
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
    public boolean isSuitSoil(@Nonnull ItemStack itemStack) {
        if(itemStack.getItem() instanceof BlockItem){
            if (this.suitSoils.contains(ItemUtils.getRegistryName(itemStack))) {
                return true;
            }
            for (SoilType soilType : this.suitSoilType) {
                if (MRSetting.getSoilMap().contains(soilType, itemStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断当前物品是否是合适的液体
     */
    public boolean isSuitFluid(@Nonnull FluidStack fluidStack){
        if (!fluidStack.isEmpty()) {
            if (this.suitSoils.contains(FluidUtils.getFluidName(fluidStack))) {
                return true;
            }
            for (SoilType soilType : this.suitSoilType) {
                if (MRSetting.getSoilMap().contains(soilType, fluidStack)) {
                    return true;
                }
            }
        }
        return false;
    }

}
