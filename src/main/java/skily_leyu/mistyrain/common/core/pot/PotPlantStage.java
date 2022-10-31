package skily_leyu.mistyrain.common.core.pot;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import skily_leyu.mistyrain.common.core.plant.Plant;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRSetting;
import skily_leyu.mistyrain.tileentity.PotTileEntity;

public class PotPlantStage {

    private int nowStage; //当前状态
    private String plantKey; //当前植物

    public PotPlantStage(int nowStage,String plantKey){
        this.nowStage = nowStage;
        this.plantKey = plantKey;
    }

    /**
     * 执行植物生长的判定，消耗，状态更新
     * @param tileEntity
     */
    public void tick(PotTileEntity tileEntity){
        Plant plant = MRSetting.getPlantMap().getPlant(plantKey);
        World world = tileEntity.getLevel();
        if(plant!=null&&world!=null){
            if(MRConfig.canGrow(world.getRandom())){
                this.nowStage = plant.getNextStage(nowStage, world.getRandom());
            }
        }
    }

    public Plant getPlant() {
        return MRSetting.getPlantMap().getPlant(plantKey);
    }

    public int getState() {
        return nowStage;
    }

    public CompoundNBT save() {
        CompoundNBT plantTag = new CompoundNBT();
        plantTag.putInt("NowStage", nowStage);
        plantTag.putString("PlantKey", plantKey);
        return plantTag;
    }

    public static PotPlantStage load(CompoundNBT plantTag){
        int nowStage = plantTag.getInt("NowStage");
        String plantKey = plantTag.getString("PlantKey");
        return new PotPlantStage(nowStage, plantKey);
    }

}
