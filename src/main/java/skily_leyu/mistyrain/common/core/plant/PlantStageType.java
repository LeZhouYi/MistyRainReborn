package skily_leyu.mistyrain.common.core.plant;

/**
 * @author skily_leyu
 */
public enum PlantStageType {

    SEED_DROP,
    SPROUT,
    ROOT_DEPTH,
    VERDANT,
    IN_BUD,
    FULL_BLOWN,
    FRUIT,
    FRUIT_MATURE,
    LEAVES_YELLOW,
    LEAVES_FALL,
    FADE,
    GATHER_1,
    GATHER_2,
    PLANT_DROP;

    /**
     * 判断当前状态是否可以产生灵气
     * @param type
     * @return
     */
    public boolean canGenAnima(){
        if(this==SEED_DROP||this==SPROUT||this==ROOT_DEPTH||this==FADE||this==PLANT_DROP){
            return false;
        }
        return true;
    }

}
