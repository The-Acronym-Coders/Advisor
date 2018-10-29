package com.teamacronymcoders.advisor.speech.text;

import com.teamacronymcoders.advisor.api.speech.Response;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

public class LangResponse implements Response {
    private final String name;
    private final String langKey;

    public LangResponse(String name, String langKey) {
        this.name = name;
        this.langKey = langKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean canRespond(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void respond(EntityPlayer entityPlayer, boolean client) {
        if (client) {
            entityPlayer.sendStatusMessage(new TextComponentTranslation(langKey), false);
        }
    }
}
