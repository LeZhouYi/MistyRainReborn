package skily_leyu.mistyrain.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import skily_leyu.mistyrain.common.MistyRain;

public class MRBlocks {
    private MRBlocks(){}

    public static final Block woodenPot = new BlockWoodenPot();
    public static final Block snowVelvet = new BlockSnowVelvet();
    public static final Block clayPot = new BlockClayPot();
    public static final Block narcissus = new BlockNarcissus();
    public static final Block homingPlum = new BlockHomingPlum();
    public static final Block greenWisteria = new BlockGreenWisteria();
    public static final Block candleRush = new BlockCandleRush();
    public static final Block breezeRattan = new BlockBreezeRattan();

    public static void registerBlocks(RegistryEvent.Register<Block> event){
        IForgeRegistry<Block> r = event.getRegistry();
        register(r,"mr_wooden_pot",woodenPot);
        register(r, "mr_snow_velvet",snowVelvet);
        register(r, "mr_clay_pot", clayPot);
        register(r, "mr_narcissus", narcissus);
        register(r,"mr_homing_plum",homingPlum);
        register(r, "mr_green_wisteria", greenWisteria);
        register(r, "mr_candle_rush", candleRush);
        register(r, "mr_breeze_rattan", breezeRattan);
    }

    public static <V extends IForgeRegistryEntry<V>> void register(IForgeRegistry<V> reg, String name, IForgeRegistryEntry<V> thing) {
        reg.register(thing.setRegistryName(new ResourceLocation(MistyRain.MOD_ID,name)));
    }

}
