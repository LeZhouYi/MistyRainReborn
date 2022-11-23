package skily_leyu.mistyrain.common.core.pot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import skily_leyu.mistyrain.common.core.anima.Anima;
import skily_leyu.mistyrain.common.core.plant.Plant;
import skily_leyu.mistyrain.common.core.plant.PlantStageType;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRSetting;
import skily_leyu.mistyrain.tileentity.tileport.PotTileEntity;

public class PotPlantStage {

    private int health; //健康度
    private int nowStage; //当前状态
    private boolean canGenAnima; //标记当前是否能够产生灵气
    private String plantKey; //当前植物

    public PotPlantStage(int nowStage,String plantKey){
        this.nowStage = nowStage;
        this.plantKey = plantKey;
        this.canGenAnima = false;
        this.health = MRConfig.PotRule.BASE_HEALTH.get();
    }

    /**
     * 检查灵气是否满足生长需求，若满足则可以产生灵气，否则不能
     * @param potTileEntity
     * @return
     */
    public void updateCheckAnima(PotTileEntity potTileEntity,World worldIn,BlockPos pos,PlantStageType stageType){
        List<Anima> needAnimas = this.getPlant().getNeedAnima();
        if(needAnimas!=null){
            if(!stageType.canGenAnima()){
                this.canGenAnima = false;
            }
            else if(needAnimas.size()==0){
                this.canGenAnima = true;
            }else{
                List<Anima> gatherAnimas = new ArrayList<>();
                //获取检测坐标集
                int offsetHori = MRConfig.PotRule.ANIMA_HONRI_RADIUS_BASE.get();
                int offsetVerti = MRConfig.PotRule.ANIMA_VERTI_RADIUS_BASE.get();
                Iterable<BlockPos> checkPosList = BlockPos.betweenClosed(pos.offset(-offsetHori, -offsetVerti, -offsetHori), pos.offset(offsetHori, offsetVerti, offsetHori));
                //检测范围内盆栽产生的灵气并统计
                for(BlockPos checkPos:checkPosList){
                    //不统计本身
                    if(checkPos!=pos){
                        TileEntity checkEntity = worldIn.getBlockEntity(checkPos);
                        if(checkEntity!=null&&checkEntity instanceof PotTileEntity){
                            gatherAnimas.addAll(((PotTileEntity)checkEntity).getGenAnima());
                        }
                    }
                }
                //合并
                gatherAnimas = Anima.combineAnimas(gatherAnimas);
                this.canGenAnima = Anima.suitAnima(needAnimas, gatherAnimas);
            }
        }else{
            this.canGenAnima = false;
        }
    }

    /**
     * 更新健康值
     * @param update
     */
    public void updateHealth(int update){
        this.health+=update;
        if(this.health>MRConfig.Constants.MAX_HEALTH){
            this.health=MRConfig.Constants.MAX_HEALTH;
        }
        if(this.health<0){
            this.health=0;
        }
    }

    /**
     * 消耗肥料
     * @param tileEntity
     * @param rand
     * @return
     */
    public int consumerFerti(PotTileEntity tileEntity, Random rand){
        int fertiConsume = this.getPlant().getNeedFerti();
        return tileEntity.consumeFerti(fertiConsume)?MRConfig.PotRule.GROW_HEALTH.get():MRConfig.PotRule.nextFertiHealth(rand);
    }

    /**
     * 消耗水份
     * @param tileEntity
     * @return
     */
    public int consumeWater(PotTileEntity tileEntity,Random rand){
        int waterCousume = this.getPlant().getNeedWater();
        FluidTank tank = tileEntity.getWaterTank();
        if(this.getPlant().isSuitWater(tank.getFluid())){
            int consumeResult = tank.getFluidAmount()-waterCousume;
            tank.drain(waterCousume, FluidAction.EXECUTE);
            return consumeResult>0?MRConfig.PotRule.GROW_HEALTH.get():MRConfig.PotRule.nextWaterHealth(rand);
        }
        return 0;
    }

    /**
     * 检查光照
     * @param tileEntity
     * @return
     */
    public int checkLight(PotTileEntity tileEntity,World worldIn,Random rand){
        int light = worldIn.getRawBrightness(tileEntity.getBlockPos(), 0);
        return this.getPlant().isSuitLight(light)?MRConfig.PotRule.GROW_HEALTH.get():MRConfig.PotRule.nextLightHealth(rand);
    }

    /**
     * 检查温度
     * @param tileEntity
     * @return
     */
    public int checkTemper(PotTileEntity tileEntity,World worldIn,Random rand){
        BlockPos blockpos = tileEntity.getBlockPos();
        float temper = worldIn.getBiome(blockpos).getTemperature(blockpos)+MRConfig.TimeRule.getTemperChange(worldIn);
        return this.getPlant().isSuitTemper(temper)?MRConfig.PotRule.GROW_HEALTH.get():MRConfig.PotRule.nextTemperHealth(rand);
    }

    /**
     * 执行植物生长的判定，消耗，状态更新
     * @param tileEntity
     */
    public void tick(PotTileEntity tileEntity){
        Plant plant = MRSetting.getPlantMap().getPlant(plantKey);
        World world = tileEntity.getLevel();
        if(plant!=null&&world!=null){
            Random random = world.getRandom();
            //统计生长要素判定
            int heathGrowCheck = checkTemper(tileEntity, world,random)+checkLight(tileEntity, world, random)+consumeWater(tileEntity, random)+consumerFerti(tileEntity, random);
            //是否满足生长要素,不满足时概率通过
            if(MRConfig.PotRule.growCheck(random, heathGrowCheck)){
                this.updateHealth(MRConfig.PotRule.nextHealth(random, false));
                //是否满足生长到下一阶段，健康值越高越容易进入下一阶段，默认最高为50%
                if(MRConfig.PotRule.canGrow(world.getRandom(),this.health)){
                    this.nowStage = plant.getNextStage(nowStage, world.getRandom());
                }
            }else{
                this.updateHealth(MRConfig.PotRule.nextHealth(random, true));
            }
            //更新灵气状态
            PlantStageType plantStageType = plant.getPlantStage(this.nowStage);
            updateCheckAnima(tileEntity,world,tileEntity.getBlockPos(),plantStageType);
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

    public PotPlantStage setCanGenAnima(boolean canGen){
        this.canGenAnima = canGen;
        return this;
    }

    public CompoundNBT save() {
        CompoundNBT plantTag = new CompoundNBT();
        plantTag.putInt("NowStage", nowStage);
        plantTag.putString("PlantKey", plantKey);
        plantTag.putInt("Health", health);
        plantTag.putBoolean("CanGenAnima", canGenAnima);
        return plantTag;
    }

    public static PotPlantStage load(CompoundNBT plantTag){
        int nowStage = plantTag.getInt("NowStage");
        String plantKey = plantTag.getString("PlantKey");
        int health = plantTag.getInt("Health");
        boolean canGen = plantTag.getBoolean("canGenAnima");
        return new PotPlantStage(nowStage, plantKey).setHealth(health).setCanGenAnima(canGen);
    }

    @Override
    public String toString() {
        return String.format("health:%d,stage:%d,plant:%s", this.health,this.nowStage,this.plantKey);
    }

    /**
     * 返回当前产生的灵气
     * @return
     */
    public List<Anima> getGenAnimas(){
        List<Anima> genAnimas = new ArrayList<>();
        if(!this.canGenAnima){
            Plant plant = MRSetting.getPlantMap().getPlant(plantKey);
            if(plant!=null){
                genAnimas.addAll(plant.getGenAnima());
            }
        }
        return genAnimas;
    }

}
