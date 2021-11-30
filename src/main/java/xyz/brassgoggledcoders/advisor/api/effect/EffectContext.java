package xyz.brassgoggledcoders.advisor.api.effect;

import com.google.common.collect.Sets;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraftforge.common.util.NonNullLazy;
import xyz.brassgoggledcoders.advisor.api.cause.CauseContext;

import java.util.Set;

public class EffectContext {
    private final NonNullLazy<LootContext> lootContext;
    private final Set<Object> visitedTables;
    private final ServerPlayerEntity player;

    public EffectContext(ServerPlayerEntity player) {
        this.lootContext = NonNullLazy.of(() -> new LootContext.Builder(player.getLevel())
                .create(LootParameterSets.EMPTY)
        );
        this.visitedTables = Sets.newHashSet();
        this.player = player;
    }

    public ServerPlayerEntity getPlayer() {
        return this.player;
    }

    public LootContext getLootContext() {
        return lootContext.get();
    }

    public boolean visitTable(Object effectTable) {
        return this.visitedTables.add(effectTable);
    }

    public static EffectContext fromCauseContext(CauseContext context) {
        return new EffectContext(context.getPlayer());
    }
}
