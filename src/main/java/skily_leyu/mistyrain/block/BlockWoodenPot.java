package skily_leyu.mistyrain.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
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
import net.minecraftforge.common.extensions.IForgeTileEntity;
import skily_leyu.mistyrain.tileentity.TileEntityWoodenPot;

public class BlockWoodenPot extends Block {

    private static VoxelShape SHAPE = VoxelShapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.4375D, 0.8125D);//碰撞箱，触碰箱

    public BlockWoodenPot() {
        super(AbstractBlock.Properties.of(Material.WOOD, DyeColor.BROWN)
                .harvestLevel(ItemTier.WOOD.getLevel()) //挖掘等级
                .sound(SoundType.WOOD) //音效
                .strength(2.0F, 3.0F)); //硬度，抗性
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
                               ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityWoodenPot();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    //    @Override
//    public void onRemove(BlockState oldState, World world, BlockPos blockPos, BlockState newStage,
//            boolean flag) {
//        if (!world.isClientSide()) {
//            PotTileEntity tileEntity = (PotTileEntity) world.getBlockEntity(blockPos);
//            if (tileEntity != null) {
//                for (ItemStack dropItem : tileEntity.getDrops()) {
//                    popResource(world, blockPos, dropItem);
//                }
//                world.removeBlockEntity(blockPos);
//            }
//        }
//    }

//    @Override
//    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos,
//            PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
//        if (playerEntity.isCrouching()) {
//            return ActionResultType.PASS;
//        }
//        PotTileEntity tileEntity = (PotTileEntity) world.getBlockEntity(blockPos);
//        ItemStack itemStack = playerEntity.getMainHandItem();
//        if (itemStack.isEmpty() || tileEntity == null) {
//            return ActionResultType.PASS;
//        }
//        if (!world.isClientSide()) {
//            Action action = tileEntity.onItemInteract(itemStack);
//            switch (action.getActionType()) {
//                case ADD_SOIL:
//                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.GRASS_PLACE,
//                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
//                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
//                    break;
//                case ADD_PLANT:
//                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_PLANTED,
//                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
//                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
//                    break;
//                case ADD_FLUID:
//                    FluidStack fluidStack = FluidUtil.getFluidContained(itemStack).get();
//                    SoundEvent soundFluid = FluidUtils.getEmptyFluidSound(fluidStack);
//                    world.playSound(null, playerEntity.blockPosition(), soundFluid, SoundCategory.NEUTRAL, 1.0F,
//                            1.0F);
//                    FluidUtils.shrink(playerEntity, fluidStack, action.getAmount());
//                    break;
//                case ADD_FERTI:
//                    ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
//                    break;
//                case REMOVE_SOIL:
//                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.GRAVEL_PLACE,
//                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
//                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
//                    ItemUtils.addItemToPlayer(action.getReturnStack(), playerEntity);// 获得物品返还
//                    break;
//                case REMOVE_PLANT:
//                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_BREAK,
//                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
//                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
//                    ItemUtils.addItemToPlayer(action.getReturnStack(), playerEntity);// 获得物品返还
//                    break;
//                case HARVEST:
//                    world.playSound(null, playerEntity.blockPosition(), SoundEvents.SHEEP_SHEAR,
//                            SoundCategory.NEUTRAL, 1.0F, 1.0F);
//                    ItemUtils.hurtItem((ServerPlayerEntity) playerEntity, itemStack, action.getAmount());// 消耗耐久
//                    ItemUtils.addItemToPlayer(action.getReturnStack(), playerEntity);// 获得物品返还
//                default:
//            }
//        } else {
//            // 渲染骨粉特效
//            if (tileEntity.canUseFerti(itemStack)) {
//                Random rand = world.getRandom();
//                for (int particleCount = 0; particleCount < MRConfig.Client.PARTICLE_AMOUNT
//                        .get(); particleCount++) {
//                    double x = blockPos.getX() + 0.1D + rand.nextDouble() * 0.8D;
//                    double y = blockPos.getY() + 0.4D + rand.nextDouble() * 0.5D;
//                    double z = blockPos.getZ() + 0.1D + rand.nextDouble() * 0.8D;
//                    world.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, rand.nextGaussian(), 0.0D,
//                            rand.nextGaussian());
//                }
//            }
//            MRDebug.printAnimas(tileEntity.getGenAnima());
//        }
//        return ActionResultType.SUCCESS;
//    }

}
