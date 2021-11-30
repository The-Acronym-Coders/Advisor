package xyz.brassgoggledcoders.advisor.effect.message;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import xyz.brassgoggledcoders.advisor.api.effect.Effect;
import xyz.brassgoggledcoders.advisor.api.effect.EffectContext;
import xyz.brassgoggledcoders.advisor.api.effect.EffectType;
import xyz.brassgoggledcoders.advisor.codec.TextComponentCodec;
import xyz.brassgoggledcoders.advisor.content.AdvisorEffectTypes;

public class MessageEffect extends Effect {
    public static final Codec<MessageEffect> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(Effect::getId),
            TextComponentCodec.CODEC.fieldOf("message").forGetter(MessageEffect::getMessage)
    ).apply(instance, MessageEffect::new));

    private final ITextComponent message;

    public MessageEffect(ResourceLocation id, ITextComponent textComponent) {
        super(id);
        this.message = textComponent;
    }

    @Override
    public boolean perform(EffectContext context) {
        context.getPlayer().sendMessage(message, Util.NIL_UUID);
        return true;
    }

    public ITextComponent getMessage() {
        return this.message;
    }

    public EffectType getType() {
        return AdvisorEffectTypes.MESSAGE.get();
    }
}
