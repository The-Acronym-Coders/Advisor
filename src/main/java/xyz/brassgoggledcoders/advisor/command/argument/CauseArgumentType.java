package xyz.brassgoggledcoders.advisor.command.argument;

import com.mojang.brigadier.context.CommandContext;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;

public class CauseArgumentType extends ManagerEntryArgumentType<ICause> {
    public CauseArgumentType() {
        super(AdvisorAPI.getCauseManager());
    }

    public static CauseArgumentType cause() {
        return new CauseArgumentType();
    }

    public static <S> ICause get(CommandContext<S> context, String name) {
        return context.getArgument(name, ICause.class);
    }
}
