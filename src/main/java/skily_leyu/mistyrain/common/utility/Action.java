package skily_leyu.mistyrain.common.utility;

import net.minecraft.item.ItemStack;

/**
 * 执行操作的结果记录
 */
public class Action {
    private ActionType actionType;
    private int amount;
    private ItemStack returnStack;

    public static final Action EMPTY = new Action();

    public Action(){
        this.actionType = ActionType.EMPTY;
        this.amount = 0;
        this.returnStack = ItemStack.EMPTY;
    }

    public Action(ActionType actionType,int amount){
        this.actionType = actionType;
        this.amount = amount;
        this.returnStack = ItemStack.EMPTY;
    }

    public Action(ActionType actionType,int amount,ItemStack returnStack){
        this.actionType = actionType;
        this.amount = amount;
        this.returnStack = returnStack;
    }

    /**
     * 获得操作后返还的物品
     * @return
     */
    public ItemStack getReturnStack(){
        return this.returnStack;
    }

    /**
     * 获得操作类型
     * @return
     */
    public ActionType getActionType(){
        return this.actionType;
    }

    /**
     * 获得操作后的整型数，一般指代减少/增加的物品数/流体量/耐久
     * @return
     */
    public int getAmount(){
        return this.amount;
    }

    /**
     * 判断当前操作是否为空
     * @return
     */
    public boolean isEmpty(){
        return this.actionType==ActionType.EMPTY;
    }

}
