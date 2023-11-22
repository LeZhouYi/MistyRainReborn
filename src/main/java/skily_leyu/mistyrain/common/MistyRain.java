package skily_leyu.mistyrain.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import skily_leyu.mistyrain.client.render.MRTilesRender;
import skily_leyu.mistyrain.common.block.MRBlocks;
import skily_leyu.mistyrain.common.item.MRItems;
import skily_leyu.mistyrain.common.tileentity.MRTiles;
import skily_leyu.mistyrain.data.MRConfig;
import skily_leyu.mistyrain.data.MRSetting;


@Mod(MistyRain.MOD_ID)
public class MistyRain {

    public static final String MOD_ID = "mistyrain";
    private static final Logger LOGGER = LogManager.getLogger();

    public MistyRain() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::setup);
        modBus.addGenericListener(Block.class, MRBlocks::registerBlocks);
        modBus.addGenericListener(Item.class, MRItems::registerItems);
        modBus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        MRTiles.TILEENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MRConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MRConfig.CLIENT_CONFIG);
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        MRTilesRender.onClientEvent(event);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MRSetting.load();
    }

    public static Logger getLogger() {
        return LOGGER;
    }

}
