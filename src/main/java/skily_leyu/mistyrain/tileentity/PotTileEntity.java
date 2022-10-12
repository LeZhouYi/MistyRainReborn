package skily_leyu.mistyrain.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.common.core.plant.Plant;
import skily_leyu.mistyrain.common.core.pot.Pot;
import skily_leyu.mistyrain.common.core.pot.PotHandler;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRSetting;

public abstract class PotTileEntity extends ModTileEntity implements ITickableTileEntity{

    protected ItemStackHandler dirtInv; //土壤
    protected ItemStackHandler plantInv; //植物
    protected PotHandler potHandler; //植物状态记录器
    private int tickCount; //计时
    private String potKey; //配置文件KeyName

    public PotTileEntity(TileEntityType<?> tileEntityType,String potKey) {
        super(tileEntityType);
        this.potKey = potKey;
        this.tickCount = getTickRate();
        this.potHandler = new PotHandler();
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
    }

    /**
     * 指向配置文件获取的数据
     * @return
     */
    public Pot getPot(){
        return MRSetting.getPotMap().getPot(this.potKey);
    }

    @Override
    public void tick() {
        World world = this.level;
        if(world!=null&&world.isAreaLoaded(worldPosition, tickCount)&&!world.isClientSide){
            tickCount--;
            if(tickCount<0){
                tickCount=getTickRate();
                potHandler.tick(this);
                syncToTrackingClients();
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("DirtInv",this.dirtInv.serializeNBT());
        nbt.put("PlantInv",this.plantInv.serializeNBT());
        nbt.put("PlantStage", this.potHandler.serializeNBT());
        nbt.putInt("TickCount", tickCount);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState blockState,CompoundNBT nbt) {
        super.load(blockState,nbt);
        this.dirtInv.deserializeNBT(nbt.getCompound("DirtInv"));
        this.plantInv.deserializeNBT(nbt.getCompound("PlantInv"));
        this.potHandler.deserializeNBT(nbt.getCompound("PlantStage"));
        this.tickCount = nbt.getInt("TickCount");
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
            Plant potPlant = MRSetting.getPlantMap().isPlantSeed(itemStack);
            if(potPlant!=null){
                for(int i = 0;i<this.getPot().getSlotSize();i++){
                    ItemStack dirtStack = this.dirtInv.getStackInSlot(i);
                    if(!dirtStack.isEmpty()&&potPlant.isSuitSoil(dirtStack)){
                        ItemUtils.setStackInHandler(dirtInv, itemStack, i, 1);
                        potHandler.addPlant(i,potPlant);
                        amount = 1;
                        System.out.println("addTEST");
                        break;
                    }
                }
            }
        }
        if(amount>0){
            syncToTrackingClients();
        }
        return amount;
    }

    /**
     * 回退物品:1、存在植物时清空植物状态，仅当Stage=0时返还物品(即未被消耗)
     * 2、不存在植物时清空土壤，返回该物品
     * @return
     */
    public ItemStack onItemRemove(){
        boolean removePlant = false;
        ItemStack returnStack = ItemStack.EMPTY;
        for(int i = this.plantInv.getSlots()-1;i>=0;i--){
            ItemStack plantStack = this.plantInv.getStackInSlot(i).copy();
            if(!plantStack.isEmpty()&&returnStack!=null){
                this.plantInv.setStackInSlot(i, returnStack);
                if(this.potHandler.removePlant(i)){
                    returnStack = plantStack;
                }
                syncToTrackingClients();
                removePlant=true;
                break;
            }
        }
        if(!removePlant){
            for(int i = this.dirtInv.getSlots()-1;i>=0;i--){
                ItemStack dirtStack = this.plantInv.getStackInSlot(i).copy();
                if(!dirtStack.isEmpty()&&returnStack!=null){
                    this.dirtInv.setStackInSlot(i, returnStack);
                    returnStack = dirtStack;
                    syncToTrackingClients();
                    break;
                }
            }
        }
        return returnStack;
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
     * 获取更新速率
     * @return
     */
    public final int getTickRate(){
        return MRConfig.GameRule.POT_PLANT_TICK.get();
    }

}
