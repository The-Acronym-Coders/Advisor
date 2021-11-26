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
import xyz.brassgoggledcoders.advisor.command.argument.EffectArgumentType;
import xyz.brassgoggledcoders.advisor.content.AdvisorArgumentTypes;

import java.util.Collection;

public class EffectCommand {
    public static LiteralArgumentBuilder<CommandSource> create() {
        return Commands.literal("effect")
                .then(Commands.argument("targets", EntityArgument.players())
                        .then(Commands.argument("effect", EffectArgumentType.effect())
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                                    Effect effect = EffectArgumentType.get(context, "effect");
                                    players.forEach(effect::perform);
                                    context.getSource()
                                            .sendSuccess(new StringTextComponent("Effected " + players.size() + " players"), false);
                                    return players.size();
                                })
                        )
                );
    }
}
