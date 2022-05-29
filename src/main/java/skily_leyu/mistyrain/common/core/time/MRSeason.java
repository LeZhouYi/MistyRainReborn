package skily_leyu.mistyrain.common.core.time;

import net.minecraft.client.resources.I18n;

public enum MRSeason{

    SPRING("spring"),
    SUMMER("summer"),
    AUTUMN("autumn"),
    WINTER("winter");

    private String name;

    private MRSeason(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public String getI18nString(){
        return I18n.get(String.format("mrseason.%d.name",this.ordinal()));
    }

    public MRSeason getNext() {
        return values()[(this.ordinal()+1)%4];
    }

}