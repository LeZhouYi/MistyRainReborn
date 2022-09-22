package skily_leyu.mistyrain.tileentity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class PotTileEntity extends TileEntity implements ITickableTileEntity{

    public PotTileEntity() {
        super(MRTileEntity.potTileEntity.get());
    }

    @Override
    public void tick() {

    }

}
