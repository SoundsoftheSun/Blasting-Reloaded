package net.soundsofthesun.blastdoubling.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.soundsofthesun.blastdoubling.attachment.BRAttachments;
import net.soundsofthesun.blastdoubling.attachment.BRCookTime;
import net.soundsofthesun.blastdoubling.attachment.BRDoubling;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {

    @Inject(method = "getTotalCookTime", at = @At("RETURN"), cancellable = true)
    private static void modGetTotalCookTime(ServerLevel serverLevel, AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, CallbackInfoReturnable<Integer> cir) {
        if (abstractFurnaceBlockEntity instanceof BlastFurnaceBlockEntity) {
            cir.setReturnValue(cir.getReturnValue() * (serverLevel.getAttachedOrElse(BRAttachments.COOK_TIME_DATA, BRCookTime.DEFAULT)).multiplier() );
        }
    }

    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/AbstractFurnaceBlockEntity;burn(Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/item/crafting/RecipeHolder;Lnet/minecraft/world/item/crafting/SingleRecipeInput;Lnet/minecraft/core/NonNullList;I)Z"))
    private static boolean modBurn(RegistryAccess registryAccess, @Nullable RecipeHolder<? extends AbstractCookingRecipe> recipeHolder, SingleRecipeInput singleRecipeInput, NonNullList<ItemStack> nonNullList, int i, Operation<Boolean> original, @Local AbstractFurnaceBlockEntity abe) {
        if (original.call(registryAccess, recipeHolder, singleRecipeInput, nonNullList, i)) {
            if (abe instanceof BlastFurnaceBlockEntity && abe.getLevel() instanceof ServerLevel level) {
                ItemStack result = recipeHolder.value().assemble(singleRecipeInput, registryAccess);
                ItemStack output = nonNullList.get(2);
                if (!(output.isEmpty()) && ItemStack.isSameItemSameComponents(output, result)) {
                    int doubling = level.getAttachedOrElse(BRAttachments.DOUBLING_DATA, BRDoubling.DEFAULT).denominator();
                    if (doubling == 1) {
                        output.grow(1);
                    } else if (doubling == 0) {
                        return true;
                    } else if (level.getRandom().nextInt(doubling) == 0) {
                        output.grow(1);
                        if (level.getAttachedOrElse(BRAttachments.DO_SOUND, true)) level.playSound(null, abe.getBlockPos(), SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 0.25f, 0.4f);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
