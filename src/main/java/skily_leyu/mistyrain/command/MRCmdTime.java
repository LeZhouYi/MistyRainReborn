package skily_leyu.mistyrain.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import skily_leyu.mistyrain.common.core.time.MRMonth;
import skily_leyu.mistyrain.common.core.time.MRSeason;
import skily_leyu.mistyrain.common.core.time.MRSolarTerm;
import skily_leyu.mistyrain.common.core.time.MRTimeDot;
import skily_leyu.mistyrain.config.MRConfigUtils;

public class MRCmdTime{

    public static void register(CommandDispatcher<CommandSource> dispatcher){
        //季节设置相关
        LiteralArgumentBuilder<CommandSource> seasonSet = Commands.literal("season");
        for(MRSeason season:MRSeason.values()){
            seasonSet.then(Commands.literal(season.getName())
                .executes((commandSource)->{
                    setSeason(commandSource.getSource().getLevel(),season);
                    return showTimeInfo(commandSource.getSource(),commandSource.getSource().getLevel());
                }));
        }

        //月份设置相关
        LiteralArgumentBuilder<CommandSource> monthSet = Commands.literal("month");
        for(MRMonth month:MRMonth.values()){
            monthSet.then(Commands.literal(month.getName())
                .executes((commandSource)->{
                    setMonth(commandSource.getSource().getLevel(),month);
                    return showTimeInfo(commandSource.getSource(),commandSource.getSource().getLevel());
                }));
        }

        //节气设置相关
        LiteralArgumentBuilder<CommandSource> solarTermSet = Commands.literal("solarterm");
        for(MRSolarTerm solarTerm:MRSolarTerm.values()){
            solarTermSet.then(Commands.literal(solarTerm.getName())
                .executes((commandSource)->{
                    setSolarTerm(commandSource.getSource().getLevel(),solarTerm);
                    return showTimeInfo(commandSource.getSource(),commandSource.getSource().getLevel());
                }));
        }


        dispatcher.register(
            Commands.literal("mrtime")
                .then(
                    Commands.literal("set")
                        .requires((commandSource)->{
                            return commandSource.hasPermission(2);
                        })
                        .then(seasonSet)
                        .then(monthSet)
                        .then(solarTermSet)
                )
                .then(
                    Commands.literal("info")
                        .requires((commandSource)->{
                            return commandSource.hasPermission(0);
                        })
                        .executes((commandSource)->{
                            return showTimeInfo(commandSource.getSource(),commandSource.getSource().getLevel());
                        })
                )
        );
    }

    /**
     * 显示当前时间信息
     * @param commandSource
     * @param world
     * @return
     */
    public static int showTimeInfo(CommandSource commandSource,ServerWorld world){
        MRTimeDot timeDot = MRConfigUtils.getTimeDot(world);
        commandSource.sendSuccess(new StringTextComponent(timeDot.getTimeInfo()), true);
        return 0;
    }

    /**
     * 设置节气
     * @param world
     * @param solarTerm
     * @return
     */
    public static int setSolarTerm(ServerWorld world,MRSolarTerm solarTerm){
        MRTimeDot timeDot = MRConfigUtils.getTimeDot(world);
        int diffdays = timeDot.update(world).diffDays(solarTerm);
        System.out.println(("didffffdays"+diffdays));
        if(diffdays!=0){
            world.setDayTime(world.getDayTime()+diffdays*24000);
        }
        return 0;
    }

    /**
     * 设置月份
     * @param world
     * @param month
     * @return
     */
    public static int setMonth(ServerWorld world,MRMonth month){
        MRTimeDot timeDot = MRConfigUtils.getTimeDot(world);
        int diffdays = timeDot.update(world).diffDays(month);
        System.out.println(("didffffdays"+diffdays));
        if(diffdays!=0){
            world.setDayTime(world.getDayTime()+diffdays*24000);
        }
        return 0;
    }

    /**
     * 设置节气
     * @param world
     * @param season
     * @return
     */
    public static int setSeason(ServerWorld world,MRSeason season){
        MRTimeDot timeDot = MRConfigUtils.getTimeDot(world);
        int diffdays = timeDot.update(world).diffDays(season);
        System.out.println(("didffffdays"+diffdays));
        if(diffdays!=0){
            world.setDayTime(world.getDayTime()+diffdays*24000);
        }
        return 0;
    }

}
