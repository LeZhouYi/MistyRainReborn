package skily_leyu.mistyrain.common.core.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

/**
 * 执行操作的结果记录
 */
public class Action {
    @Nonnull
    private final ActionType actionType;
    private final int amount;
    @Nonnull
    private List<ItemStack> returnStacks;

    @Nonnull
    public static final Action EMPTY = new Action();

    public Action() {
        this(ActionType.EMPTY, 0, new ArrayList<>());
    }

    public Action(ActionType actionType, int amount) {
        this(actionType, amount, new ArrayList<>());
    }

    public Action(ActionType actionType, int amount, ItemStack returnStack) {
        this.actionType = (actionType != null) ? actionType : ActionType.EMPTY;
        this.amount = amount;
        this.returnStacks = new ArrayList<>();
        returnStacks.add(returnStack);
    }

    public Action(ActionType actionType, int amount, List<ItemStack> returnStacks) {
        this.actionType = (actionType != null) ? actionType : ActionType.EMPTY;
        this.amount = amount;
        this.returnStacks = (returnStacks != null) ? returnStacks : new ArrayList<>();
    }

    /**
     * 设置返还物品
     */
    public void setReturnStacks(List<ItemStack> returnStacks) {
        this.returnStacks = (returnStacks != null) ? returnStacks : new ArrayList<>();
    }

    /**
     * 获得操作后返还的物品
     */
    @Nonnull
    public List<ItemStack> getReturnStack() {
        return this.returnStacks;
    }

    /**
     * 获得操作类型
     */
    @Nonnull
    public ActionType getActionType() {
        return this.actionType;
    }

    /**
     * 获得操作后的整型数，一般指代减少/增加的物品数/流体量/耐久
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * 判断当前操作是否为空
     */
    public boolean isEmpty() {
        return this.actionType == ActionType.EMPTY;
    }

    @Override
    public String toString() {
        return String.format("type:%s;amount:%d;return:%s",this.actionType,this.amount,this.returnStacks);
    }

}
