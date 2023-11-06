package skily_leyu.mistyrain.block;

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
import skily_leyu.mistyrain.tileentity.TileClayPot;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockClayPot extends BlockPotBase {
    private static final VoxelShape SHAPE = VoxelShapes.box(0.09375D, 0.0D, 0.09375D, 0.90625D, 0.21875D, 0.90625D);//碰撞箱，触碰箱
    public BlockClayPot() {
        super(AbstractBlock.Properties.of(Material.CLAY, DyeColor.GRAY)
                .harvestLevel(ItemTier.WOOD.getLevel()) //挖掘等级
                .sound(SoundType.BONE_BLOCK) //音效
                .strength(2.4F, 3.0F)); //硬度，抗性
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos,
                               ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileClayPot();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

}
