package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.mob.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {
    @WrapOperation(method = "checkDespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;isDisallowedInPeaceful()Z"))
    private boolean no_peaceful_despawn$allowAllInPeaceful(MobEntity instance, Operation<Boolean> original) {
        return false;
    }
}
