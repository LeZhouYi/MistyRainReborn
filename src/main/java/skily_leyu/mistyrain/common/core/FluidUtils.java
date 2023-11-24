package skily_leyu.mistyrain.common.core;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidUtils {

    private FluidUtils() {
    }

    /**
     * 减少物品中储存的流体
     */
    public static void shrink(PlayerEntity playerEntity, FluidStack fluidStack, int amount) {
        if (playerEntity != null && !playerEntity.isCreative() && fluidStack != null && !fluidStack.isEmpty()) {
            fluidStack.shrink(amount);
        }
    }

    /**
     * 判断物品是否拥有流体并返回
     */
    @Nonnull
    public static FluidStack getFluidStack(ItemStack itemStack) {
        if (itemStack != null) {
            FluidBucketWrapper wrapper = new FluidBucketWrapper(itemStack);
            return wrapper.getFluid();
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
