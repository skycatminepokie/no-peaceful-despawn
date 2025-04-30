package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.boss.WitherEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherEntity.class)
public abstract class WitherEntityMixin {
    @WrapOperation(method = "checkDespawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/WitherEntity;isDisallowedInPeaceful()Z"))
    private boolean no_peaceful_despawn$dontDisallowAllInPeaceful(WitherEntity instance, Operation<Boolean> original) {
        return false;
    }
}
