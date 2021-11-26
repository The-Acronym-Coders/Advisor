package xyz.brassgoggledcoders.advisor.api;

import xyz.brassgoggledcoders.advisor.api.effect.IEffectManager;

public class AdvisorAPI {
    public static IEffectManager effectManager;

    public static void setEffectManager(IEffectManager effectManager) {
        AdvisorAPI.effectManager = effectManager;
    }

    public static IEffectManager getEffectManager() {
        return effectManager;
    }
}
