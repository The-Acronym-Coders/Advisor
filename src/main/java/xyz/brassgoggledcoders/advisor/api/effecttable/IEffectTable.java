package xyz.brassgoggledcoders.advisor.api.effecttable;

import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.manager.IManagerEntry;

import java.util.function.Consumer;

public interface IEffectTable extends IManagerEntry {
    void gatherEffects(EffectContext context, Consumer<Effect> effectConsumer);
}
