package skily_leyu.mistyrain.common.core.pot;

import java.util.List;

import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.common.core.soil.SoilType;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRSetting;

public class Pot {

    private String name; //花盆名
    private int slotSize; //对应泥土可放格子数，可种植物数量
    private int tankSize; //储水量
    private List<String> suitSoils; //适合的土壤
    private List<SoilType> suitSoilType; //适合的土壤类型
    private List<String> suitFluids; //适合的水份
    private int fertilizer; //肥料值

    public int getSlotSize(){
        return this.slotSize;
    }

    public boolean isSuitSoil(ItemStack itemStack){
        if(this.suitSoils.contains(ItemUtils.getRegistryName(itemStack))){
            return true;
        }
        for(SoilType soilType:this.suitSoilType){
            if(MRSetting.getSoilMap().contains(soilType, itemStack)){
                return true;
            }
        }
        return false;
    }

}
