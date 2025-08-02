package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.spawner.MobSpawnerLogic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

import static com.skycatdev.nopeacefuldespawn.NPaaceConfig.PEACEFUL_SPAWNS;

@Mixin(MobSpawnerLogic.class)
public class MobSpawnerLogicMixin {
    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getDifficulty()Lnet/minecraft/world/Difficulty;"))
    private Difficulty noPeacefulDespawn$allowSpawners(ServerWorld world, Operation<Difficulty> original) {
        if (Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PEACEFUL_SPAWNS)){
            return Difficulty.NORMAL;
        }else{
            return Difficulty.PEACEFUL;
        }
    }

    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;getSpawnGroup()Lnet/minecraft/entity/SpawnGroup;"))
    private SpawnGroup noPeacefulDespawn$allowSpawners2(EntityType instance, Operation<SpawnGroup> original) {
        if (instance.getSpawnGroup() == SpawnGroup.MONSTER){
            return SpawnGroup.AMBIENT;
        }
        return instance.getSpawnGroup();
    }
}
