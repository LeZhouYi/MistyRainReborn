package skily_leyu.mistyrain.common.core.pot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import skily_leyu.mistyrain.common.core.plant.Plant;
import skily_leyu.mistyrain.common.tileentity.TilePotBase;

public class PotHandler {

    private final Map<Integer, PotPlantStage> stageMap; // 植物对应的格子数及当前的生长状态

    public PotHandler() {
        this.stageMap = new HashMap<>();
    }

    /**
     * 对每个植物执行检查更新
     */
    public void tick(TilePotBase tileEntity) {
        for (PotPlantStage plantStage : stageMap.values()) {
            plantStage.tick(tileEntity);
        }
    }

    /**
     * 新增种植
     */
    public void addPlant(int i, @Nonnull Plant potPlant) {
        this.stageMap.put(i, new PotPlantStage(0, potPlant.getName()));
    }

    /**
     * 获取特定格子中植物的BlockState，若不存在植物或该植物Stage=0，即SeedDrop，则返回null
     *
     * @param slot 指定格子
     */
    @Nullable
    public BlockState getBlockStage(int slot) {
        if (this.stageMap.containsKey(slot)) {
            PotPlantStage plantStage = this.stageMap.get(slot);
            Plant plant = plantStage.getPlant();
            if (plant != null) {
                return plant.getBlockState(plantStage.getState());
            }
        }
        return null;
    }

    public CompoundNBT serializeNBT() {
        ListNBT nbtTagList = new ListNBT();
        for (Map.Entry<Integer, PotPlantStage> entry : this.stageMap.entrySet()) {
            CompoundNBT plantTag = entry.getValue().save();
            plantTag.putInt("Slot", entry.getKey());
            nbtTagList.add(plantTag);
        }
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Plants", nbtTagList);
        return nbt;

    }

    public void deserializeNBT(CompoundNBT nbt) {
        this.stageMap.clear();
        ListNBT nbtTagList = nbt.getList("Plants", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbtTagList.size(); i++) {
            CompoundNBT plantTag = nbtTagList.getCompound(i);
            int slot = plantTag.getInt("Slot");
            PotPlantStage plantStage = PotPlantStage.load(plantTag);
            this.stageMap.put(slot, plantStage);
        }
    }

    /**
     * 移除植物
     */
    public void removePlant(int i) {
        try{
            this.stageMap.remove(i);
        }catch (NullPointerException e){
            //pass
        }

    }

    /**
     * 判断是否为空
     */
    public boolean isEmpty() {
        return this.stageMap.size() < 1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, PotPlantStage> entry : this.stageMap.entrySet()) {
            String tempString = String.format("slot:%d,plantStage:%s", entry.getKey(), entry.getValue().toString());
            stringBuilder.append(tempString);
        }
        return stringBuilder.toString();
    }

    /**
     * 获得收获
     */
    public List<ItemStack> getHarvest(Random random) {
        List<ItemStack> results = new ArrayList<>();
        for (Map.Entry<Integer, PotPlantStage> entry : this.stageMap.entrySet()) {
            List<ItemStack> teResults = entry.getValue().getHarvest(random); // 获得产物
            if (teResults != null) {
                results.addAll(teResults);
                break;
            }
        }
        return results;
    }

}
