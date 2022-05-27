package skily_leyu.mistyrain.common.core.time;

import net.minecraft.client.resources.I18n;

public enum MRMonth {
    JAN,
    FEB,
    MAR,
    APR,
    MAY,
    JUN,
    JUL,
    AUG,
    SEPT,
    OCT,
    NOV,
    DEC;

    public String getI18nString(){
        return I18n.get(String.format("mrmonth.%d.name",this.ordinal()));
    }

}
