package com.teamacronymcoders.advisor.api.runcondition;

import com.google.gson.JsonObject;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class RunConditionProvider extends ForgeRegistryEntry<RunConditionProvider> {
    public abstract IRunCondition provide(JsonObject jsonObject);
}
