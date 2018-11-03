package com.teamacronymcoders.advisor.command;

import com.teamacronymcoders.advisor.Advisor;
import com.teamacronymcoders.advisor.api.AdvisorRegistries;
import com.teamacronymcoders.advisor.api.speech.Response;
import com.teamacronymcoders.advisor.network.ResponsePacket;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class RespondCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "respond";
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public String getUsage(ICommandSender sender) {
        return "advisor.command.respond";
    }

    @Override
    @ParametersAreNonnullByDefault
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1 || args.length > 2) {
            throw new WrongUsageException("advisor.command.args.wrongAmount", 1);
        }
        if (sender instanceof EntityPlayerMP) {
            Response response = AdvisorRegistries.RESPONSES.getValue(new ResourceLocation(args[0]));
            if (response == null) {
                throw new WrongUsageException("advisor.value.notFound", args[0]);
            } else {
                EntityPlayerMP entityPlayer = (EntityPlayerMP) sender;
                if (response.canRespond(entityPlayer) || forceResponse(args)) {
                    response.respond(entityPlayer, false);
                    Advisor.instance.getPacketHandler().sendToPlayer(new ResponsePacket(Objects.requireNonNull(response.getRegistryName())), entityPlayer);
                }
            }
        }
    }

    private boolean forceResponse(String[] args) throws CommandException {
        if (args.length > 1) {
            return parseBoolean(args[1]);
        }
        return false;
    }
}
