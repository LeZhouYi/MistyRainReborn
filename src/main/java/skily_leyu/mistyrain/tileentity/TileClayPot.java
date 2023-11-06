package skily_leyu.mistyrain.tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import skily_leyu.mistyrain.common.core.action.Action;
import skily_leyu.mistyrain.common.core.action.ActionType;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;

public class TileClayPot extends TilePotBase {

    public TileClayPot() {
        super(MRTiles.clayPotTileEntity.get(), "mr_clay_pot");
    }

    /**
     * 更改添加土壤为添加水份，但仍为保留一份物品占据土壤栏
     */
    @Override
    @Nonnull
    public Action onSoilAdd(ItemStack itemStack) {
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
     * 返还土壤时直接清空，没有额外返还
     */
    @Override
    @Nonnull
    public Action onRemoveSoil() {
        Action action = super.onRemoveSoil();
        if (!action.isEmpty()) {
            action.setReturnStacks(new ArrayList<>());
        }
        return action;
    }

    /**
     * 获取掉落物，排除土壤
     */
    @Override
    @Nonnull
    public List<ItemStack> getDrops() {
        return new ArrayList<>(ItemUtils.getHandlerItem(this.plantInv, false));
    }

}
