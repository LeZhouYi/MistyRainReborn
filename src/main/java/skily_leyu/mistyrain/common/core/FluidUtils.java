package skily_leyu.mistyrain.common.core;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FluidUtils {

    private FluidUtils(){}

    public static boolean hasFluid(IFluidHandlerItem handlerItem){
        for(int i = 0;i< handlerItem.getTanks();i++){
            FluidStack fluidStack = handlerItem.getFluidInTank(0);
            if(!fluidStack.isEmpty()){
                return true;
            }
        }
        return false;
    }

    public static SoundEvent getEmptyFluidSound(IFluidHandlerItem handlerItem){
        for(int i = 0;i< handlerItem.getTanks();i++){
            FluidStack fluidStack = handlerItem.getFluidInTank(0);
            if(!fluidStack.isEmpty()){
                if(fluidStack.getFluid() == Fluids.LAVA){
                    return SoundEvents.BUCKET_EMPTY_LAVA;
                }
                return SoundEvents.BUCKET_EMPTY;
            }
        }
        return SoundEvents.BUCKET_EMPTY;
    }

    /**
     * 返体清空流体的声音
     *
     * @param fluidStack
     * @return
     */
    public static SoundEvent getEmptyFluidSound(FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.isEmpty() && fluidStack.getFluid() == Fluids.LAVA) {
            return SoundEvents.BUCKET_EMPTY_LAVA;
        }
        return SoundEvents.BUCKET_EMPTY;
    }

    /**
     * 减少物品中储存的流体
     *
     * @param playerEntity
     * @param fluidStack
     * @param amount
     */
    public static void shrink(PlayerEntity playerEntity, FluidStack fluidStack, int amount) {
        if (playerEntity != null && !playerEntity.isCreative() && fluidStack != null && !fluidStack.isEmpty()) {
            fluidStack.shrink(amount);
        }
    }

    /**
     * 判断物品是否拥有流体并返回
     *
     * @param itemStack
     * @return
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
     *
     * @param fluidStack
     * @return
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
