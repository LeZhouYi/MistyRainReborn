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
                    ServerWorld world = commandSource.getSource().getLevel();
                    setSeason(world,season);
                    return showTimeInfo(commandSource.getSource(),world);
                }));
        }

        //月份设置相关
        LiteralArgumentBuilder<CommandSource> monthSet = Commands.literal("month");
        for(MRMonth month:MRMonth.values()){
            monthSet.then(Commands.literal(month.getName())
                .executes((commandSource)->{
                    ServerWorld world = commandSource.getSource().getLevel();
                    setMonth(world,month);
                    return showTimeInfo(commandSource.getSource(),world);
                }));
        }

        //节气设置相关
        LiteralArgumentBuilder<CommandSource> solarTermSet = Commands.literal("solarterm");
        for(MRSolarTerm solarTerm:MRSolarTerm.values()){
            solarTermSet.then(Commands.literal(solarTerm.getName())
                .executes((commandSource)->{
                    ServerWorld world = commandSource.getSource().getLevel();
                    setSolarTerm(world,solarTerm);
                    return showTimeInfo(commandSource.getSource(),world);
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
                .then(
                    Commands.literal("next")
                        .requires((commandSource)->{
                            return commandSource.hasPermission(2);
                        })
                        .then(
                            Commands.literal("month").executes((commandSource)->{
                                ServerWorld world = commandSource.getSource().getLevel();
                                MRTimeDot timeDot = new MRTimeDot(world);
                                setMonth(world, timeDot.getMonth().getNext());
                                return showTimeInfo(commandSource.getSource(),world);
                            })
                        )
                        .then(
                            Commands.literal("season").executes((commandSource)->{
                                ServerWorld world = commandSource.getSource().getLevel();
                                MRTimeDot timeDot = new MRTimeDot(world);
                                setSeason(world, timeDot.getSeason().getNext());
                                return showTimeInfo(commandSource.getSource(),world);
                            })
                        )
                        .then(
                            Commands.literal("solarterm").executes((commandSource)->{
                                ServerWorld world = commandSource.getSource().getLevel();
                                MRTimeDot timeDot = new MRTimeDot(world);
                                setSolarTerm(world,timeDot.getSolarTerm().getNext());
                                return showTimeInfo(commandSource.getSource(),world);
                            })
                        )
                        .then(
                            Commands.literal("day").executes((commandSource->{
                                ServerWorld world = commandSource.getSource().getLevel();
                                world.setDayTime(world.getDayTime()+24000);
                                return showTimeInfo(commandSource.getSource(),world);
                            }))
                        )
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
        if(diffdays!=0){
            world.setDayTime(world.getDayTime()+diffdays*24000);
        }
        return 0;
    }

    /**
     * 设置季节
     * @param world
     * @param season
     * @return
     */
    public static int setSeason(ServerWorld world,MRSeason season){
        MRTimeDot timeDot = MRConfigUtils.getTimeDot(world);
        int diffdays = timeDot.update(world).diffDays(season);
        if(diffdays!=0){
            world.setDayTime(world.getDayTime()+diffdays*24000);
        }
        return 0;
    }

}
