package skily_leyu.mistyrain.common.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.common.MistyRain;
import skily_leyu.mistyrain.common.block.MRBlocks;

public class MRTiles {
    public static final DeferredRegister<TileEntityType<?>> TILEENTITY_REGISTER = DeferredRegister
            .create(ForgeRegistries.TILE_ENTITIES, MistyRain.MOD_ID);
    public static RegistryObject<TileEntityType<TileWoodenPot>> woodenPotTileEntity = TILEENTITY_REGISTER
            .register("mr_wooden_pot_tileentity", () -> TileEntityType.Builder.of(
                    TileWoodenPot::new, MRBlocks.woodenPot
            ).build(null));
    public static RegistryObject<TileEntityType<TileClayPot>> clayPotTileEntity = TILEENTITY_REGISTER
            .register("mr_clay_pot_tileentity",()-> TileEntityType.Builder.of(
                    TileClayPot::new, MRBlocks.clayPot
            ).build(null));

}
