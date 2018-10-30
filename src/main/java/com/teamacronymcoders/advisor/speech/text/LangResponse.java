package com.teamacronymcoders.advisor.speech.text;

import com.teamacronymcoders.advisor.api.speech.IResponse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class LangResponse extends IForgeRegistryEntry.Impl<IResponse> implements IResponse {
    private final String langKey;

    public LangResponse(String langKey) {
        this.langKey = langKey;
    }

    @Override
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void respond(EntityPlayer entityPlayer, boolean client) {
        if (client) {
            entityPlayer.sendMessage(new TextComponentTranslation(langKey));
        }
    }
}
