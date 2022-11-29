package skily_leyu.mistyrain.common.core.soil;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;

public class SoilMap {
    private Map<SoilType, List<String>> soilMap; // 泥土类型对应的泥土列表

    /**
     * 判断当前物品是否存在该类型的土壤列表中
     *
     * @param type
     * @param itemStack
     * @return
     */
    public boolean contains(SoilType type, ItemStack itemStack) {
        if (this.soilMap.containsKey(type)) {
            return this.soilMap.get(type).contains(ItemUtils.getRegistryName(itemStack));
        }
        return false;
    }

    /**
     * 判断当前流体是否存在该类型的土壤列表中
     *
     * @param type
     * @param itemStack
     * @return
     */
    public boolean contains(SoilType type, FluidStack fluidStack) {
        if (this.soilMap.containsKey(type)) {
            return this.soilMap.get(type).contains(FluidUtils.getFluidName(fluidStack));
        }
        return false;
    }

}
