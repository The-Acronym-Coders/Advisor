package xyz.brassgoggledcoders.advisor.command.argument;

import com.mojang.brigadier.context.CommandContext;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

public class EffectArgumentType extends ManagerEntryArgumentType<Effect> {

    public EffectArgumentType() {
        super(AdvisorAPI.getEffectManager());
    }

    public static EffectArgumentType effect() {
        return new EffectArgumentType();
    }

    public static <S> Effect get(CommandContext<S> context, String name) {
        return context.getArgument(name, Effect.class);
    }
}
