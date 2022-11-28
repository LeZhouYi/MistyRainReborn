package skily_leyu.mistyrain.common.core.soil;

import java.util.Map;

import net.minecraft.item.ItemStack;
import skily_leyu.mistyrain.common.utility.ItemUtils;
import skily_leyu.mistyrain.config.MRConfig;

public class FertiMap {

    private Map<String, Integer> fertiMap;

    /**
     * 判断当前物品是不是肥料，并返回该肥料的营养值
     *
     * @param itemStackIn
     * @return
     */
    public int isFertilizer(ItemStack itemStackIn) {
        if (itemStackIn != null && !itemStackIn.isEmpty()) {
            String itemName = ItemUtils.getRegistryName(itemStackIn);
            if (this.fertiMap.containsKey(itemName)) {
                return this.fertiMap.get(itemName);
            }
        }
        return MRConfig.Constants.EMPTY_FERTI;
    }

}
