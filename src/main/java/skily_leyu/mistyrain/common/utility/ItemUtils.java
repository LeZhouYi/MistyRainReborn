package skily_leyu.mistyrain.common.utility;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemUtils {

    public static String getRegistryName(ItemStack itemStack){
        return itemStack.getItem().getRegistryName().toString();
    }

    /**
     * 添加物品进ItemStackHandler并返回添加成功的物品数
     * @param handler
     * @param itemStack
     * @param once 若True，则成功放进一次后返回；若False，则放进直至不能再放进后返回
     * @return
     */
    public static int addItemInHandler(ItemStackHandler handler,ItemStack itemStack,boolean once){
        if(itemStack.isEmpty() || handler.getSlots()<1){
            return 0;
        }
        int itemNowConut = itemStack.getCount();
        for(int i = 0;i<handler.getSlots()&&itemNowConut>0;i++){
            int slotLimit = handler.getSlotLimit(i);
            ItemStack slotStack = handler.getStackInSlot(i);
            if(slotStack.isEmpty()){
                //物品栏为空的情况，直接执行放入物品操作
                int inputCount = (itemNowConut>slotLimit)?slotLimit:itemNowConut;
                if(inputCount>0){
                    handler.setStackInSlot(i, itemStack.copy().split(inputCount));
                    itemNowConut-=inputCount;
                    if(once){
                        break;
                    }
                }
            }else if(ItemStack.isSame(itemStack, slotStack)){
                int limit = (slotLimit>itemStack.getMaxStackSize())?itemStack.getMaxStackSize():slotLimit;
                int inputCount = (itemNowConut>limit)?limit:itemNowConut;
                if(inputCount>0){
                    handler.setStackInSlot(i, itemStack.copy().split(inputCount));
                    itemNowConut-=inputCount;
                    if(once){
                        break;
                    }
                }
            }
        }
        return itemStack.getCount()-itemNowConut;
    }

}
