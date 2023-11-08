package skily_leyu.mistyrain;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import skily_leyu.mistyrain.block.MRBlocks;
import skily_leyu.mistyrain.config.MRConfig;
import skily_leyu.mistyrain.config.MRSetting;
import skily_leyu.mistyrain.item.MRItems;
import skily_leyu.mistyrain.tileentity.MRTiles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(MistyRain.MOD_ID)
public class MistyRain {

    public static final String MOD_ID = "mistyrain";
    private static final Logger LOGGER = LogManager.getLogger();

    public MistyRain() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::setup);
        modBus.addGenericListener(Block.class,MRBlocks::registerBlocks);
        modBus.addGenericListener(Item.class,MRItems::registerItems);

        MinecraftForge.EVENT_BUS.register(this);
        MRTiles.TILEENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MRConfig.COMMON_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MRConfig.CLIENT_CONFIG);
    }

    private void setup(final FMLCommonSetupEvent event){
        MRSetting.load();
    }

    public static Logger getLogger(){
        return LOGGER;
    }

}
