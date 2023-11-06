package skily_leyu.mistyrain.tileentity.Render;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import skily_leyu.mistyrain.tileentity.MRTiles;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MRTileEntityRender {

    private MRTileEntityRender() {
    }

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        ClientRegistry.bindTileEntityRenderer(MRTiles.woodenPotTileEntity.get(), WoodenPotTER::new);
        ClientRegistry.bindTileEntityRenderer(MRTiles.clayPotTileEntity.get(), ClayPotTER::new);
    }

}
