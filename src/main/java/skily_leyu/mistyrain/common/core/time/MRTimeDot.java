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

    /**
     * 初始化
     * @param world
     */
    public MRTimeDot(World world){
        this.nowTick = world.getGameTime();
        this.allDays = nowTick/24000+1;
    }

    /**
     * 获取当前季节
     * @return
     */
    public MRSeason getSeason(){
        return nowSeason!=null?nowSeason:setSeason();
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
        return this.nowMonth!=null?this.nowMonth:setMonth();
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

}
