package skily_leyu.mistyrain.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import skily_leyu.mistyrain.command.argument.MRArgSeason;

public class MRCmdTime{

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        dispatcher.register(
            Commands.literal("mrtime").then(
                Commands.literal("set").requires((commandSource)->{
                        return commandSource.hasPermission(2);
                    }).then(
                        Commands.literal("season").then(
                            Commands.argument("season", MRArgSeason.getArgSeason()).executes((commandSource)->{
                                return setTime();
                            })
                        )
                    )
            )
        );
    }

    public static int setTime(){
        return 0;
    }

}
