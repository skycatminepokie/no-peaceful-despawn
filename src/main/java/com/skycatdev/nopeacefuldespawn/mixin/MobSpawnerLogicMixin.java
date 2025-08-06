package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

import static com.skycatdev.nopeacefuldespawn.NoPeacefulDespawnConfig.PEACEFUL_SPAWNERS;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private Difficulty noPeacefulDespawn$allowSpawners(ServerWorld world, Operation<Difficulty> original) {
        if (Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PEACEFUL_SPAWNERS)){
            return Difficulty.EASY;
        }else{
            return Difficulty.PEACEFUL;
        }
    }

    //Extra check for monsters like phantoms that do not follow rules of spawning
    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;canSpawn(Lnet/minecraft/world/WorldView;)Z"))
    private boolean noPeacefulDespawn$respectGamerule(MobEntity instance, WorldView world, Operation<Boolean> original) {
        if (!instance.getWorld().getServer().getGameRules().getBoolean(PEACEFUL_SPAWNERS) && instance.getWorld().getDifficulty() == Difficulty.PEACEFUL){
            if (instance instanceof Monster){
                return false;
            }
        }

        return true;
    }

}
