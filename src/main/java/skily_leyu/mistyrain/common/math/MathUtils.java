package skily_leyu.mistyrain.common.math;

public class MathUtils {

    /**
     * true则表示var在[var1,var2]范围内
     */
    public static boolean isBetween(int[] borders, int var){
        if(borders!=null&&borders.length==2){
            return var>=borders[0]&&var<=borders[1];
        }
        return false;
    }
}
