//package skily_leyu.mistyrain.block.pot;
//
//import net.minecraft.block.AbstractBlock;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.SoundType;
//import net.minecraft.block.material.Material;
//import net.minecraft.item.DyeColor;
//import net.minecraft.item.ItemTier;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.shapes.ISelectionContext;
//import net.minecraft.util.math.shapes.VoxelShape;
//import net.minecraft.util.math.shapes.VoxelShapes;
//import net.minecraft.world.IBlockReader;
////import skily_leyu.mistyrain.tileentityd.WoodenPotTileEntity;
//
////public class BlockWoodenPot extends BlockMRPot {
////
////    private static VoxelShape SHAPE = VoxelShapes.box(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.4375D, 0.8125D);
////
////    public BlockWoodenPot() {
////        super(AbstractBlock.Properties.of(Material.WOOD, DyeColor.BROWN)
////                .harvestLevel(ItemTier.WOOD.getLevel())
////                .sound(SoundType.WOOD)
////                .strength(2.0F, 3.0F));
////    }
////
////    @Override
////    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_,
////            ISelectionContext p_220053_4_) {
////        return SHAPE;
////    }
////
////    @Override
////    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
////        return new WoodenPotTileEntity();
////    }
////
////}
