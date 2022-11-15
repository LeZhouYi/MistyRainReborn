package skily_leyu.mistyrain.common.utility;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class MRDebug {

    public static void printItemHandler(ItemStackHandler handlerIn){
        if(handlerIn!=null){
            for(int i = 0;i<handlerIn.getSlots();i++){
                System.out.println(ItemUtils.getRegistryName(handlerIn.getStackInSlot(i)));
            }
        }
    }

    public static void printItemStack(ItemStack itemStack){
        System.out.println(itemStack.toString());
    }

    public static void printString(String text){
        System.out.println(text);
    }

}
