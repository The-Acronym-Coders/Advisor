package com.teamacronymcoders.advisor.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ForgeRegistryArgument<T extends IForgeRegistryEntry<T>> implements ArgumentType<T> {
    private final IForgeRegistry<T> forgeRegistry;
    private final List<String> examples;
    private final DynamicCommandExceptionType exceptionType = new DynamicCommandExceptionType((input) ->
            new TranslationTextComponent("adviser.argument.registry.invalid", input));

    public ForgeRegistryArgument(IForgeRegistry<T> forgeRegistry) {
        this.forgeRegistry = forgeRegistry;
        examples = createExamples(forgeRegistry);
    }


    private static <U extends IForgeRegistryEntry<U>> List<String> createExamples(IForgeRegistry<U> forgeRegistry) {
        List<String> examples = Lists.newArrayList();
        Iterator<ResourceLocation> iterator = forgeRegistry.getKeys().iterator();
        int i = 0;
        while (iterator.hasNext() & i < 5) {
            i++;
            examples.add(iterator.next().toString());
        }
        return examples;
    }

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation resourceLocation = ResourceLocation.read(reader);
        return Optional.ofNullable(forgeRegistry.getValue(resourceLocation))
                .orElseThrow(() -> exceptionType.create(resourceLocation));
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.func_212476_a(forgeRegistry.getKeys().stream(), builder);
    }

    @Override
    public Collection<String> getExamples() {
        return examples;
    }
}
