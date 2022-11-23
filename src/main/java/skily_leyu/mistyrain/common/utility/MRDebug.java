package skily_leyu.mistyrain.common.utility;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import skily_leyu.mistyrain.common.core.anima.Anima;

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

    public static void printAnimas(List<Anima> inputs){
        if(inputs!=null){
            for(Anima anima:inputs){
                System.out.println(anima.toString());
            }
        }
    }

}
