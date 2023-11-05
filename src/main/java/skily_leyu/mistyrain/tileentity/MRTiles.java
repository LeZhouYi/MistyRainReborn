package skily_leyu.mistyrain.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.block.MRBlock;

public class MRTiles {
    public static final DeferredRegister<TileEntityType<?>> TILEENTITY_REGISTER = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, MistyRain.MOD_ID);
    public static RegistryObject<TileEntityType<TileWoodenPot>> woodenPotTileEntity = TILEENTITY_REGISTER
            .register("mr_wooden_pot_tileentity", () -> TileEntityType.Builder.of(
                    TileWoodenPot::new, MRBlock.blockWoodenPot.get()
            ).build(null));

}
