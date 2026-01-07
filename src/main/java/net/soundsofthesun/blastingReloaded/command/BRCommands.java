package net.soundsofthesun.blastingReloaded.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.soundsofthesun.blastingReloaded.attachment.BRAttachments;
import net.soundsofthesun.blastingReloaded.attachment.BRCookTime;
import net.soundsofthesun.blastingReloaded.attachment.BRDoubling;

import static net.minecraft.server.permissions.Permissions.COMMANDS_ADMIN;

public class BRCommands {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("blasting")
                    .requires(source -> source.permissions().hasPermission(COMMANDS_ADMIN))
                    .executes(BRCommands::help)
                    .then(Commands.literal("doubling_chance")
                            .executes(BRCommands::getDoublingChance)
                            .then(Commands.argument("denominator", IntegerArgumentType.integer(1))
                                    .executes(BRCommands::setDoublingChance)))
                    .then(Commands.literal("cook_time")
                            .executes(BRCommands::getCookTime)
                            .then(Commands.argument("cook_time", IntegerArgumentType.integer(1))
                                    .executes(BRCommands::setCookTime)))
            );
        });
    }

    private static int setCookTime(CommandContext<CommandSourceStack> context) {
        context.getSource().getServer().getAllLevels().forEach(level -> level.setAttached(BRAttachments.COOK_TIME_DATA, new BRCookTime(context.getArgument("cook_time", Integer.class))));
        context.getSource().sendSuccess(() -> Component.literal("Blasting Cook Time: "+context.getSource().getLevel().getAttachedOrElse(BRAttachments.COOK_TIME_DATA, BRCookTime.DEFAULT).mult()), false);
        return 1;
    }

    private static int getCookTime(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal("Blasting Cook Time: "+context.getSource().getLevel().getAttachedOrElse(BRAttachments.COOK_TIME_DATA, BRCookTime.DEFAULT).mult()), false);
        return 1;
    }


    private static int setDoublingChance(CommandContext<CommandSourceStack> context) {
        context.getSource().getServer().getAllLevels().forEach(level -> level.setAttached(BRAttachments.DOUBLING_DATA, new BRDoubling(context.getArgument("denominator", Integer.class))));
        context.getSource().sendSuccess(() -> Component.literal("Blasting Doubling Chance: "+context.getSource().getLevel().getAttachedOrElse(BRAttachments.DOUBLING_DATA, BRDoubling.DEFAULT).denominator()), false);
        return 1;
    }

    private static int getDoublingChance(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal("Blasting Doubling Chance: "+context.getSource().getLevel().getAttachedOrElse(BRAttachments.DOUBLING_DATA, BRDoubling.DEFAULT).denominator()), false);
        return 1;
    }

    private static int help(CommandContext<CommandSourceStack> context) {
        int doubling = context.getSource().getLevel().getAttachedOrElse(BRAttachments.DOUBLING_DATA, BRDoubling.DEFAULT).denominator();
        int cookTime = context.getSource().getLevel().getAttachedOrElse(BRAttachments.COOK_TIME_DATA, BRCookTime.DEFAULT).mult();
        context.getSource().sendSuccess(() -> Component.literal("/blasting {cook_time/doubling_chance} (Integer)"), false);
        context.getSource().sendSuccess(() -> Component.literal("Cook Time: "+cookTime+". Doubling Chance: "+doubling+" (1/"+doubling+")."), false);
        return 1;
    }

}
