package com.teamacronymcoders.advisor.response.text;

import com.teamacronymcoders.advisor.api.response.Response;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

public class TextComponentResponse extends Response {
    private final ITextComponent textComponent;

    public TextComponentResponse(ITextComponent textComponent) {
        this.textComponent = textComponent;
    }

    @Override
    public void respond(PlayerEntity playerEntity, boolean client) {
        if (!client) {
            playerEntity.sendMessage(textComponent);
        }
    }
}
