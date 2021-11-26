package xyz.brassgoggledcoders.advisor;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.brassgoggledcoders.advisor.api.AdvisorAPI;
import xyz.brassgoggledcoders.advisor.content.AdvisorEffectTypes;
import xyz.brassgoggledcoders.advisor.json.EffectManager;

@Mod(Advisor.ID)
public class Advisor {
    public static final String ID = "advisor";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static EffectManager effectManager = new EffectManager();

    public Advisor() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AdvisorEffectTypes.setup(modEventBus);

        AdvisorAPI.setEffectManager(effectManager);
    }
}
