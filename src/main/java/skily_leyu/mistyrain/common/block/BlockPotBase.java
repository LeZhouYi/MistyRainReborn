package skily_leyu.mistyrain.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import skily_leyu.mistyrain.common.core.action.Action;
import skily_leyu.mistyrain.common.core.FluidUtils;
import skily_leyu.mistyrain.common.core.ItemUtils;
import skily_leyu.mistyrain.data.MRConfig;
import skily_leyu.mistyrain.common.tileentity.TilePotBase;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.Random;

@ParametersAreNonnullByDefault
public abstract class BlockPotBase extends Block {
    protected BlockPotBase(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos,
                                PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (playerEntity.isCrouching()) {
            return ActionResultType.PASS;
        }
        TilePotBase tileEntity = (TilePotBase) world.getBlockEntity(blockPos);
        ItemStack itemStack = playerEntity.getMainHandItem();
        if (itemStack.isEmpty() || tileEntity == null) {
            return ActionResultType.PASS;
        }
        if (!world.isClientSide()) {
            Action action = tileEntity.onItemInteract(itemStack);
            switch (action.getActionType()) {
                case ADD_SOIL:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.GRASS_PLACE,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                    break;
                case ADD_PLANT:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_PLANTED,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                    break;
                case ADD_FLUID:
                    onAddFluid(world,playerEntity,itemStack,action);
                    break;
                case REMOVE_SOIL:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.GRAVEL_PLACE,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
                    ItemUtils.addItemToPlayer(action.getReturnStack(), playerEntity);// 获得物品返还
                    break;
                case REMOVE_PLANT:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_BREAK,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
                    ItemUtils.addItemToPlayer(action.getReturnStack(), playerEntity);// 获得物品返还
                    break;
                case HARVEST:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.SHEEP_SHEAR,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
                    ItemUtils.addItemToPlayer(action.getReturnStack(), playerEntity);// 获得物品返还
                    break;
                case ADD_FERTI:
                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                    break;
                default:
            }
        } else if (tileEntity.canUseFerti(itemStack)) {
            renderBoneParticle(world,blockPos);
        }
        return ActionResultType.SUCCESS;
    }

    protected void onAddFluid(World world, PlayerEntity playerEntity, ItemStack itemStack, Action action){
        Optional<FluidStack> fluidStackOp = FluidUtil.getFluidContained(itemStack);
        if (fluidStackOp.isPresent()) {
            FluidStack fluidStack = fluidStackOp.get();
            SoundEvent soundFluid = FluidUtils.getEmptyFluidSound(fluidStack);
            world.playSound(null, playerEntity.blockPosition(), soundFluid, SoundCategory.NEUTRAL, 1.0F,
                    1.0F);
            FluidUtils.shrink(playerEntity, fluidStack, action.getAmount());
        }
    }

    /**
     * 渲染骨粉特效
     */
    protected void renderBoneParticle(World world,BlockPos blockPos){
        Random rand = world.getRandom();
        for (int particleCount = 0; particleCount < MRConfig.Client.PARTICLE_AMOUNT
                .get(); particleCount++) {
            double x = blockPos.getX() + 0.1D + rand.nextDouble() * 0.8D;
            double y = blockPos.getY() + 0.4D + rand.nextDouble() * 0.5D;
            double z = blockPos.getZ() + 0.1D + rand.nextDouble() * 0.8D;
            world.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, rand.nextGaussian(), 0.0D,
                    rand.nextGaussian());
        }
    }

    @Override
    public void onRemove(BlockState oldState, World world, BlockPos blockPos, BlockState newStage,
                         boolean flag) {
        if (!world.isClientSide()) {
            TilePotBase tileEntity = (TilePotBase) world.getBlockEntity(blockPos);
            if (tileEntity != null) {
                for (ItemStack dropItem : tileEntity.getDrops()) {
                    popResource(world, blockPos, dropItem);
                }
                world.removeBlockEntity(blockPos);
            }
        }
    }

}