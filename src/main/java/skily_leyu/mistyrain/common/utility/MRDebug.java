package skily_leyu.mistyrain.common.utility;

import net.minecraftforge.items.ItemStackHandler;

public class MRDebug {

    public static void printItemHandler(ItemStackHandler handlerIn){
        if(handlerIn!=null){
            for(int i = 0;i<handlerIn.getSlots();i++){
                System.out.println(ItemUtils.getRegistryName(handlerIn.getStackInSlot(i)));
            }
        }
    }

    public static void printPlantHandler(){

    }

}
