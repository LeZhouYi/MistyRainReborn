package skily_leyu.mistyrain.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import skily_leyu.mistyrain.MistyRain;

@Mod.EventBusSubscriber
public class MRCmdHandler {

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event){
        MistyRain.getLogger().info("[MistyRain]:Start registying commands");
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        MRCmdTime.register(dispatcher);
    }
}
