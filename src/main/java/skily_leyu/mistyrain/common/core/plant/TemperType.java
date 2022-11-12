package skily_leyu.mistyrain.common.core.plant;

public enum TemperType {

    FROZEN(0.15F),//严寒
    COLD(0.3F),//寒冷
    COOL(0.8F),//凉爽
    WARM(1.0F),//温暖
    HOT(1.5F),//炎热
    EXHOT(2.0F);//酷热

    private float temper; //上限温度

    private TemperType(float temper){
        this.temper = temper;
    }

    /**
     * 获取温度上限
     * @return
     */
    public float getTemper(){
        return this.temper;
    }

    /**
     * 获取当前温度对标的温度分类
     * @param temperIn
     * @return
     */
    public static TemperType getTemperType(float temperIn){
        for(TemperType type:TemperType.values()){
            if(temperIn<type.getTemper()){
                return type;
            }
        }
        return TemperType.EXHOT;
    }

}
