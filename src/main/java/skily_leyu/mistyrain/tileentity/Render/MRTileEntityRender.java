package skily_leyu.mistyrain.tileentity.Render;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import skily_leyu.mistyrain.tileentity.MRTileEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MRTileEntityRender {

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
//        ClientRegistry.bindTileEntityRenderer(MRTileEntity.woodenPotTileEntity.get(), (tileEntityRendererDispatcher -> {
//            return new WoodenPotTER(tileEntityRendererDispatcher);
//        }));
//        ClientRegistry.bindTileEntityRenderer(MRTileEntity.clayPotTileEntity.get(), (tileEntityRendererDispatcher -> {
//            return new ClayPotTER(tileEntityRendererDispatcher);
//        }));

    }

}
