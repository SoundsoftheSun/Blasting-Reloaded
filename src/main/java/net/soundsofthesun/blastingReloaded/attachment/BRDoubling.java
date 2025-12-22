package net.soundsofthesun.blastingReloaded.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record BRDoubling(Integer denominator) {
    public static Codec<BRDoubling> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("denominator").forGetter(BRDoubling::denominator)
    ).apply(instance, BRDoubling::new));

    public static StreamCodec<ByteBuf, BRDoubling> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public static BRDoubling DEFAULT = new BRDoubling(4);

    public BRDoubling clear() {
        return DEFAULT;
    }
}
