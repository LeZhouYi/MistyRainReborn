package skily_leyu.mistyrain.block.potplant;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import skily_leyu.mistyrain.block.BlockPotPlant;

public class BlockHomingPlum extends BlockPotPlant {

    private static IntegerProperty STAGE = IntegerProperty.create("plant_stage", 0, 5);

    public BlockHomingPlum() {
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
    public IntegerProperty getStageProperty() {
        return STAGE;
    }

}
