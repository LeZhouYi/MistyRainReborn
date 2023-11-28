package skily_leyu.mistyrain.client.render;

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
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.fluids.FluidStack;
import skily_leyu.mistyrain.common.core.FluidUtils;
import skily_leyu.mistyrain.common.core.RenderUtils;
import skily_leyu.mistyrain.common.tileentity.TileClayPot;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TERClayPot extends TileEntityRenderer<TileClayPot> {

    public TERClayPot(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private void add(IVertexBuilder renderer, MatrixStack stack, Vector3f pos3, float u, float v,
                     float[] argb) {
        renderer.vertex(stack.last().pose(), pos3.x(), pos3.y(), pos3.z())
                .color(argb[0], argb[1], argb[2], argb[3])
                .uv(u, v)
                .uv2(0, 240)
                .normal(1, 0, 0)
                .endVertex();
    }

    @Override
    public void render(TileClayPot tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
                       IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        // 流体
        ItemStack dirtStack = tileEntityIn.getDirtStack(0);
        if (!dirtStack.isEmpty()) {
            FluidStack fluidStack = FluidUtils.getFluidStack(dirtStack);
            TextureAtlasSprite texture = RenderUtils.getFluidSprite(fluidStack);
            if (texture != null) {
                IVertexBuilder builder = bufferIn.getBuffer(RenderType.translucent());
                int color = RenderUtils.getLiquidColor(fluidStack, tileEntityIn);
                float[] argb = RenderUtils.getRGBA(color);

                Vector3f pos = new Vector3f(0.15625F,0.15625F,0.84375F);

                matrixStackIn.pushPose();
                //顶面
                add(builder, matrixStackIn, pos, texture.getU0(), texture.getV1(), argb);
                add(builder, matrixStackIn, new Vector3f(pos.z(),pos.y(),pos.z()), texture.getU1(), texture.getV1(), argb);
                add(builder, matrixStackIn, new Vector3f(pos.z(),pos.y(),pos.x()), texture.getU1(), texture.getV0(), argb);
                add(builder, matrixStackIn, new Vector3f(pos.x(),pos.y(),pos.x()), texture.getU0(), texture.getV0(), argb);

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
