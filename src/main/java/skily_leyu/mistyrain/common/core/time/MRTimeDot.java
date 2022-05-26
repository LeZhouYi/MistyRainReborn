package skily_leyu.mistyrain.common.core.time;

import net.minecraft.world.World;

/**
 * @author Skily_leyu
 * 记录当前时间点并计算季节，节气，月分，天数等
 */
public class MRTimeDot {
    private int daysPerMonth; //Config:设置一个月多少天
    private int monthStart; //Config:设置游戏开始的初始月份，取值0-11

    private long nowTick; //记录当前时间点
    private MRSeason nowSeason; //记录当前季节
    private MRMonth nowMonth; //记录当前月份
    private MRSolarTerm nowSolarTerm; //记录当前节气
    private long allDays; //记录总天数

    private boolean needUpdate; //记录是否需要更新

    /**
     * 初始化
     * @param world
     */
    public MRTimeDot(World world){
        this.nowTick = world.getGameTime();
        this.allDays = setAllDays();
        this.needUpdate = true;
    }

    /**
     * 更新
     * @param world
     * @return
     */
    public MRTimeDot update(World world){
        this.nowTick = world.getGameTime();
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
     * @return
     */
    public MRSeason getSeason(){
        return nowSeason!=null&&needUpdate?nowSeason:setSeason();
    }

    /**
     * 更新季节
     * @return
     */
    private MRSeason setSeason() {
        //设一月为0，冬季为3，3=((0+10)%12)/3
        //设二月为1，冬季为3，3=((1+10)%12)/3
        this.nowSeason = MRSeason.values()[((getMonth().ordinal()+10)%12)/3];
        return this.nowSeason;
    }

    /**
     * 获取当前月份
     * @return
     */
    public MRMonth getMonth(){
        return this.nowMonth!=null&&needUpdate?this.nowMonth:setMonth();
    }

    /**
     * 更新月份
     * @return
     */
    private MRMonth setMonth() {
        int months = (int)(getAllDays()/daysPerMonth);
        this.nowMonth = MRMonth.values()[(months+monthStart)%12];
        return this.nowMonth;
    }

    /**
     * 获取总天数
     * @return
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
     * @return
     */
    public int getMonthDay(){
        //设alldays=1,每月=30天，首月的第一天=(1-1)%30+1=第一天
        return (int)((getAllDays()-1)%daysPerMonth)+1;
    }

    /**
     * 获取当前节气
     * @return
     */
    public MRSolarTerm getSolarTerm(){
        return this.nowSolarTerm!=null&&needUpdate?this.nowSolarTerm:setSolarTerm();
    }

    /**
     * 更新节气
     * @return
     */
    private MRSolarTerm setSolarTerm(){
        //设首月为2月，设天数为30天中的第4天，则节气=(22+1*2+0)%24=0
        //设首月为1月，设天数为30天中的第29天，则节气=(22+0*2+2)%24=0
        int monthIndex = getMonth().ordinal();
        float dayPercent = getMonthDay()/(float)daysPerMonth;
        int extractIndex = dayPercent<5/30F?0:(dayPercent<20F/30F)?1:2; //偏移值，用于计算对应节气的下标
        this.nowSolarTerm = MRSolarTerm.values()[(22+monthIndex*2+extractIndex)%24];
        return this.nowSolarTerm;
    }

}
