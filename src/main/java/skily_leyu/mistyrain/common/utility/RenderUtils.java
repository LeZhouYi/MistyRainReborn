package skily_leyu.mistyrain.common.utility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.fluids.FluidStack;

public class RenderUtils {

    public static float getRed(int color) {
        return (color >> 16 & 0xFF) / 255.0F;
    }

    public static float getGreen(int color) {
        return (color >> 8 & 0xFF) / 255.0F;
    }

    public static float getBlue(int color) {
        return (color & 0xFF) / 255.0F;
    }

    public static float getAlpha(int color) {
        return (color >> 24 & 0xFF) / 255.0F;
    }

    /**
     * 获取流体颜色
     *
     * @param fluid
     * @param tileEntity
     * @return
     */
    public static int getLiquidColor(@Nonnull FluidStack fluid, @Nonnull TileEntity tileEntity) {
        World world = tileEntity.getLevel();
        if (world != null && world.isClientSide) {
            if (fluid.getFluid() == Fluids.WATER) {
                return BiomeColors.getAverageWaterColor(world, tileEntity.getBlockPos()) | 0xFF000000;
            }
        }
        return fluid.getFluid().getAttributes().getColor();
    }

    /**
     * 获取流体Texture
     *
     * @param fluidStack
     * @return
     */
    @Nullable
    public static TextureAtlasSprite getFluidSprite(FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.isEmpty()) {
            ResourceLocation fluidStill = fluidStack.getFluid().getAttributes().getStillTexture();
            return Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(fluidStill);
        }
        return null;
    }

}
