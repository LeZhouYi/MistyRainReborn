package skily_leyu.mistyrain.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.common.core.potplant.Pot;
import skily_leyu.mistyrain.common.core.potplant.PotPlant;
import skily_leyu.mistyrain.common.core.potplant.PotPlantHandler;
import skily_leyu.mistyrain.common.core.potplant.PotPlantMap;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class WoodenPotTileEntity extends TileEntity implements ITickableTileEntity{

    private static final Pot WOODEN_POT = MRSetting.potMap.getPot("mr_wooden_pot");

    protected ItemStackHandler dirtInv; //土壤
    protected ItemStackHandler plantInv; //植物
    private PotPlantHandler potHandler; //植物状态记录器

    public WoodenPotTileEntity() {
        super(MRTileEntity.woodenPotTileEntity.get());
        this.dirtInv = new ItemStackHandler(this.getPot().getSlotSize()){
            @Override
            public int getSlotLimit(int slot){
                return 1;
            }
        };
        this.plantInv = new ItemStackHandler(this.getPot().getSlotSize()){
            @Override
            public int getSlotLimit(int slot){
                return 1;
            }
        };
        this.potHandler = new PotPlantHandler();
    }

    @Override
    public void tick() {

    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt =  super.serializeNBT();
        nbt.put("DirtInv",this.dirtInv.serializeNBT());
        nbt.put("PlantInv",this.plantInv.serializeNBT());
        nbt.put("PlantStage", this.potHandler.serializeNBT());
        return nbt;
    }

    @Override
    public void deserializeNBT(BlockState blockState,CompoundNBT nbt) {
        super.deserializeNBT(blockState,nbt);
        this.dirtInv.deserializeNBT(nbt.getCompound("DirtInv"));
        this.plantInv.deserializeNBT(nbt.getCompound("PlantInv"));
        this.potHandler.deserializeNBT(nbt.getCompound("PlantStage"));
    }

    /**
     * 添加物品:
     * 1.若为泥土，则在泥土栏放置一次泥土
     * 2.若为植物，则在植物栏放置一次植物并设置该植物状态为0(一般为SeepDrop)
     * @param itemStack
     * @return
     */
    public int onItemAdd(ItemStack itemStack){
        if(this.getPot().isSuitSoil(itemStack)){
            return ItemUtils.addItemInHandler(this.dirtInv, itemStack, true);
        }else{
            PotPlant potPlant = this.getPlantList().isPlantSeed(itemStack);
            if(potPlant!=null){
                for(int i = 0;i<this.getPot().getSlotSize();i++){
                    ItemStack dirtStack = this.dirtInv.getStackInSlot(i);
                    if(!dirtStack.isEmpty()&&potPlant.isSuitSoil(dirtStack)){
                        ItemUtils.setStackInHandler(dirtInv, itemStack, i, 1);
                        potHandler.addPlant(i,potPlant);
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public ItemStack getDirtStack(int slot){
        if(slot>=0 && slot<this.dirtInv.getSlots()){
            return this.dirtInv.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(),1,getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(getBlockState(), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        deserializeNBT(state, tag);
    }

    /**
     * 指向配置文件获取的数据
     * @return
     */
    public PotPlantMap getPlantList(){
        return MRSetting.potPlants;
    }

    /**
     * 指向配置文件获取的数据
     * @return
     */
    public Pot getPot(){
        return WOODEN_POT;
    }

}
