package skily_leyu.mistyrain.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.common.core.action.Action;
import skily_leyu.mistyrain.common.core.action.ActionType;
import skily_leyu.mistyrain.common.core.plant.Plant;
import skily_leyu.mistyrain.common.core.pot.Pot;
import skily_leyu.mistyrain.common.core.pot.PotHandler;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;
import skily_leyu.mistyrain.config.MRConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TilePotBase extends TileBase implements ITickableTileEntity {

    protected ItemStackHandler dirtInv; // 土壤
    protected ItemStackHandler plantInv; // 植物
    protected PotHandler potHandler;//植物生长状态处理
    private int tickCount; // 计时
    private final String potKey; // 配置文件KeyName

    protected TilePotBase(TileEntityType<?> tileEntityType, String potKey) {
        super(tileEntityType);
        this.potKey = potKey;
        this.tickCount = getTickRate();
        this.potHandler = new PotHandler();
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
                this.potHandler.tick(this); //检查植物生长
                syncToTrackingClients();
            }
        }
    }

    @Override
    @Nonnull
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("DirtInv", this.dirtInv.serializeNBT());
        nbt.put("PlantInv", this.plantInv.serializeNBT());
        nbt.put("PotHandler",this.potHandler.serializeNBT());
        nbt.putInt("TickCount", tickCount);
        return super.save(nbt);
    }

    @Override
    public void load(@Nonnull BlockState blockState, @Nonnull CompoundNBT nbt) {
        super.load(blockState, nbt);
        this.dirtInv.deserializeNBT(nbt.getCompound("DirtInv"));
        this.plantInv.deserializeNBT(nbt.getCompound("PlantInv"));
        this.potHandler.deserializeNBT(nbt.getCompound("PotHandler"));
        this.tickCount = nbt.getInt("TickCount");
    }

    /**
     * 获取更新速率
     */
    public final int getTickRate() {
        return MRConfig.PotRule.PLANT_TICK.get();
    }

    /**
     * 判断当前物品是否为收获工具
     */
    public boolean isHarvestTools(ItemStack itemStack) {
        return itemStack.getItem() instanceof ShearsItem;
    }

    /**
     * 物品与盆栽交互的主入口
     */
    @Nonnull
    public Action onItemInteract(ItemStack itemStack) {
        Action action = Action.EMPTY;
        if (!itemStack.isEmpty()) {
            // 移除泥土/植物
            if (this.isRemoveTools(itemStack)) {
                action = this.onRemovePlant();
                if (action.isEmpty()) {
                    action = this.onRemoveSoil();
                }
            }
            // 收获产品
            else if (this.isHarvestTools(itemStack)) {
                action = this.onHarvest(itemStack);
            }
            // 添加土壤
            else if (!isSoilFull() && this.getPot().isSuitSoil(itemStack)) {
                action = this.onFluidAdd(itemStack); //添加流体类土壤
                if(action.isEmpty()){
                    action = this.onSoilAdd(itemStack); //添加方块类土壤
                }
            }
            //添加植物
            else if (!isSoilEmpty()){
                action = this.onPlantAdd(itemStack);
            }
        }
        if (!action.isEmpty()) {
            syncToTrackingClients();
        }
        return action;
    }

    public Action onFluidAdd(ItemStack itemStack) {
        FluidStack fluidStack = FluidUtils.getFluidStack(itemStack);
        if (!fluidStack.isEmpty()
                && fluidStack.getAmount() >= 1000) {
            int amount = ItemUtils.addItemInHandler(this.dirtInv, itemStack, true);
            if (amount > 0) {
                return new Action(ActionType.ADD_FLUID, 1000);
            }
        }
        return Action.EMPTY;
    }

    /**
     * 判断土壤栏是否全部存在土壤
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
     */
    @Nonnull
    public Action onRemovePlant() {
        // 清空植物
        for (int i = this.plantInv.getSlots() - 1; i >= 0; i--) {
            ItemStack plantStack = ItemUtils.clearStackInHandler(plantInv, i);
            if (!plantStack.isEmpty()) {
                return new Action(ActionType.REMOVE_PLANT, 1, plantStack);
            }
        }
        return Action.EMPTY;
    }

    /**
     * 添加土壤
     */
    @Nonnull
    public Action onSoilAdd(ItemStack itemStack) {
        int amount = ItemUtils.addItemInHandler(this.dirtInv, itemStack, true);
        if (amount > 0) {
            return new Action(ActionType.ADD_SOIL, amount);
        }
        return Action.EMPTY;
    }

    /**
     * 执行收获的操作
     */
    @Nonnull
    public Action onHarvest(ItemStack itemStack) {
        World world = this.getLevel();
        if (world != null) {
            List<ItemStack> results = new ArrayList<>();
            return new Action(ActionType.HARVEST, 1, results);
        }
        return Action.EMPTY;
    }

    /**
     * 判断当前工具是否为移除工具
     */
    public boolean isRemoveTools(ItemStack itemStack) {
        return itemStack.getItem() instanceof ShovelItem || itemStack.getItem() instanceof HoeItem;
    }

    /**
     * 1.若为植物，则在植物栏放置一次植物并设置该植物状态为0(一般为SeepDrop)
     */
    @Nonnull
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
     * 清空土壤，返回该物品
     */
    @Nonnull
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
     * 判断土壤栏是否为空
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
     */
    public ItemStack getDirtStack(int slot) {
        if (slot >= 0 && slot < this.dirtInv.getSlots()) {
            return this.dirtInv.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    /**
     * 获取掉落物
     */
    @Nonnull
    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        drops.addAll(ItemUtils.getHandlerItem(this.dirtInv, false));
        drops.addAll(ItemUtils.getHandlerItem(this.plantInv, false));
        return drops;
    }

    /**
     * 是否可以使用骨粉
     */
    public boolean canUseFerti(ItemStack itemStack){
        return itemStack.getItem()==Items.BONE_MEAL && !potHandler.isEmpty();
    }

}
