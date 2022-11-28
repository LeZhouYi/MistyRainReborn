package skily_leyu.mistyrain.common.core.pot;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.common.core.anima.Anima;
import skily_leyu.mistyrain.common.core.soil.SoilType;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class Pot {

    private String name; // 花盆名
    private int slotSize; // 对应泥土可放格子数，可种植物数量
    private int tankSize; // 储水量
    private List<String> suitSoils; // 适合的土壤
    private List<SoilType> suitSoilType; // 适合的土壤类型
    private List<String> suitFluids; // 适合的水份
    private int fertilizer; // 肥料值
    private List<Anima> extraAnimas; // 植物产生灵气时，可以额外增加的灵气

    /**
     * 获取额外的灵气产生
     *
     * @return
     */
    public List<Anima> getExtraAnimas() {
        return this.extraAnimas;
    }

    /**
     * 获取花盆的名字
     *
     * @return
     */
    public String getPotName() {
        return this.name;
    }

    /**
     * 获取最大可储存数
     *
     * @return
     */
    public int getMaxFerti() {
        return this.fertilizer;
    }

    /**
     * 获取格子数
     *
     * @return
     */
    public int getSlotSize() {
        return this.slotSize;
    }

    /**
     * 判断当前流体是否为合适的水份
     *
     * @param fluidStack
     * @return
     */
    public boolean isSuitFluid(FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.isEmpty()) {
            ResourceLocation location = fluidStack.getFluid().getRegistryName();
            if (location != null) {
                return this.suitFluids.contains(location.toString());
            }
        }
        return false;
    }

    /**
     * 判断当前物品是否是合适的土壤
     *
     * @param itemStack
     * @return
     */
    public boolean isSuitSoil(ItemStack itemStack) {
        if (this.suitSoils.contains(ItemUtils.getRegistryName(itemStack))) {
            return true;
        }
        for (SoilType soilType : this.suitSoilType) {
            if (MRSetting.getSoilMap().contains(soilType, itemStack)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前物品是否包含流体且为合适的土壤
     *
     * @param itemStack
     * @return
     */
    public boolean isSuitSoilFluid(ItemStack itemStack) {
        if (itemStack != null) {
            FluidStack fluidStack = FluidUtils.getFluidStack(itemStack);
            if (!fluidStack.isEmpty()) {
                for (String suitName : this.suitSoils) {
                    ItemStack suitItem = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(suitName)));
                    FluidStack suitFluid = FluidUtils.getFluidStack(suitItem);
                    if (!suitFluid.isEmpty() && fluidStack.isFluidEqual(suitFluid)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取储水量
     *
     * @return
     */
    public int getTankSize() {
        return this.tankSize;
    }

}
