package com.teamacronymcoders.advisor.api;

import com.teamacronymcoders.advisor.api.response.Response;
import com.teamacronymcoders.advisor.api.response.ResponseProvider;
import com.teamacronymcoders.advisor.api.runcondition.RunConditionProvider;
import com.teamacronymcoders.advisor.api.trigger.Trigger;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

import java.util.HashMap;
import java.util.Map;

public class AdvisorAPI {
    public static final Map<ResourceLocation, Response> RESPONSES = new HashMap<>();

    public static final IForgeRegistry<RunConditionProvider> RUN_CONDITION_PROVIDERS =
            RegistryManager.ACTIVE.getRegistry(RunConditionProvider.class);
    public static final IForgeRegistry<ResponseProvider> RESPONSE_PROVIDERS =
            RegistryManager.ACTIVE.getRegistry(ResponseProvider.class);
    public static final IForgeRegistry<Trigger> TRIGGERS =
            RegistryManager.ACTIVE.getRegistry(Trigger.class);
}
