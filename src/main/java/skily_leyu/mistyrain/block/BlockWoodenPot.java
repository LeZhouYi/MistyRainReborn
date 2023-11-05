package skily_leyu.mistyrain.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import skily_leyu.mistyrain.common.core.action.Action;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.tileentity.TilePotBase;
import skily_leyu.mistyrain.tileentity.TileWoodenPot;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockWoodenPot extends Block {

    private static final VoxelShape SHAPE = VoxelShapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.4375D, 0.8125D);//碰撞箱，触碰箱

    public BlockWoodenPot() {
        super(AbstractBlock.Properties.of(Material.WOOD, DyeColor.BROWN)
                .harvestLevel(ItemTier.WOOD.getLevel()) //挖掘等级
                .sound(SoundType.WOOD) //音效
                .strength(2.0F, 3.0F)); //硬度，抗性
    }

    @Override
    @Nonnull
    public VoxelShape getShape(BlockState blockState, IBlockReader blockReader, BlockPos blockPos,
                               ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileWoodenPot();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
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
                    Optional<FluidStack> fluidStackOp = FluidUtil.getFluidContained(itemStack);
                    if (fluidStackOp.isPresent()) {
                        FluidStack fluidStack = fluidStackOp.get();
                        SoundEvent soundFluid = FluidUtils.getEmptyFluidSound(fluidStack);
                        world.playSound(null, playerEntity.blockPosition(), soundFluid, SoundCategory.NEUTRAL, 1.0F,
                                1.0F);
                        FluidUtils.shrink(playerEntity, fluidStack, action.getAmount());
                    }
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
                default:
            }
        } else if (tileEntity.canUseFerti(itemStack)) {
            //渲染骨粉特效
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
        return ActionResultType.SUCCESS;
    }

}