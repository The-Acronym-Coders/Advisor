package com.teamacronymcoders.advisor.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public interface IProxy {
    EntityPlayer getPlayer(MessageContext messageContext);
}
