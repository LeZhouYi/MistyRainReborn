package skily_leyu.mistyrain.common.core.anima;

import java.util.ArrayList;
import java.util.List;

import skily_leyu.mistyrain.config.MRConfig;

/**
 * @author Skily
 */
public class Anima {

    private AnimaType type; //灵气类型
    private AnimaLevel level; //灵气等级
    private int amount; //所拥有份数

    public Anima(AnimaType type, AnimaLevel level, int amount){
        this.type = type;
        this.level = level;
        this.amount = amount;
    }

    /**
     * 比较needs所列的灵气能否在gets中找到并满足所需求
     * @param needs
     * @param gets
     * @return
     */
    public static boolean suitAnima(List<Anima> needs, List<Anima> gets){
        if(needs!=null&&needs.size()>0&&gets!=null){
            for(Anima needAnima:needs){
                for(Anima getAnima:gets){
                    if(needAnima.isAnima(getAnima.getType())){
                        if(needAnima.compareAmount(getAnima)>0){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 比较所拥有灵气份数的多少(转成同等浓度)
     * @return
     */
    public int compareAmount(Anima anima){
        int thisAmount = (int)(Math.pow(MRConfig.PotRule.ANIMA_COMBINE_AMOUNT.get(), this.getLevel().ordinal())*this.getAmount());
        int thatAmount = (int)(Math.pow(MRConfig.PotRule.ANIMA_COMBINE_AMOUNT.get(), anima.getLevel().ordinal())*anima.getAmount());
        return thisAmount-thatAmount;
    }

    /**
     * 将列表中灵气进行规整，合并
     * @param inputs
     * @return
     */
    public static List<Anima> combineAnimas(List<Anima> inputs){
        List<Anima> result = new ArrayList<>();
        for(AnimaType type: AnimaType.values()){
            Anima teResult = new Anima(type,AnimaLevel.THIN,0);
            for(Anima teAnima:inputs){
                if(teAnima.isAnima(type)){
                    teResult.addAnima(teAnima);
                }
            }
            if(teResult.isEmpty()){
                result.add(teResult.upgrade());
            }
        }
        return result;
    }

    /**
     * 若该灵气当前浓度达到进阶要求，则浓度进阶；一般为每拥有三份灵气，则浓度提升一阶
     * @return
     */
    public Anima upgrade(){
        while(this.level!=AnimaLevel.RICH&&this.amount>=MRConfig.PotRule.ANIMA_COMBINE_AMOUNT.get()){
            this.level=AnimaLevel.values()[this.level.ordinal()+1];
            this.amount/=MRConfig.PotRule.ANIMA_COMBINE_AMOUNT.get();
        }
        return this;
    }

    /**
     * 判断灵气是否为空
     * @return
     */
    public boolean isEmpty(){
        return this.type == AnimaType.NULL || this.amount<=0;
    }

    /**
     * 添加灵气，同类型的才会进行合并；且原灵气是低浓度+高浓度，则统计为低浓度；若高浓度+低浓度，则无变化
     * @param anima
     */
    public void addAnima(Anima anima){
        if(anima!=null && this.isAnima(anima.getType())){
            int levelInx = anima.getLevel().ordinal()-this.level.ordinal();
            if(levelInx>=0){
                this.amount+=(int)(Math.pow(MRConfig.PotRule.ANIMA_COMBINE_AMOUNT.get(), levelInx)*anima.getAmount());
            }
        }
    }

    /**
     * 获取灵气份数
     * @return
     */
    public int getAmount(){
        return this.amount;
    }

    /**
     * 获取浓度等级
     * @return
     */
    public AnimaLevel getLevel(){
        return this.level;
    }

    /**
     * 获取灵气类型
     * @return
     */
    public AnimaType getType(){
        return this.type;
    }

    /**
     * 判断是否是同种类型的灵气
     * @param type
     * @return
     */
    public boolean isAnima(AnimaType type){
        return this.type == type;
    }

}
