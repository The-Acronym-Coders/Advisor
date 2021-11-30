package xyz.brassgoggledcoders.advisor.command.argument;

import com.mojang.brigadier.context.CommandContext;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;

public class EffectTableArgumentType extends ManagerEntryArgumentType<IEffectTable> {
    public EffectTableArgumentType() {
        super(AdvisorAPI.getEffectTableManager());
    }

    public static EffectTableArgumentType effectTable() {
        return new EffectTableArgumentType();
    }

    public static <S> IEffectTable get(CommandContext<S> context, String name) {
        return context.getArgument(name, IEffectTable.class);
    }
}
