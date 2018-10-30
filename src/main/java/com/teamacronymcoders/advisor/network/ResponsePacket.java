package com.teamacronymcoders.advisor.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ResponsePacket implements IMessage {
    private String registryName;

    @SuppressWarnings("unused")
    public ResponsePacket() {

    }

    public ResponsePacket(ResourceLocation registryName) {
        this.registryName = registryName.toString();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.registryName = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, registryName);
    }

    public String getRegistryName() {
        return registryName;
    }
}
