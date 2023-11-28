package skily_leyu.mistyrain.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import skily_leyu.mistyrain.client.particle.ParticleDataTalismanDefense;
import skily_leyu.mistyrain.common.core.FluidUtils;
import skily_leyu.mistyrain.common.core.ItemUtils;
import skily_leyu.mistyrain.common.core.action.Action;
import skily_leyu.mistyrain.common.tileentity.TilePotBase;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
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
                    if (itemStack.getItem() instanceof BlockItem) {
                        Block block = ((BlockItem) itemStack.getItem()).getBlock();
                        SoundType soundType = block.getSoundType(block.defaultBlockState(), world, null, null);
                        world.playSound(null, playerEntity.blockPosition(), soundType.getPlaceSound(),
                                SoundCategory.NEUTRAL, 1.0F, 1.0F);
                        ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                    }
                    break;
                case ADD_PLANT:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_PLANTED,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                    break;
                case ADD_FLUID:
                    onAddFluid(world, playerEntity, hand, itemStack, action);
                    break;
                case REMOVE_FLUID:
                    onRemoveFluid(world, playerEntity, hand, itemStack, action);
                    break;
                case REMOVE_SOIL:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.GRAVEL_PLACE,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
                    ItemUtils.addItemToPlayer(action.getReturnStacks(), playerEntity);// 获得物品返还
                    break;
                case REMOVE_PLANT:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_BREAK,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
                    ItemUtils.addItemToPlayer(action.getReturnStacks(), playerEntity);// 获得物品返还
                    break;
                case HARVEST:
                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.SHEEP_SHEAR,
                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
                    ItemUtils.addItemToPlayer(action.getReturnStacks(), playerEntity);// 获得物品返还
                    break;
                case ADD_FERTI:
                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                    break;
                default:
            }
        } else if (tileEntity.canUseFerti(itemStack)) {
            renderBoneParticle(world, blockPos);
        }
        return ActionResultType.SUCCESS;
    }

    protected void onAddFluid(World world, PlayerEntity playerEntity, Hand hand, ItemStack itemStack, Action action) {
        FluidStack fluidStack = FluidUtils.getFluidStack(itemStack);
        if (!fluidStack.isEmpty()) {
            int amount = action.getAmount();
            FluidActionResult result = FluidUtil.tryEmptyContainer(itemStack, new FluidHandlerItemStack(new ItemStack(Items.BUCKET), amount), amount, playerEntity, true);
            if (result.isSuccess()) {
                SoundEvent soundFluid = fluidStack.getFluid().getAttributes().getEmptySound();
                world.playSound(null, playerEntity.blockPosition(), soundFluid, SoundCategory.NEUTRAL, 1.0F,
                        1.0F);
                ItemUtils.replaceHandItem(playerEntity, hand, result.getResult());
            }

        }
    }

    protected void onRemoveFluid(World world, PlayerEntity playerEntity, Hand hand, ItemStack itemStack, Action action) {
        FluidBucketWrapper bucketWrapper = new FluidBucketWrapper(action.getReturnStacks().get(0));
        if (!bucketWrapper.getFluid().isEmpty()) {
            SoundEvent sound = bucketWrapper.getFluid().getFluid().getAttributes().getFillSound();
            FluidActionResult result = FluidUtil.tryFillContainer(itemStack, bucketWrapper, action.getAmount(), playerEntity, true);
            if (result.isSuccess()) {
                ItemUtils.replaceHandItem(playerEntity, hand, result.getResult());
                world.playSound(null, playerEntity.blockPosition(), sound, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }

    /**
     * 渲染骨粉特效
     */
    protected void renderBoneParticle(World world, BlockPos blockPos) {
        Random rand = world.getRandom();
        for (int particleCount = 0; particleCount < 1; particleCount++) {
            double x = blockPos.getX() + 0.1D + rand.nextDouble() * 0.8D;
            double y = blockPos.getY() + 0.4D + rand.nextDouble() * 0.5D;
            double z = blockPos.getZ() + 0.1D + rand.nextDouble() * 0.8D;
            world.addParticle(new ParticleDataTalismanDefense(), x, y, z, rand.nextGaussian(), 0.0D,
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
