package skily_leyu.mistyrain.common.core.time;

import net.minecraft.client.resources.I18n;

public enum MRSolarTerm {
    SPRING_BEGIN,
    THE_RAINS,
    INSECTS_AWAKEN,
    VERNAL_EQUINOX,
    CLEAR_AND_BRIGHT,
    GRAIN_RAIN,
    SUMMER_BEGINS,
    GRAIN_BUDS,
    GRAIN_IN_EAR,
    SUMMER_SOLSTICE,
    SLIGHT_HEAT,
    GREAT_HEAT,
    AUTUMN_BEGINS,
    STOPPING_THE_HEAT,
    WHITE_DEWS,
    AUTUMN_EQUINOX,
    COLD_DEWS,
    HOAR_FROST_FALLS,
    WINTER_BEGINS,
    LIGHT_SNOW,
    HEAVY_SNOW,
    WINTER_SOLSTICE,
    SLIGHT_COLD,
    GREAT_COLD;

    public String getI18nString(){
        return I18n.get(String.format("mrsolarterm.%d.name",this.ordinal()));
    }
}
