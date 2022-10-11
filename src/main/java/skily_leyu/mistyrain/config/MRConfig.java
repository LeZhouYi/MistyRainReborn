package skily_leyu.mistyrain.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MRConfig {

    public static ForgeConfigSpec COMMON_CONFIG;

    public static class TimeConfig{
        public static ForgeConfigSpec.IntValue MONTH_START; //第一次进入游戏时的起始月份
        public static ForgeConfigSpec.IntValue DAYS_PER_MONTH; //多少天一个月
    }

    public static class GameRule{
        public static ForgeConfigSpec.IntValue POT_PLANT_TICK_RATE; //盆栽的Tick Rate
    }

    static{
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Time settings").push("time");

        TimeConfig.MONTH_START = COMMON_BUILDER.comment("Month start when first start game").defineInRange("month_start", 2, 0, 11);
        TimeConfig.DAYS_PER_MONTH = COMMON_BUILDER.comment("Days per month").defineInRange("days_per_month", 30, 10, 60);

        GameRule.POT_PLANT_TICK_RATE = COMMON_BUILDER.comment("Tick Rate of Pot Plant TileEntity").defineInRange("pot_plant_tick_rate", 120, 20, 600);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

}
