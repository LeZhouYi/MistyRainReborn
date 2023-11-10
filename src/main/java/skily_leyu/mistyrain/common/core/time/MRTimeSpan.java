package skily_leyu.mistyrain.common.core.time;

public class MRTimeSpan {
    private final MRSolarTerm start;
    private final MRSolarTerm end;

    public MRTimeSpan(MRSolarTerm start, MRSolarTerm end){
        this.start = start;
        this.end = end;
    }

    /**
     * 判断当前节气是否在范围内
     */
    public boolean isInSpan(MRSolarTerm now){
        if(start.ordinal()<=end.ordinal()){
            return now.ordinal()>=start.ordinal()&&now.ordinal()<=end.ordinal();
        }
        return now.ordinal()>=start.ordinal() || now.ordinal()<=end.ordinal();
    }

}
