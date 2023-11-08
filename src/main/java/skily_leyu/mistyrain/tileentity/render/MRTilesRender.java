package skily_leyu.mistyrain.tileentity.render;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import skily_leyu.mistyrain.tileentity.MRTiles;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MRTilesRender {

    private MRTilesRender() {
    }

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(MRTiles.woodenPotTileEntity.get(), TERWoodenPot::new);
        ClientRegistry.bindTileEntityRenderer(MRTiles.clayPotTileEntity.get(), TERClayPot::new);
    }

}
