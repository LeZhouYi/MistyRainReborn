package skily_leyu.mistyrain.common.core.time;

public class MRTimeSpan {
    private MRSolarTerm start;
    private MRSolarTerm end;

    public MRTimeSpan(MRSolarTerm start, MRSolarTerm end){
        this.start = start;
        this.end = end;
    }

    /**
     * 判断当前节气是否在范围内
     * @param now
     * @return
     */
    public boolean isInSpan(MRSolarTerm now){
        if(start.ordinal()<=end.ordinal()){
            return now.ordinal()>=start.ordinal()&&now.ordinal()<=end.ordinal();
        }
        return now.ordinal()>=start.ordinal() || now.ordinal()<=end.ordinal();
    }

}
