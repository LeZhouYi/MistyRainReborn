package skily_leyu.mistyrain.common.core.time;

import net.minecraft.client.resources.I18n;

public enum MRSolarTerm {
    SPRING_BEGINS("spring_begins",-0.75F),
    THE_RAINS("the_rains",-0.45F),
    INSECTS_AWAKEN("insets_awaken",-0.15F),
    VERNAL_EQUINOX("vernal_equinox",0.0F),
    CLEAR_AND_BRIGHT("clear_and_bright",0.15F),
    GRAIN_RAIN("grain_rain",0.35F),
    SUMMER_BEGINS("summer_begins",0.5F),
    GRAIN_BUDS("grain_buds",0.65F),
    GRAIN_IN_EAR("grain_in_ear",0.75F),
    SUMMER_SOLSTICE("summer_solstice",0.8F),
    SLIGHT_HEAT("slight_heat",0.9F),
    GREAT_HEAT("great_heat",1.0F),
    AUTUMN_BEGINS("autumn_begins",0.75F),
    STOPPING_THE_HEAT("stopping_the_heat",0.45F),
    WHITE_DEWS("white_dews",0.15F),
    AUTUMN_EQUINOX("autumn_equinox",0.0F),
    COLD_DEWS("cold_dews",-0.15F),
    HOAR_FROST_FALLS("hoar_frost_falls",-0.35F),
    WINTER_BEGINS("winter_begins",-0.5F),
    LIGHT_SNOW("light_snow",-0.65F),
    HEAVY_SNOW("heavy_snow",-0.75F),
    WINTER_SOLSTICE("winter_solstice",-0.8F),
    SLIGHT_COLD("slight_cold",0.9F),
    GREAT_COLD("great_cold",-1.0F);

    private final String name;
    private final float temperFactor;//基础温度影响倍率

    MRSolarTerm(String name,float temperFactor){
        this.name = name;
        this.temperFactor = temperFactor;
    }

    public String getName(){
        return this.name;
    }

    public float getTemperFactor(){
        return this.temperFactor;
    }

    public String getI18nString(){
        return I18n.get(String.format("mr_solar_term.%d.name",this.ordinal()));
    }

    public MRSolarTerm getNext() {
        return values()[(this.ordinal()+1)%24];
    }
}
