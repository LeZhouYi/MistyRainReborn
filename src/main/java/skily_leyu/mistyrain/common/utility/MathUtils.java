package skily_leyu.mistyrain.common.utility;

public class MathUtils {

    /**
     * true则表示var在[var1,var2]范围内
     * @param left
     * @param right
     * @param var
     * @return
     */
    public static boolean isBetween(int[] borders, int var){
        if(borders!=null&&borders.length==2){
            return var>=borders[0]&&var<=borders[1];
        }
        return false;
    }

}
