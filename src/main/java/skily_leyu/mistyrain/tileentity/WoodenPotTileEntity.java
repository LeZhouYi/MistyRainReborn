package skily_leyu.mistyrain.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.common.core.potplant.Pot;
import skily_leyu.mistyrain.common.core.potplant.PotPlant;
import skily_leyu.mistyrain.common.core.potplant.PotPlantHandler;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class WoodenPotTileEntity extends ModTileEntity implements ITickableTileEntity{

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
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("DirtInv",this.dirtInv.serializeNBT());
        nbt.put("PlantInv",this.plantInv.serializeNBT());
        nbt.put("PlantStage", this.potHandler.serializeNBT());
        return super.save(nbt);
    }

    @Override
    public void load(BlockState blockState,CompoundNBT nbt) {
        super.load(blockState,nbt);
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
        int amount = 0;
        if(this.getPot().isSuitSoil(itemStack)){
            amount = ItemUtils.addItemInHandler(this.dirtInv, itemStack, true);
        }else{
            PotPlant potPlant = MRSetting.getPlantMap().isPlantSeed(itemStack);
            if(potPlant!=null){
                for(int i = 0;i<this.getPot().getSlotSize();i++){
                    ItemStack dirtStack = this.dirtInv.getStackInSlot(i);
                    if(!dirtStack.isEmpty()&&potPlant.isSuitSoil(dirtStack)){
                        ItemUtils.setStackInHandler(dirtInv, itemStack, i, 1);
                        potHandler.addPlant(i,potPlant);
                        amount = 1;
                        break;
                    }
                }
            }
        }
        if(amount>0){
            syncToTrackingClients();
            setChanged();
        }
        return amount;
    }

    /**
     * 获取指定格子的土壤
     * @param slot
     * @return
     */
    public ItemStack getDirtStack(int slot){
        if(slot>=0 && slot<this.dirtInv.getSlots()){
            return this.dirtInv.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    /**
     * 指向配置文件获取的数据
     * @return
     */
    public Pot getPot(){
        return MRSetting.getPotMap().getPot("mr_wooden_pot");
    }

    /**
     * 获取指定格子植物的BlockState
     * @param slot
     * @return
     */
    @Nullable
    public BlockState getPlantStage(int slot){
        if(slot<0|slot>=this.plantInv.getSlots()){
            return this.potHandler.getBlockStage(slot);
        }
        return null;
    }

}
