package skily_leyu.mistyrain.common.core.time;

import net.minecraft.client.resources.I18n;

public enum MRSolarTerm {
    SPRING_BEGIN("spring_begins"),
    THE_RAINS("the_rains"),
    INSECTS_AWAKEN("insets_awaken"),
    VERNAL_EQUINOX("vernal_equinox"),
    CLEAR_AND_BRIGHT("clear_and_bright"),
    GRAIN_RAIN("grain_rain"),
    SUMMER_BEGINS("summer_begins"),
    GRAIN_BUDS("grain_buds"),
    GRAIN_IN_EAR("grain_in_ear"),
    SUMMER_SOLSTICE("summer_solstice"),
    SLIGHT_HEAT("slight_heat"),
    GREAT_HEAT("great_heat"),
    AUTUMN_BEGINS("autumn_begins"),
    STOPPING_THE_HEAT("stopping_the_heat"),
    WHITE_DEWS("white_dews"),
    AUTUMN_EQUINOX("autumn_equinox"),
    COLD_DEWS("cold_dews"),
    HOAR_FROST_FALLS("hoar_frost_falls"),
    WINTER_BEGINS("winter_begins"),
    LIGHT_SNOW("light_snow"),
    HEAVY_SNOW("heavy_snow"),
    WINTER_SOLSTICE("winter_solstice"),
    SLIGHT_COLD("slight_cold"),
    GREAT_COLD("great_cold");

    private String name;

    private MRSolarTerm(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getI18nString(){
        return I18n.get(String.format("mrsolarterm.%d.name",this.ordinal()));
    }
}
