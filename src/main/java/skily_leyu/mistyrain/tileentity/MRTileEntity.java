package skily_leyu.mistyrain.tileentity;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import skily_leyu.mistyrain.MistyRain;
import skily_leyu.mistyrain.block.MRBlock;

public class MRTileEntity {
    public static final DeferredRegister<TileEntityType<?>> TILEENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MistyRain.MOD_ID);

    public static RegistryObject<TileEntityType<WoodenPotTileEntity>> woodenPotTileEntity = TILEENTITY_REGISTER.register("mr_pot_tileentity", ()->{
        return TileEntityType.Builder.of(()->{
            return new WoodenPotTileEntity();
        }, MRBlock.blockWoodenPot.get()).build(null);
    });
}
