package skily_leyu.mistyrain.common.core.math;

public class MathUtils {

    private MathUtils() {
    }

    /**
     * true则表示var在[var1,var2]范围内
     */
    public static boolean isBetween(int[] borders, int value) {
        if (borders != null && borders.length == 2) {
            return value >= borders[0] && value <= borders[1];
        }
        return false;
    }
}
