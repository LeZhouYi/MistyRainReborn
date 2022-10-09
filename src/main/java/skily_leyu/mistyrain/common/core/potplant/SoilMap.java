package skily_leyu.mistyrain.common.core.potplant;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.common.utility.ItemUtils;

public class SoilMap {
    private Map<SoilType,List<String>> soilMap; //泥土类型对应的泥土列表

    /**
     * 判断当前物品是否存在该类型的土壤列表中
     * @param type
     * @param itemStack
     * @return
     */
    public boolean contains(SoilType type, ItemStack itemStack){
        if(this.soilMap.containsKey(type)){
            return this.soilMap.get(type).contains(ItemUtils.getRegistryName(itemStack));
        }
        return false;
    }

}
