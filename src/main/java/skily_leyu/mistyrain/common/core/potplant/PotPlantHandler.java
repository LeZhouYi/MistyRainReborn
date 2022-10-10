package skily_leyu.mistyrain.common.core.potplant;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import skily_leyu.mistyrain.config.MRSetting;

public class PotPlantHandler {

    private Map<Integer,Integer> stageMap; //植物对应的格子数及当前的生长状态
    private Map<Integer,String> plantMap; //植物对应的格子对应的植物

    public PotPlantHandler(){
        this.stageMap = new HashMap<>();
        this.plantMap = new HashMap<>();
    }

    /**
     * 新增种植
     * @param i
     * @param potPlant
     */
    public void addPlant(int i, @Nonnull PotPlant potPlant) {
        this.stageMap.put(i, 0);//设置状态为0(一般为SeedDrop)
        this.plantMap.put(i, potPlant.getName());
    }

    /**
     * 获取特定格子中植物的BlockState，若不存在植物或该植物Stage=0，即SeedDrop，则返回null
     * @param slot 指定格子
     * @return
     */
    @Nullable
    public BlockState getBlockStage(int slot){
        if(!this.stageMap.containsKey(slot)||!this.plantMap.containsKey(slot)){
            return null;
        }
        String name = this.plantMap.get(slot);
        PotPlant potPlant = MRSetting.potPlants.getPotPlant(name);
        if(potPlant!=null){
            return potPlant.getBlockState(stageMap.get(slot));
        }
        return null;
    }

    public CompoundNBT serializeNBT(){
        ListNBT nbtTagList = new ListNBT();
        for(Integer i:plantMap.keySet()){
            CompoundNBT plantTag = new CompoundNBT();
            plantTag.putInt("Slot", i);
            plantTag.putInt("Stage", stageMap.get(i));
            plantTag.putString("Plant", plantMap.get(i));
            nbtTagList.add(plantTag);
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Plants",nbtTagList);
        return nbt;

    }

    public void deserializeNBT(CompoundNBT nbt) {
        ListNBT nbtTagList = nbt.getList("Plants", Constants.NBT.TAG_COMPOUND);
        for(int i = 0;i<nbtTagList.size();i++){
            CompoundNBT plantTag = nbtTagList.getCompound(i);
            int slot = plantTag.getInt("Slot");
            this.stageMap.put(slot, plantTag.getInt("Stage"));
            this.plantMap.put(slot,plantTag.getString("Plant"));
        }
    }

}
