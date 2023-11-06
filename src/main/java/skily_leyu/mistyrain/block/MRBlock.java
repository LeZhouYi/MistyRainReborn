package skily_leyu.mistyrain.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.MistyRain;

public class MRBlock {
    private MRBlock(){}
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
            MistyRain.MOD_ID);
    public static final RegistryObject<Block> blockWoodenPot = BLOCKS.register("mr_wooden_pot", BlockWoodenPot::new);
    public static final RegistryObject<Block> blockSnowVelvet = BLOCKS.register("mr_snow_velvet", BlockSnowVelvet::new);
    public static final RegistryObject<Block> blockClayPot = BLOCKS.register("mr_clay_pot", BlockClayPot::new);
    public static final RegistryObject<Block> blockNarcissus = BLOCKS.register("mr_narcissus", BlockNarcissus::new);
    public static final RegistryObject<Block> blockHomingPlum = BLOCKS.register("mr_homing_plum", BlockHomingPlum::new);
    public static final RegistryObject<Block> blockGreenWisteria = BLOCKS.register("mr_green_wisteria",BlockGreenWisteria::new);
    public static final RegistryObject<Block> blockCandleRush = BLOCKS.register("mr_candle_rush",BlockCandleRush::new);
    public static final RegistryObject<Block> blockBreezeRattan = BLOCKS.register("mr_breeze_rattan",BlockBreezeRattan::new);
}
