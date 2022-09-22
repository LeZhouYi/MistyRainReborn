package skily_leyu.mistyrain.block;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.block.pot.BlockWoodenPot;
import skily_leyu.mistyrain.block.potplant.BlockSnowVelvet;

public class MRBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MistyRain.MOD_ID);

    public static RegistryObject<Block> blockSnowVelvet = BLOCKS.register("mr_snow_velvet", ()->{
        return new BlockSnowVelvet();
    });

    public static RegistryObject<Block> blockWoodenPot = BLOCKS.register("mr_wooden_pot", ()->{
        return new BlockWoodenPot();
    });
}
