package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

import static com.skycatdev.nopeacefuldespawn.NoPeacefulDespawnConfig.PEACEFUL_SPAWNERS;
import static net.minecraft.entity.mob.MobEntity.canMobSpawn;

@Mixin({GhastEntity.class} )
public abstract class AllowInPeacefulMixinGhostBusters {
    @WrapOperation(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private static Difficulty noPeacefulDespawn$skipPeacefulCheck(WorldAccess world, Operation<Difficulty> original) {
        Difficulty difficulty = original.call(world);
        if (difficulty == Difficulty.PEACEFUL) { // If it's not peaceful, no need to do other checks
            MinecraftServer server = world.getServer();
            if (server != null && server.getGameRules().get(PEACEFUL_SPAWNERS).get()) { // If it should spawn, let it
                return Difficulty.EASY;
            }
        }
        return difficulty;
    }
}
