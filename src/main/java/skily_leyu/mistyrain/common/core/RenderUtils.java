package skily_leyu.mistyrain.common.core;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.fluids.FluidStack;

public class RenderUtils {

    private RenderUtils(){}

    public static float[] getRGBA(int color){
        return new float[]{getRed(color),getGreen(color),getBlue(color),getAlpha(color)};
    }

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
     */
    public static int getLiquidColor(@Nonnull FluidStack fluid, @Nonnull TileEntity tileEntity) {
        World world = tileEntity.getLevel();
        if (world != null && world.isClientSide && fluid.getFluid() == Fluids.WATER) {
                return BiomeColors.getAverageWaterColor(world, tileEntity.getBlockPos()) | 0xFF000000;

        }
        return fluid.getFluid().getAttributes().getColor();
    }

    /**
     * 获取流体Texture
     */
    @Nullable
    public static TextureAtlasSprite getFluidSprite(FluidStack fluidStack) {
        if (fluidStack != null && !fluidStack.isEmpty()) {
            ResourceLocation fluidStill = fluidStack.getFluid().getAttributes().getStillTexture();
            return Minecraft.getInstance().getTextureAtlas(PlayerContainer.BLOCK_ATLAS).apply(fluidStill);
        }
        return null;
    }

    public static void drawCenterString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent component,float x, float y,int color){
        int width = fontRenderer.width(component);
        fontRenderer.draw(matrixStack,component,x-width/2.0F,y,color);
    }

    public static  void drawRightString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent component,float x, float y,int color){
        int width = fontRenderer.width(component);
        fontRenderer.draw(matrixStack,component,x-width,y,color);
    }

}
