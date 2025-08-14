package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static com.skycatdev.nopeacefuldespawn.NoPeacefulDespawnConfig.PEACEFUL_SPAWNERS;

@Mixin(MobSpawnerLogic.class)
public abstract class MobSpawnerLogicMixin {
    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private Difficulty noPeacefulDespawn$allowSpawners(ServerWorld world, Operation<Difficulty> original) {
        Difficulty difficulty = original.call(world);
        // If it's not peaceful, no need to do other checks
        if (difficulty == Difficulty.PEACEFUL && world.getServer().getGameRules().get(PEACEFUL_SPAWNERS).get()) {
            return Difficulty.EASY;
        }
        return difficulty;
    }

    //In PhantomEntity.class it seems isDisallowedInPeaceful is typed again
    //This is in spite of already inheriting isDisallowedInPeaceful from MobEntity.class
    //It's more simple to wrap with this function below for people who do mod this game.
    //Mojang should remove the extra isDisallowedInPeaceful to save function calls.
    //If for whatever reason other community mods do not follow rules of spawning, we keep this here.
    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;canSpawn(Lnet/minecraft/world/WorldView;)Z"))
    private boolean noPeacefulDespawn$respectGamerule(MobEntity instance, WorldView world, Operation<Boolean> original) {
        if (instance.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            MinecraftServer server = instance.getWorld().getServer();
            if (server != null && server.getGameRules().getBoolean(PEACEFUL_SPAWNERS)) {
                return true;
            }
        }

        return original.call(instance, world);
    }

}
