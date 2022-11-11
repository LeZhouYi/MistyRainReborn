package skily_leyu.mistyrain.block.pot;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.items.ItemHandlerHelper;
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
                    //撤回物品/清空植物
                    //清空土壤时，会清空水份
                    if(itemStack.getItem() instanceof HoeItem || itemStack.getItem() instanceof ShovelItem){
                        ItemStack returnStack = tileEntity.onItemRemove();
                        if(returnStack!=null){
                            playerEntity.playSound(SoundEvents.GRASS_PLACE, 1.0F, 1.0F);
                            if(!playerEntity.isCreative()){
                                //消耗耐久
                                itemStack.hurt(1, RANDOM, (ServerPlayerEntity) playerEntity);
                                //获得物品返还
                                ItemHandlerHelper.giveItemToPlayer(playerEntity, returnStack);
                            }
                        }
                    }else{
                        //若是桶且包含流体，则执行流体操作
                        //若是拥有流体容器的物品，则执行流体操作
                        //若是其它物品，则执行物品添加操作，如添加土壤/植物
                        if(tileEntity.onHandleBucket(itemStack)){
                            playerEntity.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                            ItemUtils.replaceHandItem(playerEntity, hand, new ItemStack(Items.BUCKET));
                        }else{
                            int amount = tileEntity.onHandleFluid(itemStack);
                            if(amount>0){
                                playerEntity.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
                                if(!playerEntity.isCreative()){
                                    FluidUtil.getFluidContained(itemStack).get().shrink(amount);
                                }
                            }else{
                                //添加物品
                                int shrinkAmount = tileEntity.onItemAdd(itemStack);
                                ItemUtils.shrinkItem(playerEntity, itemStack, shrinkAmount);
                            }
                        }
                    }
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

}
