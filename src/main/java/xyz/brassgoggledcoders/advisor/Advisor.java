package xyz.brassgoggledcoders.advisor;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.brassgoggledcoders.advisor.content.AdvisorArgumentTypes;
import xyz.brassgoggledcoders.advisor.content.AdvisorEffectTypes;

@Mod(Advisor.ID)
public class Advisor {
    public static final String ID = "advisor";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public Advisor() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AdvisorEffectTypes.setup(modEventBus);
        AdvisorArgumentTypes.setup();
    }
}
