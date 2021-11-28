package xyz.brassgoggledcoders.advisor.api.cause;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.LootContext;

public class CauseContext {
    private final ServerPlayerEntity player;
    private final LootContext lootContext;

    public CauseContext(ServerPlayerEntity player, LootContext lootContext) {
        this.lootContext = lootContext;
        this.player = player;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public LootContext getLootContext() {
        return lootContext;
    }
}
