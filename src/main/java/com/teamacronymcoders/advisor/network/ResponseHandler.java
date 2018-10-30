package com.teamacronymcoders.advisor.network;

import com.teamacronymcoders.advisor.api.AdvisorRegistries;
import com.teamacronymcoders.advisor.api.speech.IResponse;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ResponseHandler implements IMessageHandler<ResponsePacket, IMessage> {
    @Override
    public IMessage onMessage(ResponsePacket message, MessageContext ctx) {
        IResponse response = AdvisorRegistries.RESPONSES.getValue(new ResourceLocation(message.getRegistryName()));
        if (response != null) {
            response.respond(Minecraft.getMinecraft().player, true);
        }
        return null;
    }
}
