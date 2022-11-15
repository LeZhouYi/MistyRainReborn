package skily_leyu.mistyrain;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import skily_leyu.mistyrain.block.MRBlock;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRSetting;
import skily_leyu.mistyrain.item.MRItem;
import skily_leyu.mistyrain.tileentity.MRTileEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(MistyRain.MOD_ID)
public class MistyRain {

    public static final String MOD_ID = "mistyrain";
    private static final Logger LOGGER = LogManager.getLogger();

    public MistyRain() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
        MRBlock.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MRItem.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        MRTileEntity.TILEENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MRConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MRConfig.CLIENT_CONFIG);
    }

    private void setup(final FMLCommonSetupEvent event){
        new MRSetting(event);
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event){
    }

    private void processIMC(final InterModProcessEvent event){
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public static Logger getLogger(){
        return LOGGER;
    }

}
