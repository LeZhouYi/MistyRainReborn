package skily_leyu.mistyrain.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.common.util.Constants;
import skily_leyu.mistyrain.common.plant.IMRPlant;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class BlockPotPlant extends Block implements IMRPlant, IPlantable {

    protected BlockPotPlant(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos,
                                PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (!world.isClientSide() && hand == Hand.MAIN_HAND && playerEntity.getMainHandItem().isEmpty() && playerEntity.isCreative()) {
            world.setBlock(blockPos, blockState.cycle(getStageProperty()), Constants.BlockFlags.BLOCK_UPDATE);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return defaultBlockState();
        return state;
    }

}
