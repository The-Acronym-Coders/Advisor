package xyz.brassgoggledcoders.advisor.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.command.argument.EffectArgumentType;

import java.util.Collection;

public class EffectCommand {
    public static LiteralArgumentBuilder<CommandSource> create() {
        return Commands.literal("effect")
                .then(Commands.argument("targets", EntityArgument.players())
                        .then(Commands.argument("effect", EffectArgumentType.effect())
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                                    Effect effect = EffectArgumentType.get(context, "effect");
                                    int effected = players.stream()
                                            .map(EffectContext::new)
                                            .map(effect::perform)
                                            .mapToInt(performed -> performed ? 1 : 0)
                                            .sum();
                                    context.getSource()
                                            .sendSuccess(new StringTextComponent("Effected " + effected + " players"), false);
                                    return players.size();
                                })
                        )
                );
    }
}
