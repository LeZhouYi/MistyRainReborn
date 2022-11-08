package skily_leyu.mistyrain.block.potplant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import skily_leyu.mistyrain.common.utility.IMRPlant;

public abstract class BlockPotPlant extends Block implements IMRPlant{

    public BlockPotPlant(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos,
            PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isClientSide() && hand == Hand.MAIN_HAND){
            if(playerEntity.getMainHandItem().isEmpty()){
                world.setBlock(blockPos, blockState.cycle(getStageProperty()), Constants.BlockFlags.BLOCK_UPDATE);
            }
        }
        return ActionResultType.SUCCESS;
    }

}
