package xyz.brassgoggledcoders.advisor.api.cause;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.util.NonNullSupplier;

import java.util.function.Supplier;

public class CauseContext {
    private final ServerPlayerEntity player;
    private final NonNullLazy<LootContext> lootContext;

    public CauseContext(ServerPlayerEntity player) {
        this.player = player;
        this.lootContext = NonNullLazy.of(() -> new LootContext.Builder(player.getLevel())
                .withParameter(LootParameters.ORIGIN, player.position())
                .withParameter(LootParameters.THIS_ENTITY, player)
                .withLuck(player.getLuck())
                .create(LootParameterSets.EMPTY)
        );
    }

    public CauseContext(ServerPlayerEntity player, LootContext lootContext) {
        this.lootContext = NonNullLazy.of(() -> lootContext);
        this.player = player;
    }

    public ServerPlayerEntity getPlayer() {
        return player;
    }

    public LootContext getLootContext() {
        return lootContext.get();
    }

    public NonNullSupplier<LootContext> supplyLootContext() {
        return lootContext;
    }
}
