package net.soundsofthesun.blastingReloaded;

import net.fabricmc.api.ModInitializer;
import net.soundsofthesun.blastingReloaded.attachment.BRAttachments;
import net.soundsofthesun.blastingReloaded.command.BRCommands;

public class BlastingReloaded implements ModInitializer {

    public static final String MOD_ID = "blasting-reloaded";

    @Override
    public void onInitialize() {
        BRAttachments.init();
        BRCommands.init();
    }
}
