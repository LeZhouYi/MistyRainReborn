package skily_leyu.mistyrain.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MRRenderType {

    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(MRBlock.blockWoodenPot.get(), RenderType.cutout());
    }
}
