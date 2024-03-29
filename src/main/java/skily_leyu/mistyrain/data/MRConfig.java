package skily_leyu.mistyrain.data;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import skily_leyu.mistyrain.common.core.time.MRTimeDot;

public class MRConfig {

    private MRConfig() {
    }

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static class Client {
        private Client() {
        }

        public static ForgeConfigSpec.IntValue PARTICLE_AMOUNT; //粒子特效数量
    }

    public static class TimeRule {
        private TimeRule() {
        }

        public static ForgeConfigSpec.IntValue MONTH_START; //第一次进入游戏时的起始月份
        public static ForgeConfigSpec.IntValue DAYS_PER_MONTH; //多少天一个月
        public static ForgeConfigSpec.DoubleValue TEMPER_CHANGE_FACTOR; //节气对温度影响因子，数值越大影响越大

        /**
         * 获取根据配置设置的TimeDot
         */
        public static MRTimeDot getTimeDot(World world) {
            return new MRTimeDot(world).update(MRConfig.TimeRule.MONTH_START.get(), MRConfig.TimeRule.DAYS_PER_MONTH.get());
        }

        /**
         * 获取当前节气对温度的影响
         */
        public static float getTemperChange(World worldIn) {
            return (float) (new MRTimeDot(worldIn).getSolarTerm().getTemperFactor() * TEMPER_CHANGE_FACTOR.get());
        }

    }

    public static class PotRule {
        private PotRule() {
        }

        public static ForgeConfigSpec.IntValue PLANT_TICK; //盆栽的Tick
        public static ForgeConfigSpec.IntValue PLANT_GROW_RATE; //盆栽植物生长概率

        public static boolean canGrow(Random random) {
            return random.nextInt(1000) < PLANT_GROW_RATE.get();
        }

    }

    static {
        ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();
        commonBuilder.comment("时间系统设置").push("time");
        TimeRule.MONTH_START = commonBuilder.comment("首次进入游戏时的月份").defineInRange("month_start", 2, 0, 11);
        TimeRule.DAYS_PER_MONTH = commonBuilder.comment("每月多少天").defineInRange("days_per_month", 30, 10, 60);
        TimeRule.TEMPER_CHANGE_FACTOR = commonBuilder.comment("节气对温度影响的幅度，值越大则影响越大，即容易出现温度变化").defineInRange("temper_change_factor", 0.25D, 0.0D, 1.0D);
        commonBuilder.pop();

        commonBuilder.comment("盆栽系统设置").push("pot plant");
        PotRule.PLANT_TICK = commonBuilder.comment("盆栽植物时间刻,值越小生长越快").defineInRange("plant_tick", 120, 20, 600);
        PotRule.PLANT_GROW_RATE = commonBuilder.comment("盆栽植物生长概率，值越大生长越快").defineInRange("plant_grow_rate", 100, 0, 1000);
        commonBuilder.pop();

        COMMON_CONFIG = commonBuilder.build();

        ForgeConfigSpec.Builder clientBuilder = new ForgeConfigSpec.Builder();
        clientBuilder.comment("渲染设置").push("render");
        Client.PARTICLE_AMOUNT = clientBuilder.comment("粒子特效渲染数量").defineInRange("particle_amount", 16, 0, 32);
        clientBuilder.pop();

        CLIENT_CONFIG = clientBuilder.build();
    }

}
