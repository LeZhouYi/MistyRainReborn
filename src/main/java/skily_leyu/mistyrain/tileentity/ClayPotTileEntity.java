package skily_leyu.mistyrain.tileentity;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import skily_leyu.mistyrain.common.core.action.Action;
import skily_leyu.mistyrain.common.core.action.ActionType;
import skily_leyu.mistyrain.common.utility.FluidUtils;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.tileentity.tileport.PotTileEntity;

public class ClayPotTileEntity extends PotTileEntity {

    public ClayPotTileEntity() {
        super(MRTileEntity.clayPotTileEntity.get(), "mr_clay_pot");
    }

    /**
     * 更改添加土壤为添加水份，但仍为保留一份物品占据土壤栏
     */
    @Override
    public Action onSoilAdd(ItemStack itemStack) {
        FluidStack fluidStack = FluidUtils.getFluidStack(itemStack);
        if (!fluidStack.isEmpty()
                && fluidStack.getAmount() >= MRConfig.PotRule.FLUID_UNIT.get()) {
            int amount = ItemUtils.addItemInHandler(this.dirtInv, itemStack, true);
            if (amount > 0) {
                return new Action(ActionType.ADD_FLUID, MRConfig.PotRule.FLUID_UNIT.get());
            }
        }
        return Action.EMPTY;
    }

    /**
     * 返还土壤时直接清空，没有额外返还
     */
    @Override
    public Action onRemoveSoil() {
        Action action = super.onRemoveSoil();
        if (!action.isEmpty()) {
            action.setReturnStacks(new ArrayList<>());
        }
        return action;
    }

}
