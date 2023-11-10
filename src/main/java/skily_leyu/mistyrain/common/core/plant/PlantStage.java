package skily_leyu.mistyrain.common.core.plant;

import java.util.*;

import javax.annotation.Nonnull;

public class PlantStage {

    private PlantStageType nowStage;
    private Map<PlantStageType, Integer> nextStages; // PlantStageType为要转变的下个状态,Integer为此状态的权重
    private Map<PlantStageType, Map<String, Integer>> harvests; // PlantStageType为要转变的下个状态,String为获得的物品，Integer为基础数量

    public PlantStage() {
        this.nowStage = PlantStageType.NULL;
        this.nextStages = new EnumMap<>(PlantStageType.class);
        this.harvests = new EnumMap<>(PlantStageType.class);
    }

    public void setHarvests(Map<PlantStageType, Map<String, Integer>> harvests) {
        this.harvests = harvests;
    }

    public void setNextStages(Map<PlantStageType, Integer> nextStages) {
        this.nextStages = nextStages;
    }

    public void setNowStage(PlantStageType nowStage) {
        this.nowStage = nowStage;
    }

    public boolean isNowStage(PlantStageType stageType) {
        return nowStage == stageType;
    }

    @Nonnull
    public PlantStageType getNowStageType() {
        return this.nowStage;
    }

    /**
     * 随机获取下一个生长状态类型
     */
    public PlantStageType getNextStageType(Random random) {
        if (this.nextStages != null && !this.nextStages.isEmpty()) {
            int weight = 0;
            for (int value : this.nextStages.values()) {
                weight += value;
            }
            int next = random.nextInt(weight);
            for (Map.Entry<PlantStageType, Integer> entry : this.nextStages.entrySet()) {
                next -= entry.getValue();
                if (next <= 0) {
                    return entry.getKey();
                }
            }
        }
        return nowStage;
    }

    /**
     * 获取当前状态可以转换的状态
     */
    @Nonnull
    public Set<PlantStageType> getTransStageType() {
        Set<PlantStageType> sets = this.harvests != null ? (this.harvests.keySet()) : null;
        sets = sets == null ? (new HashSet<>()) : sets;
        return sets;
    }

    /**
     * 获取产物
     */
    @Nonnull
    public Map<String, Integer> getHarvest(PlantStageType transType) {
        Map<String, Integer> map = (this.harvests != null && this.harvests.containsKey(transType))
                ? this.harvests.get(transType)
                : null;
        map = map == null ? new HashMap<>() : map;
        return map;
    }

}
