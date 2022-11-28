package skily_leyu.mistyrain.common.utility;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

public class FluidUtils {

    /**
     * 判断物品是否拥有流体并返回
     *
     * @param itemStack
     * @return
     */
    public static FluidStack getFluidStack(ItemStack itemStack) {
        if (itemStack != null) {
            FluidBucketWrapper wrapper = new FluidBucketWrapper(itemStack);
            return wrapper.getFluid();
        }
        return FluidStack.EMPTY;
    }

    /**
     * 获取流体注册名
     *
     * @param fluidStack
     * @return
     */
    public static String getFluidName(FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.isEmpty()) {
            ResourceLocation location = fluidStack.getFluid().getRegistryName();
            return location != null ? location.toString() : null;
        }
        return null;
    }

}
