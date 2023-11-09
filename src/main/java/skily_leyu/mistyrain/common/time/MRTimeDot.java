package skily_leyu.mistyrain.common.time;

import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

/**
 * @author Skily_leyu
 * 记录当前时间点并计算季节，节气，月分，天数等
 */
public class MRTimeDot {
    private int daysPerMonth=30; //Config:设置一个月多少天
    private int monthStart=2; //Config:设置游戏开始的初始月份，取值0-11

    private long nowTick; //记录当前时间点
    private MRSeason nowSeason; //记录当前季节
    private MRMonth nowMonth; //记录当前月份
    private MRSolarTerm nowSolarTerm; //记录当前节气
    private long allDays; //记录总天数

    private boolean needUpdate; //记录是否需要更新

    /**
     * 初始化
     */
    public MRTimeDot(World world){
        this.nowTick = world.getDayTime();
        this.allDays = setAllDays();
        this.needUpdate = true;
    }

    /**
     * 更新
     */
    public MRTimeDot update(int monthStart, int daysPerMonth){
        this.daysPerMonth = daysPerMonth;
        this.monthStart = monthStart;
        return this;
    }

    /**
     * 更新
     */
    public MRTimeDot update(World world){
        this.nowTick = world.getDayTime();
        long teAllDays = setAllDays();
        if(this.allDays!=teAllDays){
            this.allDays = teAllDays;
            needUpdate = true;
        }else{
            needUpdate = false;
        }
        return this;
    }

    /**
     * 获取当前季节
     */
    public MRSeason getSeason(){
        return nowSeason!=null&&!needUpdate?nowSeason:setSeason();
    }

    /**
     * 更新季节
     */
    private MRSeason setSeason() {
        //设一月为0，冬季为3，3=((0+10)%12)/3
        //设二月为1，冬季为3，3=((1+10)%12)/3
        this.nowSeason = MRSeason.values()[((getMonth().ordinal()+10)%12)/3];
        return this.nowSeason;
    }

    /**
     * 获取当前月份
     */
    public MRMonth getMonth(){
        return this.nowMonth!=null&&!needUpdate?this.nowMonth:setMonth();
    }

    /**
     * 更新月份
     */
    private MRMonth setMonth() {
        int months = (int)(getAllDays()/daysPerMonth);
        this.nowMonth = MRMonth.values()[(months+monthStart)%12];
        return this.nowMonth;
    }

    /**
     * 获取总天数
     */
    public long getAllDays(){
        return this.allDays;
    }

    /**
     * 计算总天
     */
    private long setAllDays(){
        return this.nowTick/24000+1;
    }

    /**
     * 返回当前为当月的第几天
     */
    public int getMonthDay(){
        //设all_days=1,每月=30天，首月的第一天=(1-1)%30+1=第一天
        return (int)((getAllDays()-1)%daysPerMonth)+1;
    }

    /**
     * 获取当前节气
     */
    public MRSolarTerm getSolarTerm(){
        return this.nowSolarTerm!=null&&!needUpdate?this.nowSolarTerm:setSolarTerm();
    }

    /**
     * 返回时间的整体信息
     */
    public String getTimeInfo(){
        String season = getSeason().getI18nString();
        String month = getMonth().getI18nString();
        String day = getDayString(getMonthDay());
        String solarTerm = getSolarTerm().getI18nString();
        return I18n.get("mr_time_info", season,month,day,solarTerm);
    }

    /**
     * 返回天数的本地化字串
     */
    public static String getDayString(int day){
        if(day>0&&day<=10){
            return getDayBase(day);
        }else if(day>10&&day<20){
            return String.format("%s%s", getDayBase(10),getDayBase(day-10));
        }else{
            return String.format("%s%s%s", getDayBase(day/10),getDayBase(10),getDayBase(day%10));
        }
    }

    /**
     * 返回基础的天数
     */
    public static String getDayBase(int day){
        return I18n.get(String.format("day.%d", day));
    }

    /**
     * 更新节气
     */
    private MRSolarTerm setSolarTerm(){
        //设首月为2月，设天数为30天中的第4天，则节气=(22+1*2+0)%24=0
        //设首月为1月，设天数为30天中的第29天，则节气=(22+0*2+2)%24=0
        int monthIndex = getMonth().ordinal();
        float dayPercent = getMonthDay()/(float)daysPerMonth;
        int extractIndex = countExtractIndex(dayPercent); //偏移值，用于计算对应节气的下标
        this.nowSolarTerm = MRSolarTerm.values()[(22+monthIndex*2+extractIndex)%24];
        return this.nowSolarTerm;
    }

    public int countExtractIndex(float dayPercent){
        if(dayPercent<5/30F){
            return 0;
        }
        return dayPercent<20F/30F?1:2;
    }

    /**
     * 获取当前节气与目标节气相差的天数
     */
    public int diffDays(MRSolarTerm solarTerm) {
        //节气所占天数=daysPerMonth/2
        //设当前节气为3，设置节气为4，diff=(4-3+24)%24=1
        //设当前节气为5，设置节气为4，diff=(4-5+24)%24=23
        int nowSolarDays = ((getMonthDay()+(int)(daysPerMonth/2D-daysPerMonth*5D/30D))-1)%(daysPerMonth/2)+1;
        int diffSolarIndex = (solarTerm.ordinal()-getSolarTerm().ordinal()+24)%24;
        return (diffSolarIndex!=0)?(daysPerMonth/2*diffSolarIndex-nowSolarDays+1):0;
    }

    /**
     * 获取当前月份与目标月份相差的天数
     */
    public int diffDays(MRMonth month) {
        //设当前月份为2月，目标月份为3月,当月23天，总月共30, diff=(3-2)*30-23=7
        //设当前月份为2月，目标月份为2月,当月23天，总月共30, diff=0
        //设当前月份为2月，目标月份为5月,当月23天，总月共30, diff=(5-2)*30-23=77
        int nowMonthDays = getMonthDay();
        int diffMonthIndex = (month.ordinal()-getMonth().ordinal()+12)%12;
        return (diffMonthIndex!=0)?(daysPerMonth*diffMonthIndex-nowMonthDays+1):0;
    }

    /**
     * 获取当前季节与目标季节相差的天数
     */
    public int diffDays(MRSeason season) {
        int nowSeasonDays = (int)((getAllDays()-1)%(daysPerMonth*3)+1);
        int diffSeasonIndex = (season.ordinal()-getSeason().ordinal()+4)%4;
        return (diffSeasonIndex!=0)?(daysPerMonth*diffSeasonIndex*3-nowSeasonDays+1):0;
    }

}
