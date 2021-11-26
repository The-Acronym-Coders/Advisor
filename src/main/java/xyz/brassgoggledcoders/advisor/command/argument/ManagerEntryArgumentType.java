package xyz.brassgoggledcoders.advisor.command.argument;

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

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class ManagerEntryArgumentType<T> implements ArgumentType<T> {
    public static final DynamicCommandExceptionType ERROR_UNKNOWN_VALUE = new DynamicCommandExceptionType(
            (name) -> new TranslationTextComponent("argument.advisor.id.invalid", name)
    );

    @Override
    public T parse(StringReader reader) throws CommandSyntaxException {
        int i = reader.getCursor();
        ResourceLocation resourcelocation = ResourceLocation.read(reader);
        return this.getValue(resourcelocation)
                .orElseThrow(() -> {
                    reader.setCursor(i);
                    return ERROR_UNKNOWN_VALUE.createWithContext(reader, resourcelocation.toString());
                });
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return ISuggestionProvider.suggestResource(this.getIds(), builder);
    }

    @Override
    public abstract Collection<String> getExamples();

    public abstract Collection<ResourceLocation> getIds();

    public abstract Optional<T> getValue(ResourceLocation id);
}
