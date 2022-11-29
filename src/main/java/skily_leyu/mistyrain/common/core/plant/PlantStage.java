package skily_leyu.mistyrain.common.core.plant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;

public class PlantStage {

    @Nonnull
    private PlantStageType nowStage;
    private Map<PlantStageType, Integer> nextStages; // PlantStageType为要转变的下个状态,Integer为此状态的权重
    private Map<PlantStageType, Map<String, Integer>> harvests; // PlantStageType为要转变的下个状态,String为获得的物品，Integer为基础数量

    public PlantStage() {
        this.nowStage = PlantStageType.NULL;
        this.nextStages = new HashMap<>();
        this.harvests = new HashMap<>();
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
     *
     * @param random
     * @return
     */
    public PlantStageType getNextStageType(Random random) {
        if (this.nextStages != null && this.nextStages.size() > 0) {
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
     *
     * @return
     */
    @Nonnull
    public Set<PlantStageType> getTransStageType() {
        Set<PlantStageType> sets = this.harvests != null ? (this.harvests.keySet()) : null;
        sets = sets == null ? (new HashSet<>()) : sets;
        return sets;
    }

    /**
     * 获取产物
     *
     * @param transType
     * @return
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
