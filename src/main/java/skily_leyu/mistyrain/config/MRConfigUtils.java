package skily_leyu.mistyrain.config;

import net.minecraft.world.World;
import skily_leyu.mistyrain.common.core.time.MRTimeDot;

public class MRConfigUtils {

    /**
     * 获取根据配置设置的TimeDot
     * @param world
     * @return
     */
    public static MRTimeDot getTimeDot(World world){
        return new MRTimeDot(world).update(MRConfig.MONTH_START.get(),MRConfig.DAYS_PER_MONTH.get());
    }
}
