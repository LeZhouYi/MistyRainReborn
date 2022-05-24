package skily_leyu.mistyrain.common.core.time;

import net.minecraft.client.resources.I18n;

public enum MRSeason{

    SPRING,
    SUMMER,
    AUTUMN,
    WINTER;

    public String getI18nString(){
        return I18n.get(String.format("mrseason.%d.name",this.ordinal()));
    }

}