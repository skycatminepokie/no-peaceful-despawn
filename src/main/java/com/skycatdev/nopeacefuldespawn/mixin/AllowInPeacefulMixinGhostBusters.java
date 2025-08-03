package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

import static com.skycatdev.nopeacefuldespawn.NPaaceConfig.PEACEFUL_SPAWNERS;
import static net.minecraft.entity.mob.MobEntity.canMobSpawn;

@Mixin({GhastEntity.class} )
public abstract class AllowInPeacefulMixinGhostBusters {
    @WrapMethod(method = "isDisallowedInPeaceful")
    private boolean noPeacefulDespawn$dontDisallowPeaceful(Operation<Boolean> original) {
        return false;
    }

    @WrapMethod(method = "canSpawn")
    private static boolean noPeacefulDespawn$dontDisallowSpawns(EntityType<GhastEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, Operation<Boolean> original) {
        if (Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PEACEFUL_SPAWNERS)){
            return random.nextInt(20) == 0 && canMobSpawn(type, world, spawnReason, pos, random);
        }else{
            return false;
        }
    }
}
