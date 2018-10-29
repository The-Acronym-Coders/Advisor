package com.teamacronymcoders.advisor;

import com.teamacronymcoders.advisor.speech.registry.ResponseRegistry;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.registrysystem.pieces.IRegistryPiece;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

import static com.teamacronymcoders.advisor.Advisor.*;

@Mod(modid = ID, name = NAME, version = VERSION, dependencies = DEPENDENCIES)
public class Advisor extends BaseModFoundation<Advisor> {
    public static final String ID = "advisor";
    public static final String NAME = "Advisor";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:base@[0.0.0,)";

    public Advisor() {
        super(ID, NAME, VERSION, CreativeTabs.MISC);
    }

    @Override
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void createRegistries(FMLPreInitializationEvent event, List<IRegistryPiece> registryPieces) {
        super.createRegistries(event, registryPieces);
        this.addRegistry("RESPONSE", new ResponseRegistry(this, registryPieces));
    }

    @Override
    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public Advisor getInstance() {
        return this;
    }

    @Override
    public boolean hasExternalResources() {
        return true;
    }
}
