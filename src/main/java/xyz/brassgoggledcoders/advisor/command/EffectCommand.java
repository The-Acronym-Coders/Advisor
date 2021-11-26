package xyz.brassgoggledcoders.advisor.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ResourceLocationArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

import java.util.Collection;

public class EffectCommand {
    public static LiteralArgumentBuilder<CommandSource> create() {
        return Commands.literal("effect")
                .then(Commands.argument("targets", EntityArgument.players())
                        .then(Commands.argument("effect", ResourceLocationArgument.id())
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                                    Effect effect = AdvisorAPI.getEffectManager()
                                            .getEffect(ResourceLocationArgument.getId(context, "effect"));
                                    if (effect == null) {
                                        context.getSource()
                                                .sendFailure(new StringTextComponent("No Effect Found"));
                                        return 0;
                                    } else {
                                        players.forEach(effect::perform);
                                        context.getSource()
                                                .sendSuccess(new StringTextComponent("Effected " + players.size() + " pLayers"), false);
                                        return players.size();
                                    }
                                })
                        )
                );
    }
}
