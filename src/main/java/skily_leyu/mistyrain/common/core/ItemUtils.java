package skily_leyu.mistyrain.common.core;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ItemUtils {

    private ItemUtils(){}

    public static boolean isHandlerEmpty(ItemStackHandler handler){
        if(handler!=null){
            for(int i = 0;i<handler.getSlots();i++){
                ItemStack itemStack = handler.getStackInSlot(i);
                if(!itemStack.isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 添加物品给玩家
     */
    public static void addItemToPlayer(List<ItemStack> stacks, PlayerEntity playerEntity) {
        if (stacks != null && !playerEntity.isCreative()) {
            for (ItemStack itemStack : stacks) {
                if (itemStack != null && !itemStack.isEmpty()) {
                    ItemHandlerHelper.giveItemToPlayer(playerEntity, itemStack);
                }
            }
        }

    }

    /**
     * 获取Handler中的所有物品
     * @param willClear True即清空所有物品
     */
    @Nonnull
    public static List<ItemStack> getHandlerItem(ItemStackHandler handler, boolean willClear) {
        List<ItemStack> listStacks = new ArrayList<>();
        if (handler != null) {
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack slotStack = handler.getStackInSlot(i);
                if (!slotStack.isEmpty()) {
                    listStacks.add(slotStack);
                    if (willClear) {
                        handler.extractItem(i, slotStack.getCount(), false);
                    }
                }
            }
        }
        return listStacks;
    }

    /**
     * 替换物品
     */
    public static void replaceHandItem(PlayerEntity playerEntity, Hand hand, ItemStack itemStack) {
        if (!playerEntity.isCreative() && !itemStack.isEmpty()) {
            playerEntity.setItemInHand(hand, itemStack);
        }
    }

    /**
     * 消耗物品耐久，创造模式则不处理
     */
    public static void hurtItem(ServerPlayerEntity playerEntity, ItemStack itemStack, int damage) {
        if (!playerEntity.isCreative() && !itemStack.isEmpty()) {
            itemStack.hurt(damage, playerEntity.getRandom(), playerEntity);
        }
    }

    /**
     * 减少ItemStack的数量，若处于创造模式则不处理
     */
    public static void shrinkItem(PlayerEntity playerEntity, ItemStack itemStack, int amount) {
        if (!playerEntity.isCreative() && !itemStack.isEmpty()) {
            itemStack.shrink(amount);
        }
    }

    /**
     * 获取物品注册名
     */
    public static String getRegistryName(ItemStack itemStack) {
        return String.valueOf(itemStack.getItem().getRegistryName());
    }

    /**
     * 移除特定格子的物品并返回
     */
    @Nonnull
    public static ItemStack clearStackInHandler(ItemStackHandler handler, int slot) {
        if (handler != null && handler.getSlots() >= slot) {
            ItemStack backStack = handler.getStackInSlot(slot);
            handler.setStackInSlot(slot, ItemStack.EMPTY);
            return backStack;
        }
        return ItemStack.EMPTY;
    }

    /**
     * 将物品设置进ItemStackHandler中，不影响原物品数量
     * @param slot      要放置的位置
     * @param amount    要放置的数量
     */
    public static void setStackInHandler(ItemStackHandler handler, ItemStack itemStack, int slot, int amount) {
        if (itemStack.isEmpty() || handler.getSlots() <= slot || amount < 1) {
            return;
        }
        ItemStack splitStack = itemStack.copy().split(amount);
        if (!splitStack.isEmpty()) {
            handler.setStackInSlot(slot, splitStack);
        }
    }

    /**
     * 添加物品进ItemStackHandler并返回添加成功的物品数
     * @param once      若True，则成功放进一次后返回；若False，则放进直至不能再放进后返回
     */
    public static int addItemInHandler(ItemStackHandler handler, ItemStack itemStack, boolean once) {
        if (itemStack.isEmpty() || handler.getSlots() < 1) {
            return 0;
        }
        int itemNowCount = itemStack.getCount();
        for (int i = 0; i < handler.getSlots() && itemNowCount > 0; i++) {
            int slotLimit = handler.getSlotLimit(i);
            ItemStack slotStack = handler.getStackInSlot(i);
            int inputCount = 0;
            // 判断可输入数
            if (slotStack.isEmpty()) {
                // 物品栏为空的情况，直接执行放入物品操作
                inputCount = Math.min(slotLimit, itemNowCount);
            } else if (ItemStack.isSame(itemStack, slotStack)) {
                int limit = Math.min(slotLimit, itemStack.getMaxStackSize());
                inputCount = Math.min(limit - slotStack.getCount(), itemNowCount);
            }
            // 输入物品
            if (inputCount > 0 && !slotStack.isEmpty()) {
                ItemStack splitStack = itemStack.copy();// 不减少原物品数量
                if (!splitStack.isEmpty()) {
                    splitStack.setCount(inputCount + slotStack.getCount());// 物品叠加
                    handler.setStackInSlot(i, splitStack);
                    itemNowCount -= inputCount;// 计算已用物品数
                    if (once) {
                        break;
                    }
                }
            }
        }
        return itemStack.getCount() - itemNowCount;
    }

}
