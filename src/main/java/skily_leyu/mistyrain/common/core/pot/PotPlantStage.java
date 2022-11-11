package skily_leyu.mistyrain.common.core.pot;

import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import skily_leyu.mistyrain.common.core.plant.Plant;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRConstants;
import skily_leyu.mistyrain.config.MRSetting;
import skily_leyu.mistyrain.tileentity.tileport.PotTileEntity;

public class PotPlantStage {

    private int health; //健康度
    private int nowStage; //当前状态
    private String plantKey; //当前植物

    public PotPlantStage(int nowStage,String plantKey){
        this.nowStage = nowStage;
        this.plantKey = plantKey;
        this.health = MRConstants.POT_PLANT_HEALTH_BASE;
    }

    /**
     * 消耗水份
     * @param tileEntity
     * @return
     */
    public int consumeWater(PotTileEntity tileEntity){
        int waterCousume = this.getPlant().getNeedWater();
        FluidTank tank = tileEntity.getWaterTank();
        int consumeResult = tank.getFluidAmount()-waterCousume;
        tank.drain(waterCousume, FluidAction.EXECUTE);
        return consumeResult>0?MRConstants.POT_PLANT_GROW_HEALTH:0;
    }

    /**
     * 检查光照
     * @param tileEntity
     * @return
     */
    public int checkLight(PotTileEntity tileEntity,World worldIn){
        int light = worldIn.getRawBrightness(tileEntity.getBlockPos(), 0);
        System.out.println(light);
        return 0;
    }

    /**
     * 执行植物生长的判定，消耗，状态更新
     * @param tileEntity
     */
    public void tick(PotTileEntity tileEntity){
        Plant plant = MRSetting.getPlantMap().getPlant(plantKey);
        World world = tileEntity.getLevel();
        if(plant!=null&&world!=null){
            int healthGrow = checkLight(tileEntity, world);
            if(MRConfig.canGrow(world.getRandom())){
                this.nowStage = plant.getNextStage(nowStage, world.getRandom());
            }
        }
    }

    /**
     * 获取当前植物的配置数据
     * @return
     */
    public Plant getPlant() {
        return MRSetting.getPlantMap().getPlant(plantKey);
    }

    public int getState() {
        return nowStage;
    }

    public PotPlantStage setHealth(int health){
        this.health = health;
        return this;
    }

    public CompoundNBT save() {
        CompoundNBT plantTag = new CompoundNBT();
        plantTag.putInt("NowStage", nowStage);
        plantTag.putString("PlantKey", plantKey);
        plantTag.putInt("Health", health);
        return plantTag;
    }

    public static PotPlantStage load(CompoundNBT plantTag){
        int nowStage = plantTag.getInt("NowStage");
        String plantKey = plantTag.getString("PlantKey");
        int health = plantTag.getInt("Health");
        return new PotPlantStage(nowStage, plantKey).setHealth(health);
    }

}
