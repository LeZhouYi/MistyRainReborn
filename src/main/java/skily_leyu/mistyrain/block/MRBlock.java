package skily_leyu.mistyrain.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.block.pot.BlockWoodenPot;
import skily_leyu.mistyrain.block.potplant.BlockBreezeRattan;
import skily_leyu.mistyrain.block.potplant.BlockCandleRush;
import skily_leyu.mistyrain.block.potplant.BlockGreenWisteria;
import skily_leyu.mistyrain.block.potplant.BlockHomingPlum;
import skily_leyu.mistyrain.block.potplant.BlockNarcissus;
import skily_leyu.mistyrain.block.potplant.BlockSnowVelvet;

public class MRBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MistyRain.MOD_ID);

    //植物
    public static RegistryObject<Block> blockSnowVelvet = BLOCKS.register("mr_snow_velvet", ()->{return new BlockSnowVelvet();});
    public static RegistryObject<Block> blockGreenWisteria = BLOCKS.register("mr_green_wisteria", ()->{return new BlockGreenWisteria();});
    public static RegistryObject<Block> blockCandleRush = BLOCKS.register("mr_candle_rush", ()->{return new BlockCandleRush();});
    public static RegistryObject<Block> blockHomingPlum = BLOCKS.register("mr_homing_plum", ()->{return new BlockHomingPlum();});
    public static RegistryObject<Block> blockBreezeRattan = BLOCKS.register("mr_breeze_rattan", ()->{return new BlockBreezeRattan();});
    public static RegistryObject<Block> blockNarcissus = BLOCKS.register("mr_narcissus", ()->{return new BlockNarcissus();});

    //花盆
    public static RegistryObject<Block> blockWoodenPot = BLOCKS.register("mr_wooden_pot", ()->{return new BlockWoodenPot();});
}
