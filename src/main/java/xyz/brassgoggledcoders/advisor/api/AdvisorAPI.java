package xyz.brassgoggledcoders.advisor.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.brassgoggledcoders.advisor.api.cause.ICause;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effecttable.IEffectTable;
import xyz.brassgoggledcoders.advisor.api.manager.IManager;

public class AdvisorAPI {
    public static final Logger LOGGER = LogManager.getLogger(AdvisorAPI.class);

    private static IManager<Effect> effectManager;
    private static IManager<ICause> causeManager;
    private static IManager<IEffectTable> effectTableManager;

    public static IManager<Effect> getEffectManager() {
        return effectManager;
    }

    public static void setEffectManager(IManager<Effect> effectManager) {
        AdvisorAPI.effectManager = effectManager;
    }

    public static IManager<ICause> getCauseManager() {
        return causeManager;
    }

    public static void setCauseManager(IManager<ICause> causeManager) {
        AdvisorAPI.causeManager = causeManager;
    }

    public static IManager<IEffectTable> getEffectTableManager() {
        return effectTableManager;
    }

    public static void setEffectTableManager(IManager<IEffectTable> effectTableManager) {
        AdvisorAPI.effectTableManager = effectTableManager;
    }
}
