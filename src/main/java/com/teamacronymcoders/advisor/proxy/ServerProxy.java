package com.teamacronymcoders.advisor.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerProxy implements IProxy {
    @Override
    public EntityPlayer getPlayer(MessageContext messageContext) {
        return messageContext.getServerHandler().player;
    }
}
