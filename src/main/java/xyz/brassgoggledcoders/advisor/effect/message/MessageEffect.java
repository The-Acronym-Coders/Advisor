package xyz.brassgoggledcoders.advisor.effect.message;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectType;
import xyz.brassgoggledcoders.advisor.codec.TextComponentCodec;
import xyz.brassgoggledcoders.advisor.content.AdvisorEffectTypes;

public class MessageEffect extends Effect {
    public static final Codec<MessageEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TextComponentCodec.CODEC.fieldOf("message").forGetter(MessageEffect::getMessage)
    ).apply(instance, MessageEffect::new));

    private final ITextComponent message;

    public MessageEffect(ITextComponent textComponent) {
        this.message = textComponent;
    }

    @Override
    public void perform(PlayerEntity player) {
        if (!player.level.isClientSide()) {
            player.sendMessage(message, Util.NIL_UUID);
        }
    }

    public ITextComponent getMessage() {
        return this.message;
    }

    public EffectType getType() {
        return AdvisorEffectTypes.MESSAGE.get();
    }
}
