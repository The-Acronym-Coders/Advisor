package com.teamacronymcoders.advisor.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.teamacronymcoders.advisor.api.AdvisorAPI;
import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.trigger.Trigger;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.GameProfileArgument;
import net.minecraft.server.management.PlayerList;

import java.util.Objects;

public class AdvisorCommand {
    public static LiteralArgumentBuilder<CommandSource> get() {
        return LiteralArgumentBuilder.<CommandSource>literal("advisor")
                .then(trigger())
                .then(response());
    }

    private static LiteralArgumentBuilder<CommandSource> trigger() {
        return Commands.literal("trigger")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile())
                        .suggests(allPlayers())
                        .then(Commands.argument("trigger", new ForgeRegistryArgument<>(AdvisorAPI.TRIGGERS))
                                .executes(ctx -> {
                                    Trigger trigger = ctx.getArgument("trigger", Trigger.class);
                                    GameProfileArgument.getGameProfiles(ctx, "player")
                                            .stream()
                                            .map(GameProfile::getId)
                                            .map(ctx.getSource().getWorld()::getPlayerByUuid)
                                            .filter(Objects::nonNull)
                                            .forEach(trigger::trigger);
                                    return 0;
                                })));
    }

    private static LiteralArgumentBuilder<CommandSource> response() {
        return Commands.literal("response")
                .requires(cs -> cs.hasPermissionLevel(2))
                .then(Commands.argument("player", GameProfileArgument.gameProfile())
                        .suggests(allPlayers())
                        .then(Commands.argument("response", new MapRegistryArgument<>(AdvisorAPI.RESPONSES))
                                .executes(ctx -> {
                                    Response response = ctx.getArgument("response", Response.class);
                                    GameProfileArgument.getGameProfiles(ctx, "player")
                                            .stream()
                                            .map(GameProfile::getId)
                                            .map(ctx.getSource().getWorld()::getPlayerByUuid)
                                            .filter(Objects::nonNull)
                                            .forEach(playerEntity -> response.respond(playerEntity, false));

                                    return 0;
                                })));
    }

    private static SuggestionProvider<CommandSource> allPlayers() {
        return (context, builder) -> {
            PlayerList playerlist = context.getSource().getServer().getPlayerList();
            return ISuggestionProvider.suggest(playerlist.getPlayers().stream()
                    .map((playerEntity) -> playerEntity.getGameProfile().getName()), builder);
        };
    }
}
