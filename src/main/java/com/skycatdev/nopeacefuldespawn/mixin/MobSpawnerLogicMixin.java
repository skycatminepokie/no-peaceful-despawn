package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @ModifyReturnValue(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private Difficulty noPeacefulDespawn$allowSpawners(Difficulty original) {
        return original == Difficulty.PEACEFUL ? Difficulty.EASY : original;
    }
}
