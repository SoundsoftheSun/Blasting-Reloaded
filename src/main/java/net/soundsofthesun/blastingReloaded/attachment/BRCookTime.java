package net.soundsofthesun.blastingReloaded.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record BRCookTime(Integer mult) {
    public static Codec<BRCookTime> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("mult").forGetter(BRCookTime::mult)
    ).apply(instance, BRCookTime::new));

    public static StreamCodec<ByteBuf, BRCookTime> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static BRCookTime DEFAULT = new BRCookTime(2);

    public BRCookTime clear() {
        return DEFAULT;
    }
}
