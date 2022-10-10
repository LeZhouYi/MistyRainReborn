package skily_leyu.mistyrain.block.pot;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.tileentity.WoodenPotTileEntity;

public class BlockWoodenPot extends Block{

    private static VoxelShape SHAPE = VoxelShapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.4375D, 0.8125D);

    public BlockWoodenPot() {
        super(AbstractBlock.Properties.of(Material.WOOD));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
            ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new WoodenPotTileEntity();
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos,
            PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isClientSide() && hand == Hand.MAIN_HAND){
            WoodenPotTileEntity tileEntity = (WoodenPotTileEntity)world.getBlockEntity(blockPos);
            ItemStack itemStack = playerEntity.getMainHandItem();
            if(!itemStack.isEmpty()&&tileEntity!=null){
                int amount = tileEntity.onItemAdd(itemStack);
                if(amount>0){
                    ItemUtils.shrinkItem(playerEntity, itemStack, amount);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

}
