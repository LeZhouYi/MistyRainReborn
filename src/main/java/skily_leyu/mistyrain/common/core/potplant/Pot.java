package skily_leyu.mistyrain.common.core.potplant;

import java.util.List;

public class Pot {

    private String name; //花盆名
    private int slotSize; //对应泥土可放格子数，可种植物数量
    private int tankSize; //储水量
    private List<String> suitSoils; //适合的土壤
    private SoilType suitSoilType; //适合的土壤类型
    private List<String> suitFluids; //适合的水份
    private int fertilizer; //肥料值

}
