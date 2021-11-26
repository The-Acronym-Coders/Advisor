package xyz.brassgoggledcoders.advisor.command.argument;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;

import java.util.Collection;
import java.util.Optional;

public class EffectArgumentType extends ManagerEntryArgumentType<Effect> {


    @Override
    public Collection<String> getExamples() {
        return AdvisorAPI.getEffectManager().getExamples();
    }

    @Override
    public Collection<ResourceLocation> getIds() {
        return AdvisorAPI.getEffectManager().getIds();
    }

    @Override
    public Optional<Effect> getValue(ResourceLocation id) {
        return Optional.ofNullable(AdvisorAPI.getEffectManager().getEffect(id));
    }

    public static EffectArgumentType effect() {
        return new EffectArgumentType();
    }

    public static <S> Effect get(CommandContext<S> context, String name) {
        return context.getArgument(name, Effect.class);
    }
}
