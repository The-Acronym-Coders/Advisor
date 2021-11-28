package xyz.brassgoggledcoders.advisor.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.brassgoggledcoders.advisor.api.cause.ICauseManager;
import xyz.brassgoggledcoders.advisor.api.effect.IEffectManager;

public class AdvisorAPI {
    public static final Logger LOGGER = LogManager.getLogger(AdvisorAPI.class);

    private static IEffectManager effectManager;
    private static ICauseManager causeManager;

    public static IEffectManager getEffectManager() {
        return effectManager;
    }

    public static void setEffectManager(IEffectManager effectManager) {
        AdvisorAPI.effectManager = effectManager;
    }

    public static ICauseManager getCauseManager() {
        return causeManager;
    }

    public static void setCauseManager(ICauseManager causeManager) {
        AdvisorAPI.causeManager = causeManager;
    }
}
