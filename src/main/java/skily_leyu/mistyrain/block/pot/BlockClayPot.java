package skily_leyu.mistyrain.block.pot;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemTier;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import skily_leyu.mistyrain.tileentity.ClayPotTileEntity;

public class BlockClayPot extends BlockMRPot {

    private static VoxelShape SHAPE = VoxelShapes.box(0.09375D, 0.0D, 0.09375D, 0.90625D, 0.21875D, 0.90625D);

    public BlockClayPot() {
        super(AbstractBlock.Properties.of(Material.CLAY, DyeColor.BROWN)
                .harvestLevel(ItemTier.WOOD.getLevel())
                .sound(SoundType.SNOW)
                .strength(2.5F, 3.0F));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
            ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ClayPotTileEntity();
    }

}
