package skily_leyu.mistyrain.common.utility;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ItemUtils {

    /**
     * 判断物品是否拥有流体Capability并返回
     * @param itemStack
     * @return
     */
    public static LazyOptional<IFluidHandler> getFluidCaps(ItemStack itemStack){
        Capability<IFluidHandler> handler = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
        if(handler!=null){
            return itemStack.getCapability(handler);
        }
        return null;
    }

    /**
     * 替换物品
     * @param playerEntity
     * @param itemStack
     */
    public static void replaceHandItem(PlayerEntity playerEntity,Hand hand,ItemStack itemStack){
        if(!playerEntity.isCreative()&&!itemStack.isEmpty()){
            playerEntity.setItemInHand(hand, itemStack);
        }
    }

    /**
     * 减少ItemStack的数量/耐久，若处于创造模式则不处理
     * @param playerEntity
     * @param itemStack
     * @param amount
     */
    public static void shrinkItem(PlayerEntity playerEntity,ItemStack itemStack,int amount){
        if(!playerEntity.isCreative()&&amount>0){
            itemStack.shrink(amount);
        }
    }

    /**
     * 获取物品注册名
     * @param itemStack
     * @return
     */
    public static String getRegistryName(ItemStack itemStack){
        return String.valueOf(itemStack.getItem().getRegistryName());
    }

    /**
     * 移除特定格子的物品并返回
     * @param handler
     * @param slot
     * @return
     */
    public static ItemStack clearStackInHandler(ItemStackHandler handler,int slot){
        if(handler!=null&&handler.getSlots()>=slot){
            ItemStack backStack = handler.getStackInSlot(slot);
            ItemStack emptyStack = ItemStack.EMPTY;
            if(emptyStack!=null){
                handler.setStackInSlot(slot, emptyStack);
            }
            return backStack;
        }
        return ItemStack.EMPTY;
    }

    /**
     * 将物品设置进ItemStackHandler中，不影响原物品数量
     * @param handler
     * @param itemStack
     * @param slot 要放置的位置
     * @param amount 要放置的数量
     */
    public static void setStackInHandler(ItemStackHandler handler,ItemStack itemStack,int slot,int amount){
        if(itemStack.isEmpty()||handler.getSlots()<=slot||amount<1){
            return;
        }
        ItemStack splitStack = itemStack.copy().split(amount);
        if(splitStack!=null){
            handler.setStackInSlot(slot,splitStack);
        }
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
            int inputCount = 0;
            //判断可输入数
            if(slotStack.isEmpty()){
                //物品栏为空的情况，直接执行放入物品操作
                inputCount = Math.min(slotLimit, itemNowConut);
            }else if(ItemStack.isSame(itemStack, slotStack)){
                int limit = Math.min(slotLimit, itemStack.getMaxStackSize());
                inputCount = Math.min(limit-slotStack.getCount(), itemNowConut);
            }
            //输入物品
            if(inputCount>0&&slotStack!=null){
                ItemStack splitStack = itemStack.copy();//不减少原物品数量
                if(splitStack!=null){
                    splitStack.setCount(inputCount+slotStack.getCount());//物品叠加
                    handler.setStackInSlot(i,splitStack);
                    itemNowConut-=inputCount;//计算已用物品数
                    if(once){
                        break;
                    }
                }
            }
        }
        return itemStack.getCount()-itemNowConut;
    }

}
