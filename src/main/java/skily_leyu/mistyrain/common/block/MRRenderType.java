package skily_leyu.mistyrain.common.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MRRenderType {
    private MRRenderType() {
    }

    @SubscribeEvent
    public static void onRenderTypeSetup(FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(MRBlocks.woodenPot, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlocks.snowVelvet, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlocks.clayPot, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlocks.narcissus, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlocks.homingPlum, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlocks.greenWisteria, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlocks.candleRush, RenderType.cutout());
        RenderTypeLookup.setRenderLayer(MRBlocks.breezeRattan, RenderType.cutout());
    }

}
