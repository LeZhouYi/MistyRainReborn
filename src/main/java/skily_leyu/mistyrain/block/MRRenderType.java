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
        RenderTypeLookup.setRenderLayer(MRBlock.blockSnowVelvet.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlock.blockGreenWisteria.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlock.blockCandleRush.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlock.blockHomingPlum.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlock.blockBreezeRattan.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlock.blockNarcissus.get(), RenderType.cutout());

        RenderTypeLookup.setRenderLayer(MRBlock.blockWoodenPot.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlock.blockClayPot.get(), RenderType.cutout());
    }
}
