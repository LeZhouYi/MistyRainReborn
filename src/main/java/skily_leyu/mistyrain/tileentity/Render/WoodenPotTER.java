package skily_leyu.mistyrain.tileentity.Render;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.data.EmptyModelData;
import skily_leyu.mistyrain.tileentity.WoodenPotTileEntity;

public class WoodenPotTER extends TileEntityRenderer<WoodenPotTileEntity>{

    public WoodenPotTER(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(WoodenPotTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStack dirtStack = tileEntityIn.getDirtStack(0);
        if(!dirtStack.isEmpty() && dirtStack.getItem() instanceof BlockItem){
            matrixStackIn.pushPose();
            matrixStackIn.translate(0.25, 0.0626, 0.25);
            matrixStackIn.scale(0.5F, 0.3125F, 0.5F);
            BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRenderer();
            BlockState state = ((BlockItem)dirtStack.getItem()).getBlock().defaultBlockState();
            blockRendererDispatcher.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
            matrixStackIn.popPose();
        }
    }

}
