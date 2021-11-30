package xyz.brassgoggledcoders.advisor.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import xyz.brassgoggledcoders.advisor.api.cause.CauseContext;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;
import xyz.brassgoggledcoders.advisor.command.argument.CauseArgumentType;
import xyz.brassgoggledcoders.advisor.command.argument.EffectArgumentType;
import xyz.brassgoggledcoders.advisor.command.argument.EffectTableArgumentType;

import java.util.Collection;
import java.util.List;

public class AdvisorCommand {
    public static LiteralArgumentBuilder<CommandSource> create() {
        return Commands.literal("advisor")
                .then(createEffect())
                .then(createEffectTable())
                .then(createCause());
    }

    public static LiteralArgumentBuilder<CommandSource> createEffect() {
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

    public static LiteralArgumentBuilder<CommandSource> createEffectTable() {
        return Commands.literal("effecttable")
                .then(Commands.argument("targets", EntityArgument.players())
                        .then(Commands.argument("effecttable", EffectTableArgumentType.effectTable())
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                                    IEffectTable effectTable = EffectTableArgumentType.get(context, "effecttable");
                                    int effected = players.stream()
                                            .map(EffectContext::new)
                                            .map(effectContext -> {
                                                List<Effect> effects = Lists.newArrayList();
                                                effectTable.gatherEffects(effectContext, effects::add);
                                                effects.forEach(effect -> effect.perform(effectContext));
                                                return !effects.isEmpty();
                                            })
                                            .mapToInt(performed -> performed ? 1 : 0)
                                            .sum();
                                    context.getSource()
                                            .sendSuccess(new StringTextComponent("Effected " + effected + " players"), false);
                                    return players.size();
                                })
                        )
                );
    }

    public static LiteralArgumentBuilder<CommandSource> createCause() {
        return Commands.literal("cause")
                .then(Commands.argument("targets", EntityArgument.players())
                        .then(Commands.argument("cause", CauseArgumentType.cause())
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> players = EntityArgument.getPlayers(context, "targets");
                                    ICause cause = CauseArgumentType.get(context, "cause");
                                    int effected = players.stream()
                                            .map(CauseContext::new)
                                            .map(cause::perform)
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
