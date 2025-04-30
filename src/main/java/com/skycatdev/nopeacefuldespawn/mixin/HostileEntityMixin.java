package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin {
    @WrapOperation(method = "canSpawnIgnoreLightLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private static Difficulty noPeacefulDespawn$skipPeacefulCheck(WorldAccess instance, Operation<Difficulty> original) {
        return Difficulty.NORMAL;
    }
}
