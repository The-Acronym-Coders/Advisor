package xyz.brassgoggledcoders.advisor.effecttable;

import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootPredicateManager;
import xyz.brassgoggledcoders.advisor.Advisor;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;
import xyz.brassgoggledcoders.advisor.codec.CodecReloadListener;

public class EffectTableManager extends CodecReloadListener<IEffectTable, EffectTable> {
    private final LootPredicateManager predicateManager;

    public EffectTableManager(LootPredicateManager predicateManager) {
        super(EffectTableCodecs.TABLE, "advisor/effecttable");
        this.predicateManager = predicateManager;
    }

    @Override
    protected void validate() {
        EffectValidationTracker validationTracker = new EffectValidationTracker(
                LootParameterSets.ALL_PARAMS,
                this.predicateManager::get
        );

        for (EffectTable table : this.getValues()) {
            table.validate(validationTracker.enterTable("{" + table.getId() + "}", table.getParameterSet()));
        }

        validationTracker.getProblems().forEach(
                (context, problem) -> Advisor.LOGGER.warn("Found validation problem in " + context + ": " + problem)
        );
    }
}
