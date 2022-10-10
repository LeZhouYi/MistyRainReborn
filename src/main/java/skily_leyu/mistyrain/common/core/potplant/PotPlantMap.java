package skily_leyu.mistyrain.common.core.potplant;

import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public class PotPlantMap {
    private Map<String,PotPlant> potPlants;

    /**
     * 检查是否存在对应种子并返回对应植物，若无则返回null
     * @param itemStack
     * @return
     */
    @Nullable
    public PotPlant isPlantSeed(ItemStack itemStack){
        for(String key:potPlants.keySet()){
            PotPlant potPlant = potPlants.get(key);
            if(potPlant.containSeed(itemStack)){
                return potPlant;
            }
        }
        return null;
    }

    @Nullable
    public PotPlant getPotPlant(String name){
        if(potPlants.containsKey(name)){
            return potPlants.get(name);
        }
        return null;
    }

}
