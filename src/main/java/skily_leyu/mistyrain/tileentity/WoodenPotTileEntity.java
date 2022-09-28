package skily_leyu.mistyrain.tileentity;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.common.core.potplant.Pot;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class WoodenPotTileEntity extends TileEntity implements ITickableTileEntity{

    private static final Pot WOODEN_POT = MRSetting.potMap.getPot("mr_wooden_pot");

    protected ItemStackHandler dirtInv = new ItemStackHandler(WOODEN_POT.getSlotSize()){
        @Override
        public int getSlotLimit(int slot){
            return 1;
        }
    }; //土壤
    protected ItemStackHandler plantInv = new ItemStackHandler(WOODEN_POT.getSlotSize()){
        @Override
        public int getSlotLimit(int slot){
            return 1;
        }
    }; //植物

    public WoodenPotTileEntity() {
        super(MRTileEntity.potTileEntity.get());
    }

    @Override
    public void tick() {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt =  super.serializeNBT();
        nbt.put("DirtInv",this.dirtInv.serializeNBT());
        nbt.put("PlantInv",this.plantInv.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
        this.dirtInv.deserializeNBT(nbt.getCompound("DirtInv"));
        this.plantInv.deserializeNBT(nbt.getCompound("PlantInv"));
    }

    public int onItemAdd(ItemStack itemStack){
        if(this.getPot().isSuitSoil(itemStack)){
            return ItemUtils.addItemInHandler(this.dirtInv, itemStack, true);
        }
        return 0;
    }

    public final Pot getPot(){
        return WOODEN_POT;
    }

}
