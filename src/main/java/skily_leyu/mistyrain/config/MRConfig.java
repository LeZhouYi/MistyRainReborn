package skily_leyu.mistyrain.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MRConfig {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.IntValue MONTH_START;
    public static ForgeConfigSpec.IntValue DAYS_PER_MONTH;

    static{
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("Time settings").push("time");

        MONTH_START = COMMON_BUILDER.comment("Month start when first start game").defineInRange("month_start", 2, 0, 11);
        DAYS_PER_MONTH = COMMON_BUILDER.comment("Days per month").defineInRange("days_per_month", 30, 10, 60);

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

}
