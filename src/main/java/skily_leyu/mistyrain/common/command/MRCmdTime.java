package skily_leyu.mistyrain.common.command;

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
import skily_leyu.mistyrain.data.MRConfig;

public class MRCmdTime {

    private MRCmdTime() {
    }

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        //季节设置相关
        LiteralArgumentBuilder<CommandSource> seasonSet = Commands.literal("season");
        for (MRSeason season : MRSeason.values()) {
            seasonSet.then(Commands.literal(season.getName())
                    .executes(commandSource -> {
                        ServerWorld world = commandSource.getSource().getLevel();
                        setSeason(world, season);
                        return showTimeInfo(commandSource.getSource(), world);
                    }));
        }

        //月份设置相关
        LiteralArgumentBuilder<CommandSource> monthSet = Commands.literal("month");
        for (MRMonth month : MRMonth.values()) {
            monthSet.then(Commands.literal(month.getName())
                    .executes(commandSource -> {
                        ServerWorld world = commandSource.getSource().getLevel();
                        setMonth(world, month);
                        return showTimeInfo(commandSource.getSource(), world);
                    }));
        }

        //节气设置相关
        LiteralArgumentBuilder<CommandSource> solarTermSet = Commands.literal("solar_term");
        for (MRSolarTerm solarTerm : MRSolarTerm.values()) {
            solarTermSet.then(Commands.literal(solarTerm.getName())
                    .executes(commandSource -> {
                        ServerWorld world = commandSource.getSource().getLevel();
                        setSolarTerm(world, solarTerm);
                        return showTimeInfo(commandSource.getSource(), world);
                    }));
        }


        dispatcher.register(
                Commands.literal("mr_time")
                        .then(
                                Commands.literal("set")
                                        .requires(commandSource ->
                                                commandSource.hasPermission(2))
                                        .then(seasonSet)
                                        .then(monthSet)
                                        .then(solarTermSet)
                        )
                        .then(
                                Commands.literal("info")
                                        .requires(commandSource -> commandSource.hasPermission(0))
                                        .executes(commandSource -> showTimeInfo(commandSource.getSource(), commandSource.getSource().getLevel()))
                        )
                        .then(
                                Commands.literal("next")
                                        .requires(commandSource -> commandSource.hasPermission(2))
                                        .then(
                                                Commands.literal("month").executes(commandSource -> {
                                                    ServerWorld world = commandSource.getSource().getLevel();
                                                    MRTimeDot timeDot = new MRTimeDot(world);
                                                    setMonth(world, timeDot.getMonth().getNext());
                                                    return showTimeInfo(commandSource.getSource(), world);
                                                })
                                        )
                                        .then(
                                                Commands.literal("season").executes(commandSource -> {
                                                    ServerWorld world = commandSource.getSource().getLevel();
                                                    MRTimeDot timeDot = new MRTimeDot(world);
                                                    setSeason(world, timeDot.getSeason().getNext());
                                                    return showTimeInfo(commandSource.getSource(), world);
                                                })
                                        )
                                        .then(
                                                Commands.literal("solar_term").executes(commandSource -> {
                                                    ServerWorld world = commandSource.getSource().getLevel();
                                                    MRTimeDot timeDot = new MRTimeDot(world);
                                                    setSolarTerm(world, timeDot.getSolarTerm().getNext());
                                                    return showTimeInfo(commandSource.getSource(), world);
                                                })
                                        )
                                        .then(
                                                Commands.literal("day").executes((commandSource -> {
                                                    ServerWorld world = commandSource.getSource().getLevel();
                                                    world.setDayTime(world.getDayTime() + 24000);
                                                    return showTimeInfo(commandSource.getSource(), world);
                                                }))
                                        )
                        )
        );
    }

    /**
     * 显示当前时间信息
     */
    public static int showTimeInfo(CommandSource commandSource, ServerWorld world) {
        MRTimeDot timeDot = MRConfig.TimeRule.getTimeDot(world);
        commandSource.sendSuccess(new StringTextComponent(timeDot.getTimeInfo()), true);
        return 0;
    }

    /**
     * 设置节气
     */
    public static void setSolarTerm(ServerWorld world, MRSolarTerm solarTerm) {
        MRTimeDot timeDot = MRConfig.TimeRule.getTimeDot(world);
        int diffDays = timeDot.update(world).diffDays(solarTerm);
        if (diffDays != 0) {
            world.setDayTime(world.getDayTime() + diffDays * 24000L);
        }
    }

    /**
     * 设置月份
     */
    public static void setMonth(ServerWorld world, MRMonth month) {
        MRTimeDot timeDot = MRConfig.TimeRule.getTimeDot(world);
        int diffDays = timeDot.update(world).diffDays(month);
        if (diffDays != 0) {
            world.setDayTime(world.getDayTime() + diffDays * 24000L);
        }
    }

    /**
     * 设置季节
     */
    public static void setSeason(ServerWorld world, MRSeason season) {
        MRTimeDot timeDot = MRConfig.TimeRule.getTimeDot(world);
        int diffDays = timeDot.update(world).diffDays(season);
        if (diffDays != 0) {
            world.setDayTime(world.getDayTime() + diffDays * 24000L);
        }
    }

}
