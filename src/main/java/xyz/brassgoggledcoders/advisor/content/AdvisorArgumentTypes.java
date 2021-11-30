package xyz.brassgoggledcoders.advisor.content;

import net.minecraft.command.arguments.ArgumentSerializer;
import net.minecraft.command.arguments.ArgumentTypes;
import xyz.brassgoggledcoders.advisor.command.argument.EffectArgumentType;
import xyz.brassgoggledcoders.advisor.command.argument.EffectTableArgumentType;

public class AdvisorArgumentTypes {
    public static void setup() {
        ArgumentTypes.register("advisor:effect", EffectArgumentType.class, new ArgumentSerializer<>(EffectArgumentType::new));
        ArgumentTypes.register("advisor:effect_table", EffectTableArgumentType.class, new ArgumentSerializer<>(EffectTableArgumentType::new));
    }
}
