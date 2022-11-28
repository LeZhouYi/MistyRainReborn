package skily_leyu.mistyrain.common.utility;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class RenderUtils {

    /**
     * 获取流体Texture
     *
     * @param fluidStack
     * @return
     */
    public static TextureAtlasSprite getFluidSprite(FluidStack fluidStack) {
        if (fluidStack != null && fluidStack.isEmpty()) {
            ResourceLocation fluidStil = fluidStack.getFluid().getAttributes().getStillTexture();
            return Minecraft.getInstance().getTextureAtlas(fluidStack.getFluid().getRegistryName()).apply(fluidStil);
        }
        return null;
    }

}
