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

    public static final Anima EMPTY = new Anima(AnimaType.NULL, AnimaLevel.NULL, 0);

    public Anima(AnimaType type, AnimaLevel level, int amount){
        this.type = type;
        this.level = level;
        this.amount = amount;
    }

    /**
     * 获取当前灵气相生的灵气类型
     * @return
     */
    public AnimaType getGenType(){
        return this.type.getGenType();
    }

    /**
     * 获取当前灵气相消的灵气类型
     * @return
     */
    public AnimaType getDecayType(){
        return this.type.getDecayType();
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
     * 比较所拥有灵气份数的多少(转成同等浓度)，<0则小于当前浓度，>0则大于当前浓度
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
        //浓度合并进阶
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
        //灵气相生进阶
        for(int i = 0;i<result.size();i++){
            Anima teAnima = result.get(i);
            Anima checkAnima = extractAnima(result, teAnima.getGenType());
            if(!checkAnima.isEmpty()){
                if(teAnima.getLevel().compareTo(checkAnima.getLevel())<0){
                    result.get(i).upLevel(1);
                }
            }
        }
        //灵气相消阶段
        for(int i = 0;i<result.size();i++){
            Anima teAnima = result.get(i);
            Anima checkAnima = extractAnima(result, teAnima.getDecayType());
            if(!checkAnima.isEmpty()){
                if(teAnima.getLevel().compareTo(checkAnima.getLevel())<0){
                    result.get(i).upLevel(-1);
                }
            }
        }
        return result;
    }

    /**
     * 将当前灵气浓度升up级(up>0)或降up级(up<0)，而不改变灵气份数
     * @param up
     */
    public void upLevel(int up){
        int size = AnimaLevel.values().length;
        int resultLevel = up+this.level.ordinal();
        resultLevel = resultLevel<0?0:((resultLevel>=size)?(size-1):resultLevel);
        if(this.level.ordinal()==0&&up<0){
            this.type = AnimaType.NULL;
        }
        this.level = AnimaLevel.values()[resultLevel];
    }

    /**
     * 获取灵气集中对应类型的灵气
     * @param inputs
     * @param type
     * @return
     */
    public static Anima extractAnima(List<Anima> inputs,AnimaType type){
        if(inputs!=null&&type!=null){
            for(Anima anima:inputs){
                if(anima.isAnima(type)){
                    return anima;
                }
            }
        }
        return Anima.EMPTY;
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

    @Override
    public String toString() {
        return String.format("type:%s,level:%s,amount:%d", this.type,this.level,this.amount);
    }

}
