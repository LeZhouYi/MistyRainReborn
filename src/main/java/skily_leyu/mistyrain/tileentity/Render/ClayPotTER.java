package skily_leyu.mistyrain.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fluids.FluidStack;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.RenderUtils;
import skily_leyu.mistyrain.tileentity.ClayPotTileEntity;

public class ClayPotTER extends TileEntityRenderer<ClayPotTileEntity> {

    public ClayPotTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v,
            float r, float g, float b, float a) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(r, g, b, a)
                .uv(u, v)
                .uv2(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    @Override
    public void render(ClayPotTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        // 流体
        ItemStack dirtStack = tileEntityIn.getDirtStack(0);
        if (!dirtStack.isEmpty()) {
            FluidStack fluidStack = FluidUtils.getFluidStack(dirtStack);
            TextureAtlasSprite texture = RenderUtils.getFluidSprite(fluidStack);
            if (texture != null) {
                IVertexBuilder builder = bufferIn.getBuffer(RenderType.translucent());
                int color = RenderUtils.getLiquidColor(fluidStack, tileEntityIn);

                float r = RenderUtils.getRed(color);
                float g = RenderUtils.getGreen(color);
                float b = RenderUtils.getBlue(color);
                float a = RenderUtils.getAlpha(color);

                float min = 0.15625F;
                float max = 0.84375F;
                float y = 0.15625F;

                matrixStackIn.pushPose();
                // 顶面
                add(builder, matrixStackIn, min, y, max, texture.getU0(), texture.getV1(), r, g, b, a);
                add(builder, matrixStackIn, max, y, max, texture.getU1(), texture.getV1(), r, g, b, a);
                add(builder, matrixStackIn, max, y, min, texture.getU1(), texture.getV0(), r, g, b, a);
                add(builder, matrixStackIn, min, y, min, texture.getU0(), texture.getV0(), r, g, b, a);

                matrixStackIn.popPose();
            }
        }
        // 渲染植物
        BlockState plantState = tileEntityIn.getPlantStage(0);
        if (plantState != null) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0625D, 0.125D, 0.0625D);
            matrixStackIn.scale(0.875F, 0.875F, 0.875F);
            BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRenderer();
            blockRendererDispatcher.renderBlock(plantState, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn,
                    EmptyModelData.INSTANCE);
            matrixStackIn.popPose();
        }
    }

}
