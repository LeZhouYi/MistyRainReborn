package skily_leyu.mistyrain.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockSnowVelvet extends BlockPotPlant {

    private static final IntegerProperty STAGE = IntegerProperty.create("plant_stage", 0, 5);
    private static final VoxelShape SHAPE = VoxelShapes.box(0.09375D, 0.0D, 0.09375D, 0.90625D, 0.9375D, 0.90625D);

    public BlockSnowVelvet() {
        super(AbstractBlock.Properties.of(Material.PLANT).noCollission());
        this.registerDefaultState(this.defaultBlockState().setValue(STAGE, 0));
        this.defaultBlockState().setValue(STAGE, 0);
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> container) {
        container.add(STAGE);
        super.createBlockStateDefinition(container);
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos,
            ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public IntegerProperty getStageProperty() {
        return STAGE;
    }
}
