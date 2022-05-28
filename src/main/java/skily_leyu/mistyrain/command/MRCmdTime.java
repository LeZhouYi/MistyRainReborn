package skily_leyu.mistyrain.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.world.World;
import skily_leyu.mistyrain.common.core.time.MRMonth;
import skily_leyu.mistyrain.common.core.time.MRSeason;
import skily_leyu.mistyrain.common.core.time.MRSolarTerm;

public class MRCmdTime{

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        //季节设置相关
        LiteralArgumentBuilder<CommandSource> seasonSet = Commands.literal("season");
        for(MRSeason season:MRSeason.values()){
            seasonSet.then(Commands.literal(season.getName())
                .executes((commandSource)->{
                    return setSeason(commandSource.getSource().getLevel(),season);
                }));
        }

        //月份设置相关
        LiteralArgumentBuilder<CommandSource> monthSet = Commands.literal("month");
        for(MRMonth month:MRMonth.values()){
            monthSet.then(Commands.literal(month.getName()))
                .executes((commandSource)->{
                    return setMonth(commandSource.getSource().getLevel(),month);
                });
        }

        //节气设置相关
        LiteralArgumentBuilder<CommandSource> solarTermSet = Commands.literal("solarterm");
        for(MRSolarTerm solarTerm:MRSolarTerm.values()){
            solarTermSet.then(Commands.literal(solarTerm.getName()))
                .executes((commandSource)->{
                    return setSolarTerm(commandSource.getSource().getLevel(),solarTerm);
                });
        }


        dispatcher.register(
            Commands.literal("mrtime").then(
                Commands.literal("set")
                    .requires((commandSource)->{
                        return commandSource.hasPermission(2);
                    })
                    .then(seasonSet)
                    .then(monthSet)
                    .then(solarTermSet)
            )
        );
    }

    public static int setSolarTerm(World world,MRSolarTerm season){
        return 0;
    }

    public static int setMonth(World world,MRMonth season){
        return 0;
    }

    public static int setSeason(World world,MRSeason season){
        return 0;
    }

}
