package com.teamacronymcoders.advisor.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy implements IProxy {
    @Override
    public EntityPlayer getPlayer(MessageContext messageContext) {
        return Minecraft.getMinecraft().player;
    }
}
