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
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.RenderUtils;
import skily_leyu.mistyrain.tileentity.ClayPotTileEntity;

public class ClayPotTER extends TileEntityRenderer<ClayPotTileEntity> {

    public ClayPotTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v) {
        renderer.vertex(stack.last().pose(), x, y, z)
                .color(1.0F, 1.0F, 1.0F, 1.0F)
                .uv(u, v)
                .overlayCoords(0, 240)
                .normal(1, 0, 0).endVertex();
    }

    @Override
    public void render(ClayPotTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        // 流体
        ItemStack dirtStack = tileEntityIn.getDirtStack(0);
        if (!dirtStack.isEmpty()) {
            TextureAtlasSprite texture = RenderUtils.getFluidSprite(FluidUtils.getFluidStack(dirtStack));
            IVertexBuilder builder = bufferIn.getBuffer(RenderType.translucent());
            if (texture != null) {
                matrixStackIn.pushPose();
                matrixStackIn.translate(0.0625, 0.3126, 0.0625);
                matrixStackIn.scale(0.875F, 0.875F, 0.875F);

                // 顶面
                add(builder, matrixStackIn, 0.0F, 0.25F, 1.0F, texture.getU0(), texture.getV1());
                add(builder, matrixStackIn, 1.0F, 0.25F, 1.0F, texture.getU1(), texture.getV1());
                add(builder, matrixStackIn, 0.0F, 0.25F, 0.0F, texture.getU0(), texture.getV0());
                add(builder, matrixStackIn, 1.0F, 0.25F, 0.0F, texture.getU1(), texture.getV0());

                matrixStackIn.popPose();
            }
        }
        // 渲染植物
        BlockState plantState = tileEntityIn.getPlantStage(0);
        if (plantState != null) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.0625, 0.3126, 0.0625);
            matrixStackIn.scale(0.875F, 0.875F, 0.875F);
            BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRenderer();
            blockRendererDispatcher.renderBlock(plantState, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn,
                    EmptyModelData.INSTANCE);
            matrixStackIn.popPose();
        }
    }

}
