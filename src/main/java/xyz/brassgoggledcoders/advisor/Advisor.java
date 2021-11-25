package xyz.brassgoggledcoders.advisor;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.brassgoggledcoders.advisor.api.effect.EffectSerializer;

@Mod(Advisor.ID)
public class Advisor {
    public static final String ID = "advisor";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public Advisor() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerRegistries);
    }

    @SuppressWarnings("unchecked")
    public void registerRegistries(RegistryEvent.NewRegistry newRegistry) {
        createRegistry(rl("effect_serializer"), EffectSerializer.class);
    }

    public static <T extends ForgeRegistryEntry<T>> void createRegistry(ResourceLocation name, Class<T> tClass) {
        new RegistryBuilder<T>()
                .setName(name)
                .setType(tClass)
                .create();
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ID, path);
    }
}
