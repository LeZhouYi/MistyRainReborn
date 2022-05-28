package skily_leyu.mistyrain.common.core.time;

import net.minecraft.client.resources.I18n;

public enum MRMonth {
    JAN("jan"),
    FEB("feb"),
    MAR("mar"),
    APR("apr"),
    MAY("may"),
    JUN("jun"),
    JUL("jul"),
    AUG("aug"),
    SEPT("sept"),
    OCT("oct"),
    NOV("nov"),
    DEC("dec");

    private String name;

    private MRMonth(String name){
        this.name = name;
    }

    public String getI18nString(){
        return I18n.get(String.format("mrmonth.%d.name",this.ordinal()));
    }

    public String getName(){
        return name;
    }

}
