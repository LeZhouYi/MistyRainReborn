package skily_leyu.mistyrain.tileentity.tileport;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.common.core.anima.Anima;
import skily_leyu.mistyrain.common.core.plant.Plant;
import skily_leyu.mistyrain.common.core.pot.Pot;
import skily_leyu.mistyrain.common.core.pot.PotHandler;
import skily_leyu.mistyrain.common.utility.Action;
import skily_leyu.mistyrain.common.utility.ActionType;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRSetting;

public abstract class PotTileEntity extends ModTileEntity implements ITickableTileEntity {

    protected ItemStackHandler dirtInv; // 土壤
    protected ItemStackHandler plantInv; // 植物
    protected FluidTank waterTank;// 水量
    protected PotHandler potHandler; // 植物状态记录器
    protected int fertiTank; // 肥料槽
    private int tickCount; // 计时
    private String potKey; // 配置文件KeyName

    public PotTileEntity(TileEntityType<?> tileEntityType, String potKey) {
        super(tileEntityType);
        this.potKey = potKey;
        this.tickCount = getTickRate();
        this.potHandler = new PotHandler();
        this.waterTank = new FluidTank(this.getPot().getTankSize());
        this.dirtInv = new ItemStackHandler(this.getPot().getSlotSize()) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
        this.plantInv = new ItemStackHandler(this.getPot().getSlotSize()) {
            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }

    /**
     * 指向配置文件获取的数据
     *
     * @return
     */
    public Pot getPot() {
        return MRSetting.getPotMap().getPot(this.potKey);
    }

    @Override
    public void tick() {
        World world = this.level;
        if (world != null && world.isAreaLoaded(worldPosition, tickCount) && !world.isClientSide) {
            tickCount--;
            if (tickCount < 1) {
                tickCount = getTickRate();
                if (!potHandler.isEmpty()) {
                    potHandler.tick(this);
                    syncToTrackingClients();
                }
            }
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("DirtInv", this.dirtInv.serializeNBT());
        nbt.put("PlantInv", this.plantInv.serializeNBT());
        nbt.put("PlantStage", this.potHandler.serializeNBT());
        nbt.putInt("TickCount", tickCount);
        nbt.put("WaterTank", this.waterTank.writeToNBT(new CompoundNBT()));
        nbt.putInt("FertiTank", this.fertiTank);
        return super.save(nbt);
    }

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        this.dirtInv.deserializeNBT(nbt.getCompound("DirtInv"));
        this.plantInv.deserializeNBT(nbt.getCompound("PlantInv"));
        this.potHandler.deserializeNBT(nbt.getCompound("PlantStage"));
        this.tickCount = nbt.getInt("TickCount");
        this.waterTank.readFromNBT(nbt.getCompound("WaterTank"));
        this.fertiTank = nbt.getInt("FertiTank");
    }

    /**
     * 物品与盆栽交互的主入口
     *
     * @param itemStack
     * @return
     */
    public Action onItemInteract(ItemStack itemStack) {
        Action action = Action.EMPTY;
        if (itemStack != null && !itemStack.isEmpty()) {
            // 移除泥土/植物
            if (isRemoveTools(itemStack)) {
                action = onRemovePlant();
                if (action.isEmpty()) {
                    action = onRemoveSoil();
                }
            }
            // 收获产品
            else if (isHarvestTools(itemStack)) {
                action = onHarvest(itemStack);
            }
            // 流体容器操作
            else if (!FluidUtils.getFluidStack(itemStack).isEmpty()) {
                action = onHandleFluid(itemStack);
            }
            // 添加土壤
            else if (!isSoilFull()
                    && (this.getPot().isSuitSoil(itemStack) || this.getPot().isSuitSoilFluid(itemStack))) {
                action = onSoilAdd(itemStack);
            }
            // 肥料，其余物品
            else if (!this.isSoilEmpty()) {
                action = onFertiAdd(itemStack);
                if (action.isEmpty()) {
                    action = onPlantAdd(itemStack);
                }
            }
        }
        if (action != null && !action.isEmpty()) {
            syncToTrackingClients();
        }
        return action;
    }

    /**
     * 判断土壤栏是否全部存在土壤
     *
     * @return
     */
    public boolean isSoilFull() {
        for (int i = 0; i < this.dirtInv.getSlots(); i++) {
            if (this.dirtInv.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 移除植物
     *
     * @return
     */
    public Action onRemovePlant() {
        // 清空植物
        for (int i = this.plantInv.getSlots() - 1; i >= 0; i--) {
            ItemStack plantStack = ItemUtils.clearStackInHandler(plantInv, i);
            if (!plantStack.isEmpty()) {
                this.potHandler.removePlant(i);
                return new Action(ActionType.REMOVE_PLANT, 1, plantStack);
            }
        }
        return Action.EMPTY;
    }

    /**
     * 添加肥料操作
     *
     * @param itemStack
     * @return
     */
    public Action onFertiAdd(ItemStack itemStack) {
        int fertiValue = MRSetting.getFertiMap().isFertilizer(itemStack);
        if (fertiValue != MRConfig.Constants.EMPTY_FERTI && this.fertiTank < this.getPot().getMaxFerti()) {
            this.consumeFerti(-fertiValue);
            return new Action(ActionType.ADD_FERTI, 1);
        }
        return Action.EMPTY;
    }

    /**
     * 添加土壤
     *
     * @param itemStack
     * @return
     */
    public Action onSoilAdd(ItemStack itemStack) {
        if (itemStack != null) {
            int amount = ItemUtils.addItemInHandler(this.dirtInv, itemStack, true);
            if (amount > 0) {
                return new Action(ActionType.ADD_SOIL, amount);
            }
        }
        return Action.EMPTY;
    }

    /**
     * 执行收获的操作
     *
     * @return
     */
    public Action onHarvest(ItemStack itemStack) {
        World world = this.getLevel();
        if (world != null) {
            List<ItemStack> results = this.potHandler.getHarvest(world.random);
            return new Action(ActionType.HARVEST, 1, results);
        }
        return Action.EMPTY;
    }

    /**
     * 判断当前物品是否为带流体的桶
     *
     * @param itemStack
     * @return
     */
    public boolean isBucket(ItemStack itemStack) {
        return itemStack.getItem() == Items.WATER_BUCKET || itemStack.getItem() == Items.LAVA_BUCKET;
    }

    /**
     * 判断当前物品是否为收获工具
     *
     * @param itemStack
     * @return
     */
    public boolean isHarvestTools(ItemStack itemStack) {
        return itemStack.getItem() instanceof ShearsItem;
    }

    /**
     * 判断当前工具是否为移除工具
     *
     * @param itemStack
     * @return
     */
    public boolean isRemoveTools(ItemStack itemStack) {
        return itemStack.getItem() instanceof ShovelItem || itemStack.getItem() instanceof HoeItem;
    }

    /**
     * 消耗肥料，若consumeValue<0,则为添加肥料
     *
     * @param consumeValue
     * @return True则表示肥料足够消耗，False则表示肥料不足
     */
    public boolean consumeFerti(int consumeValue) {
        this.fertiTank -= consumeValue;
        if (this.fertiTank < 0) {
            this.fertiTank = 0;
            return false;
        }
        if (this.fertiTank > this.getPot().getMaxFerti()) {
            this.fertiTank = this.getPot().getMaxFerti();
        }
        return true;
    }

    /**
     * 1.若为植物，则在植物栏放置一次植物并设置该植物状态为0(一般为SeepDrop)
     *
     * @param itemStack
     * @return
     */
    public Action onPlantAdd(ItemStack itemStackIn) {
        Plant potPlant = MRSetting.getPlantMap().isPlantSeed(itemStackIn);
        if (potPlant != null) {
            for (int i = 0; i < this.dirtInv.getSlots(); i++) {
                ItemStack dirtStack = this.dirtInv.getStackInSlot(i);
                ItemStack plantStack = this.plantInv.getStackInSlot(i);
                if (!dirtStack.isEmpty() && plantStack.isEmpty() && potPlant.isSuitSoil(dirtStack)) {
                    ItemUtils.setStackInHandler(plantInv, itemStackIn, i, 1);
                    potHandler.addPlant(i, potPlant);
                    return new Action(ActionType.ADD_PLANT, 1);
                }
            }
        }
        return Action.EMPTY;
    }

    /**
     * 判断当前物品是否是肥料且能否进行施肥操作
     *
     * @param itemStack
     * @return
     */
    public boolean canUseFerti(ItemStack itemStack) {
        if (!this.isSoilEmpty()) {
            // 添加肥料
            int fertiValue = MRSetting.getFertiMap().isFertilizer(itemStack);
            if (fertiValue != MRConfig.Constants.EMPTY_FERTI && this.fertiTank < this.getPot().getMaxFerti()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 清空土壤，返回该物品
     *
     * @return
     */
    public Action onRemoveSoil() {
        // 若清空土壤
        for (int i = this.dirtInv.getSlots() - 1; i >= 0; i--) {
            ItemStack dirtStack = ItemUtils.clearStackInHandler(dirtInv, i);
            if (!dirtStack.isEmpty()) {
                return new Action(ActionType.REMOVE_SOIL, 1, dirtStack);
            }
        }
        return Action.EMPTY;
    }

    /**
     * 执行流体容器物品的检查和添加的操作，若返回True则对物品产生操作和更新
     *
     * @param itemStack
     * @return
     */
    public Action onHandleFluid(ItemStack itemStack) {
        if (!isSoilEmpty() && itemStack != null) {
            FluidStack fluidStack = FluidUtils.getFluidStack(itemStack);
            if (getPot().isSuitFluid(fluidStack)) {
                FluidStack copyStack = fluidStack.copy();
                if (copyStack.getAmount() > MRConfig.PotRule.FLUID_UNIT.get()) {
                    copyStack.setAmount(MRConfig.PotRule.FLUID_UNIT.get());
                }
                int amount = this.waterTank.fill(copyStack, FluidAction.EXECUTE);
                if (amount > 0) {
                    return new Action(ActionType.ADD_FLUID, amount);
                }
            }
        }
        return Action.EMPTY;
    }

    /**
     * 判断土壤栏是否为空
     *
     * @return
     */
    public boolean isSoilEmpty() {
        for (int i = 0; i < this.dirtInv.getSlots(); i++) {
            if (!this.dirtInv.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取指定格子植物的BlockState
     *
     * @param slot
     * @return
     */
    @Nullable
    public BlockState getPlantStage(int slot) {
        if (slot >= 0 && slot < this.plantInv.getSlots()) {
            return this.potHandler.getBlockStage(slot);
        }
        return null;
    }

    /**
     * 获取指定格子的土壤
     *
     * @param slot
     * @return
     */
    public ItemStack getDirtStack(int slot) {
        if (slot >= 0 && slot < this.dirtInv.getSlots()) {
            return this.dirtInv.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    /**
     * 获取更新速率
     *
     * @return
     */
    public final int getTickRate() {
        return MRConfig.PotRule.PLANT_TICK.get();
    }

    /**
     * 获取水份槽
     *
     * @return
     */
    public FluidTank getWaterTank() {
        return this.waterTank;
    }

    /**
     * 获取掉落物
     *
     * @return
     */
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.addAll(ItemUtils.getHandlerItem(this.dirtInv, false));
        drops.addAll(ItemUtils.getHandlerItem(this.plantInv, false));
        return drops;
    }

    /**
     * 获取产生的灵气
     *
     * @return
     */
    public List<Anima> getGenAnima() {
        List<Anima> genAnimas = this.potHandler.getGenAnimas();
        if (genAnimas != null && genAnimas.size() > 0) {
            genAnimas.addAll(this.getPot().getExtraAnimas());
        }
        return genAnimas;
    }

}
