package skily_leyu.mistyrain.block.potplant;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class BlockNarcissus extends Block{

    private static IntegerProperty STAGE = IntegerProperty.create("plant_stage", 0, 5);

    public BlockNarcissus() {
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
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos,
            PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isClientSide() && hand == Hand.MAIN_HAND){
            if(playerEntity.getMainHandItem().isEmpty()){
                world.setBlock(blockPos, blockState.cycle(STAGE), Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
        return ActionResultType.SUCCESS;
    }

}
