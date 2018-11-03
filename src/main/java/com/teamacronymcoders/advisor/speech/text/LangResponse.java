package com.teamacronymcoders.advisor.speech.text;

import com.google.gson.annotations.JsonAdapter;
import com.teamacronymcoders.advisor.api.speech.Response;
import com.teamacronymcoders.advisor.json.constructor.ConstructorDeserializer;
import com.teamacronymcoders.advisor.json.constructor.JsonConstructor;
import com.teamacronymcoders.advisor.json.constructor.JsonProperty;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;

@JsonAdapter(ConstructorDeserializer.class)
public class LangResponse extends Response {
    private final String langKey;

    @JsonConstructor
    public LangResponse(@JsonProperty(value = "langKey", required = true) String langKey) {
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
