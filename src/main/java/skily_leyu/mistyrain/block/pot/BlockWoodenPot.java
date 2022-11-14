package skily_leyu.mistyrain.block.pot;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;
import skily_leyu.mistyrain.common.utility.Action;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.tileentity.WoodenPotTileEntity;

public class BlockWoodenPot extends Block{

    private static VoxelShape SHAPE = VoxelShapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.4375D, 0.8125D);

    public BlockWoodenPot() {
        super(AbstractBlock.Properties.of(Material.WOOD));
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
            ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public boolean hasTileEntity(BlockState state){
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world){
        return new WoodenPotTileEntity();
    }

    @Override
    public ActionResultType use(BlockState blockState, World world, BlockPos blockPos,
            PlayerEntity playerEntity, Hand hand, BlockRayTraceResult rayTraceResult) {
        if(!world.isClientSide()){
            if(playerEntity.isCrouching()){
                return ActionResultType.PASS;
            }else{
                WoodenPotTileEntity tileEntity = (WoodenPotTileEntity)world.getBlockEntity(blockPos);
                ItemStack itemStack = playerEntity.getMainHandItem();
                if(!itemStack.isEmpty()&&tileEntity!=null){
                    Action action = tileEntity.onItemInteract(itemStack);
                    switch(action.getActionType()){
                        case ADD_SOIL:
                            world.playSound(null, playerEntity.blockPosition(), SoundEvents.GRASS_PLACE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                            ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                            break;
                        case ADD_PLANT:
                            world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_PLANTED, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                            ItemUtils.shrinkItem(playerEntity, itemStack, action.getAmount());
                            break;
                        case ADD_BUCKET_FLUID:
                            SoundEvent soundBucket = (itemStack.getItem()==Items.LAVA_BUCKET)?SoundEvents.BUCKET_EMPTY_LAVA:SoundEvents.BUCKET_EMPTY;
                            world.playSound(null, playerEntity.blockPosition(), soundBucket, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                            ItemUtils.replaceHandItem(playerEntity, hand, new ItemStack(Items.BUCKET));
                            break;
                        case ADD_FLUID:
                            FluidStack fluidStack = FluidUtil.getFluidContained(itemStack).get();
                            SoundEvent soundFluid = (fluidStack.getFluid()==Fluids.LAVA)?SoundEvents.BUCKET_EMPTY_LAVA:SoundEvents.BUCKET_EMPTY;
                            world.playSound(null, playerEntity.blockPosition(), soundFluid, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                            if(!playerEntity.isCreative()){
                                fluidStack.shrink(action.getAmount());
                            }
                            break;
                        case REMOVE_SOIL:
                            world.playSound(null, playerEntity.blockPosition(), SoundEvents.GRAVEL_PLACE, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                            if(!playerEntity.isCreative()){
                                //消耗耐久
                                itemStack.hurt(1, RANDOM, (ServerPlayerEntity) playerEntity);
                                //获得物品返还
                                ItemStack returnStack = action.getReturnStack();
                                if(returnStack!=null){
                                    ItemHandlerHelper.giveItemToPlayer(playerEntity, returnStack);
                                }
                            }
                            break;
                        case REMOVE_PLANT:
                            world.playSound(null, playerEntity.blockPosition(), SoundEvents.CROP_BREAK, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                            if(!playerEntity.isCreative()){
                                //消耗耐久
                                itemStack.hurt(1, RANDOM, (ServerPlayerEntity) playerEntity);
                                //获得物品返还
                                ItemStack returnStack = action.getReturnStack();
                                if(returnStack!=null){
                                    ItemHandlerHelper.giveItemToPlayer(playerEntity, returnStack);
                                }
                            }
                            break;
                        default:
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

}
