package skily_leyu.mistyrain.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerChunkProvider;
import net.minecraft.world.server.ServerWorld;
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
            PotPlant potPlant = this.getPlantList().isPlantSeed(itemStack);
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

    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(worldPosition,-1,getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        System.out.println("test2");
        handleUpdateTag(getBlockState(),pkt.getTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    public void syncToTrackingClients(){
        World world = this.level;
        if(world!=null&&!world.isClientSide()){
            SUpdateTileEntityPacket packet = this.getUpdatePacket();
            if(packet!=null){
                ServerChunkProvider chunkProvider = ((ServerWorld)world).getChunkSource();
                System.out.println("test");
                chunkProvider.chunkMap.getPlayers(new ChunkPos(worldPosition), false).forEach(e->e.connection.send(packet));
            }
        }
    }

}
