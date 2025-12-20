package net.soundsofthesun.blastingReloaded.mixin;

import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BlastFurnaceBlockEntity.class)
public class BlastFurnaceBlockEntityMixin {

    @ModifyConstant(method = "getBurnDuration", constant = @Constant(intValue = 2))
    private int a(int constant) {
        return 2;
    }

}
