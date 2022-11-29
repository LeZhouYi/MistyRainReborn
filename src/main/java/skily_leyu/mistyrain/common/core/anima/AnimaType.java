package skily_leyu.mistyrain.common.core.anima;

import javax.annotation.Nonnull;

public enum AnimaType {

    NULL,
    BLANK,
    GOLD,
    WOOD,
    WATER,
    FIRE,
    SOIL;

    public static final AnimaType[] ELEMENTS = new AnimaType[] { GOLD, WOOD, WATER, FIRE, SOIL };

    /**
     * 获取当前灵气相克的灵气类型
     *
     * @return
     */
    @Nonnull
    public AnimaType getDecayType() {
        if (this == WOOD) {
            return GOLD;
        } else if (this == GOLD) {
            return FIRE;
        } else if (this == FIRE) {
            return WATER;
        } else if (this == WATER) {
            return SOIL;
        } else if (this == SOIL) {
            return WOOD;
        } else {
            return NULL;
        }
    }

    /**
     * 获取当前灵气相生的灵气类型
     *
     * @return
     */
    @Nonnull
    public AnimaType getGenType() {
        if (this == GOLD) {
            return WATER;
        } else if (this == WATER) {
            return WOOD;
        } else if (this == WOOD) {
            return FIRE;
        } else if (this == FIRE) {
            return SOIL;
        } else if (this == SOIL) {
            return GOLD;
        } else {
            return NULL;
        }
    }

}
