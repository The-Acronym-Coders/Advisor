package com.teamacronymcoders.advisor;

import com.teamacronymcoders.advisor.api.AdvisorRegistries;
import com.teamacronymcoders.advisor.api.speech.IResponse;
import com.teamacronymcoders.advisor.command.RespondCommand;
import com.teamacronymcoders.advisor.json.JsonLoader;
import com.teamacronymcoders.advisor.json.ResponseDeserializer;
import com.teamacronymcoders.advisor.network.ResponseHandler;
import com.teamacronymcoders.advisor.network.ResponsePacket;
import com.teamacronymcoders.advisor.speech.sound.SoundResponse;
import com.teamacronymcoders.advisor.speech.text.LangResponse;
import com.teamacronymcoders.base.BaseModFoundation;
import com.teamacronymcoders.base.command.CommandSubBase;
import net.minecraft.client.resources.IResource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.server.command.CommandTreeBase;

import static com.teamacronymcoders.advisor.Advisor.*;

@Mod(modid = ID, name = NAME, version = VERSION, dependencies = DEPENDENCIES)
public class Advisor extends BaseModFoundation<Advisor> {
    public static final String ID = "advisor";
    public static final String NAME = "Advisor";
    public static final String VERSION = "@VERSION@";
    public static final String DEPENDENCIES = "required-after:base@[0.0.0,)";

    @Instance
    public static Advisor instance;

    public final JsonLoader<IResponse> responseLoader = new JsonLoader<>("/advisor/responses", IResponse.class,
            () -> this.getLogger().getLogger(), new ResponseDeserializer());
    private final CommandTreeBase advisorCommand = new CommandSubBase(ID);

    public Advisor() {
        super(ID, NAME, VERSION, CreativeTabs.MISC);
    }

    @Override
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        this.getPacketHandler().registerPacket(ResponseHandler.class, ResponsePacket.class, Side.CLIENT);
        AdvisorRegistries.RESPONSE_CLASS.put("advisor:sound", SoundResponse.class);
        AdvisorRegistries.RESPONSE_CLASS.put("advisor:lang", LangResponse.class);
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

    @EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        this.advisorCommand.addSubcommand(new RespondCommand());
        event.registerServerCommand(this.advisorCommand);
    }
}
