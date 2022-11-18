package skily_leyu.mistyrain.config;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import skily_leyu.mistyrain.common.core.time.MRTimeDot;

public class MRConfig {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static class Client{
        public static ForgeConfigSpec.IntValue PARTICLE_AMOUNT; //粒子特效数量
    }

    public static class TimeRule{
        public static ForgeConfigSpec.IntValue MONTH_START; //第一次进入游戏时的起始月份
        public static ForgeConfigSpec.IntValue DAYS_PER_MONTH; //多少天一个月
        public static ForgeConfigSpec.DoubleValue TEMPER_CHANGE_FACTOR; //节气对温度影响因子，数值越大影响越大

        /**
         * 获取根据配置设置的TimeDot
         * @param world
         * @return
         */
        public static MRTimeDot getTimeDot(World world){
            return new MRTimeDot(world).update(MRConfig.TimeRule.MONTH_START.get(),MRConfig.TimeRule.DAYS_PER_MONTH.get());
        }

        /**
         * 获取当前节气对温度的影响
         * @param worldIn
         * @return
         */
        public static float getTemperChange(World worldIn){
            return (float)(new MRTimeDot(worldIn).getSolarTerm().getTemperFactor()*TEMPER_CHANGE_FACTOR.get());
        }

    }

    public static class Constants{
        public static int EMPTY_FERTI = -1; //标志当前物品不是肥料
        public static int HEALTH_FACTOR_CHECK = 100; //生长要属通过阀值
        public static int MAX_HEALTH = 200; //最大健康度累计
        public static int MAX_GROW_BOUND = 1000;//最大生长随机数
    }

    public static class PotRule{
        public static ForgeConfigSpec.IntValue PLANT_TICK; //盆栽的Tick
        public static ForgeConfigSpec.IntValue PLANT_GROW_CHANCE; //植物判定成功时的生长概率(0-1000)
        public static ForgeConfigSpec.IntValue BASE_HEALTH; //盆栽植物的基础健康值
        public static ForgeConfigSpec.IntValue GROW_HEALTH; //盆栽植物单个生长属性的最大健康判定量
        public static ForgeConfigSpec.DoubleValue WATER_FACTOR; //水份对盆栽生长的影响，数据越大则影响越小
        public static ForgeConfigSpec.DoubleValue LIGHT_FACTOR; //光照对盆栽生长的影响
        public static ForgeConfigSpec.DoubleValue FERTI_FACTOR; //肥料对盆栽生长的影响
        public static ForgeConfigSpec.DoubleValue TEMPER_FACTOR; //温度对盆栽生长的影响
        public static ForgeConfigSpec.IntValue FLUID_UNIT; //水桶对盆栽交互的容量
        public static ForgeConfigSpec.IntValue DEBUFF_HEALTH; //未通过检查时扣除健康的最大值
        public static ForgeConfigSpec.IntValue BUFF_HEALTH; //通过检查时增加健康的最大值
        public static ForgeConfigSpec.IntValue ANIMA_HONRI_RADIUS_BASE; //基础灵气检查的水平半径
        public static ForgeConfigSpec.IntValue ANIMA_VERTI_RADIUS_BASE; //基础灵气检查的竖直半径
        public static ForgeConfigSpec.IntValue ANIMA_COMBINE_AMOUNT; //灵气浓度进阶所有数量

                /**
         * 获取下一个水份影响的生长值
         * @param rand
         * @return
         */
        public static int nextWaterHealth(Random rand){
            return rand.nextInt((int)(GROW_HEALTH.get()*WATER_FACTOR.get()));
        }

        /**
         * 获取下一个光照影响的生长值
         * @param rand
         * @return
         */
        public static int nextLightHealth(Random rand){
            return rand.nextInt((int)(GROW_HEALTH.get()*LIGHT_FACTOR.get()));
        }

        /**
         * 获取下一个肥料影响的生长值
         * @param rand
         * @return
         */
        public static int nextFertiHealth(Random rand){
            return rand.nextInt((int)(GROW_HEALTH.get()*FERTI_FACTOR.get()));
        }

        /**
         * 获取下一个温度影响的生长值
         * @param rand
         * @return
         */
        public static int nextTemperHealth(Random rand){
            return rand.nextInt((int)(GROW_HEALTH.get()*TEMPER_FACTOR.get()));
        }

        /**
         * 判定是否可以生长
         * @param random
         * @return
         */
        public static boolean canGrow(Random random,int health){
            int rate = (int)(PotRule.PLANT_GROW_CHANCE.get()*(health/(float)(Constants.MAX_HEALTH/2)));
            return random.nextInt(Constants.MAX_GROW_BOUND)<rate;
        }

        /**
         * 判定是否通过生长要素判定
         * @param random
         * @param health
         * @return
         */
        public static boolean growCheck(Random random, int health){
            return random.nextInt(Constants.HEALTH_FACTOR_CHECK)<=health;
        }

        /**
         * 获取下一个健康度的变化值
         * @param random
         * @param isDebuff true则为减少
         * @return
         */
        public static int nextHealth(Random random, boolean isDebuff){
            return isDebuff?(-random.nextInt(DEBUFF_HEALTH.get()+1)):(random.nextInt(BUFF_HEALTH.get()+1));
        }

    }

    static{
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("时间系统设置").push("time");
        TimeRule.MONTH_START = COMMON_BUILDER.comment("首次进入游戏时的月份").defineInRange("month_start", 2, 0, 11);
        TimeRule.DAYS_PER_MONTH = COMMON_BUILDER.comment("每月多少天").defineInRange("days_per_month", 30, 10, 60);
        TimeRule.TEMPER_CHANGE_FACTOR = COMMON_BUILDER.comment("节气对温度影响的幅度，值越大则影响越大，即容易出现温度变化").defineInRange("temper_change_factor", 0.25D, 0.0D, 1.0D);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("盆栽系统设置").push("pot plant");
        PotRule.PLANT_TICK = COMMON_BUILDER.comment("盆栽植物时间刻,值越小生长越快").defineInRange("plant_tick", 120, 20, 600);
        PotRule.PLANT_GROW_CHANCE = COMMON_BUILDER.comment("盆栽生长要素判定成功后，进入下一生长阶段的基础概率").defineInRange("plant_grow_chance", 100, 0, Constants.MAX_GROW_BOUND);
        PotRule.BASE_HEALTH = COMMON_BUILDER.comment("刚种植时盆栽默认的健康值").defineInRange("base_health", 100, 0, Constants.MAX_HEALTH);
        PotRule.GROW_HEALTH = COMMON_BUILDER.comment("盆栽生长要素判定的基础健康值,值越大越容易通过生长判定和忽略生长要素影响").defineInRange("grow_health", 25, 0, 100);
        PotRule.WATER_FACTOR = COMMON_BUILDER.comment("水份对盆栽的影响程度，值越大影响越小").defineInRange("water_factor", 0.55D, 0.0D, 1.0D);
        PotRule.LIGHT_FACTOR = COMMON_BUILDER.comment("光照对盆栽的影响程度，值越大影响越小").defineInRange("light_factor", 0.75D, 0.0D, 1.0D);
        PotRule.FERTI_FACTOR = COMMON_BUILDER.comment("肥料对盆栽的影响程度，值越大影响越小").defineInRange("ferti_factor", 0.85D, 0.0D, 1.0D);
        PotRule.TEMPER_FACTOR = COMMON_BUILDER.comment("温度对盆栽的影响程度，值越大影响越小").defineInRange("temper_factor", 0.65D, 0.0D, 1.0D);
        PotRule.FLUID_UNIT = COMMON_BUILDER.comment("水桶对盆栽交互时的容量").defineInRange("fluid_unit", 1000, 500, 2000);
        PotRule.DEBUFF_HEALTH = COMMON_BUILDER.comment("未通过生长要素检查时扣除健康的最大值").defineInRange("debuff_health", 3 , 0, Constants.MAX_HEALTH);
        PotRule.BUFF_HEALTH = COMMON_BUILDER.comment("通过生长要素检查时增加健康的最大值").defineInRange("buff_health", 3, 0, Constants.MAX_HEALTH);
        PotRule.ANIMA_HONRI_RADIUS_BASE = COMMON_BUILDER.comment("基础盆栽植物灵气检查的水平半径，不算自身那格").defineInRange("anima_honri_radius_base", 1, 0, 7);
        PotRule.ANIMA_VERTI_RADIUS_BASE = COMMON_BUILDER.comment("基础盆栽植物灵气检查的竖直半径，不算自身那格").defineInRange("anima_verti_radius_base", 0, 0, 7);
        PotRule.ANIMA_COMBINE_AMOUNT = COMMON_BUILDER.comment("灵气浓度进阶所需数量").defineInRange("anima_combine_amount", 3, 2, 8);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();

        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("渲染设置").push("render");
        Client.PARTICLE_AMOUNT = CLIENT_BUILDER.comment("粒子特效渲染数量").defineInRange("particle_amount", 16, 0, 32);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

}
