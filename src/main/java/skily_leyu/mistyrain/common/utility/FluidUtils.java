package skily_leyu.mistyrain.common.utility;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class FluidUtils {

    /**
     * 获取流体注册名
     * @param fluidStack
     * @return
     */
    public static String getFluidName(FluidStack fluidStack){
        if(fluidStack!=null&&!fluidStack.isEmpty()){
            ResourceLocation location = fluidStack.getFluid().getRegistryName();
            return location!=null?location.toString():null;
        }
        return null;
    }

}
