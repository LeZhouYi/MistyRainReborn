package skily_leyu.mistyrain.block.pot;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.ITickableTileEntity;

public class BlockWoodenPot extends Block implements ITickableTileEntity{

    public BlockWoodenPot(Properties p_i48440_1_) {
        super(AbstractBlock.Properties.of(Material.WOOD));
    }

    @Override
    public void tick() {

    }

}
