package skily_leyu.mistyrain.common.core.plant;

import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public class PlantMap {
    private Map<String,Plant> plantMap;

    /**
     * 检查是否存在对应种子并返回对应植物，若无则返回null
     * @param itemStack
     * @return
     */
    @Nullable
    public Plant isPlantSeed(ItemStack itemStack){
        for(Plant plant:plantMap.values()){
            if(plant.containSeed(itemStack)){
                return plant;
            }
        }
        return null;
    }

    @Nullable
    public Plant getPlant(String name){
        if(plantMap.containsKey(name)){
            return plantMap.get(name);
        }
        return null;
    }

}
