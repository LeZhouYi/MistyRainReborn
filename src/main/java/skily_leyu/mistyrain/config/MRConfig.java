package skily_leyu.mistyrain.config;

import java.util.Random;

import net.minecraftforge.common.ForgeConfigSpec;

public class MRConfig {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static class TimeConfig{
        public static ForgeConfigSpec.IntValue MONTH_START; //第一次进入游戏时的起始月份
        public static ForgeConfigSpec.IntValue DAYS_PER_MONTH; //多少天一个月
    }

    public static class GameRule{
        public static ForgeConfigSpec.IntValue POT_PLANT_TICK; //盆栽的Tick
        public static ForgeConfigSpec.IntValue PLANT_GROW_CHANCE; //植物判定成功时的生长概率(0-1000)
    }

    static{
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Time settings").push("time");
        TimeConfig.MONTH_START = COMMON_BUILDER.comment("Month start when first start game").defineInRange("month_start", 2, 0, 11);
        TimeConfig.DAYS_PER_MONTH = COMMON_BUILDER.comment("Days per month").defineInRange("days_per_month", 30, 10, 60);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Pot plant settings").push("pot plant");
        GameRule.POT_PLANT_TICK = COMMON_BUILDER.comment("The tick of pot plant tileEntity").defineInRange("pot_plant_tick", 120, 20, 600);
        GameRule.PLANT_GROW_CHANCE = COMMON_BUILDER.comment("The chance of plant growing when passed sucessfully").defineInRange("plant_grow_chance", 100, 0, 1000);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static boolean canGrow(Random random){
        return random.nextInt(1000)<GameRule.PLANT_GROW_CHANCE.get();
    }

}
