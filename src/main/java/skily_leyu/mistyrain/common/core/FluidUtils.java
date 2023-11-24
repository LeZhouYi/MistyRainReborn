package skily_leyu.mistyrain.common.core;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class FluidUtils {

    private FluidUtils() {
    }

    /**
     * 判断物品是否拥有流体并返回
     */
    @Nonnull
    public static FluidStack getFluidStack(ItemStack itemStack) {
        if (itemStack != null) {
            Optional<FluidStack> fluidStackOp = FluidUtil.getFluidContained(itemStack);
            if(fluidStackOp.isPresent()){
                return fluidStackOp.get();
            }
        }
        return new FluidStack(FluidStack.EMPTY, 0);
    }

    /**
     * 获取流体注册名
     */
    @Nullable
    public static String getFluidName(FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.isEmpty()) {
            ResourceLocation location = fluidStack.getFluid().getRegistryName();
            return location != null ? location.toString() : null;
        }
        return null;
    }

}
