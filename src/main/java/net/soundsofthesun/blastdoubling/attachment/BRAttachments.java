package net.soundsofthesun.blastdoubling.attachment;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import net.soundsofthesun.blastdoubling.BlastDoubling;

public class BRAttachments {

    public static void init() {}

    public static final AttachmentType<BRCookTime> COOK_TIME_DATA = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(BlastDoubling.MOD_ID, "cook_time_mult"),
            builder->builder
                    .initializer(()->BRCookTime.DEFAULT)
                    .persistent(BRCookTime.CODEC)
    );

    public static final AttachmentType<BRDoubling> DOUBLING_DATA = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(BlastDoubling.MOD_ID, "doubling_chance"),
            builder->builder
                    .initializer(()->BRDoubling.DEFAULT)
                    .persistent(BRDoubling.CODEC)
    );

    public static final AttachmentType<Boolean> DO_SOUND = AttachmentRegistry.createPersistent(
            Identifier.fromNamespaceAndPath(BlastDoubling.MOD_ID, "play_sound"),
            Codec.BOOL
    );


}
