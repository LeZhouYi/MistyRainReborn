package skily_leyu.mistyrain.common.utility;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * 执行操作的结果记录
 */
public class Action {
    private ActionType actionType;
    private int amount;
    private List<ItemStack> returnStacks;

    public static final Action EMPTY = new Action();

    public Action() {
        this.actionType = ActionType.EMPTY;
        this.amount = 0;
        this.returnStacks = new ArrayList<>();
    }

    public Action(ActionType actionType, int amount) {
        this.actionType = actionType;
        this.amount = amount;
        this.returnStacks = new ArrayList<>();
    }

    public Action(ActionType actionType, int amount, ItemStack returnStack) {
        this.actionType = actionType;
        this.amount = amount;
        this.returnStacks = new ArrayList<>();
        returnStacks.add(returnStack);
    }

    public Action(ActionType actionType, int amount, List<ItemStack> returnStacks) {
        this.actionType = actionType;
        this.amount = amount;
        this.returnStacks = returnStacks;
    }

    /**
     * 设置返还物品
     *
     * @param returnStacks
     */
    public void setReturnStacks(List<ItemStack> returnStacks) {
        this.returnStacks = returnStacks;
    }

    /**
     * 获得操作后返还的物品
     *
     * @return
     */
    public List<ItemStack> getReturnStack() {
        return this.returnStacks;
    }

    /**
     * 获得操作类型
     *
     * @return
     */
    public ActionType getActionType() {
        return this.actionType;
    }

    /**
     * 获得操作后的整型数，一般指代减少/增加的物品数/流体量/耐久
     *
     * @return
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * 判断当前操作是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return this.actionType == ActionType.EMPTY;
    }

}
