package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

import static com.skycatdev.nopeacefuldespawn.NPaaceConfig.PEACEFUL_SPAWNERS;
import static net.minecraft.entity.mob.HostileEntity.isSpawnDark;
import static net.minecraft.entity.mob.MobEntity.canMobSpawn;

@Mixin(HostileEntity.class)
public abstract class AllowInPeacefulMixin {

    //Required for spawners
    @WrapMethod(method = "canSpawnInDark")
    private static boolean noPeacefulDespawn$dontDisallowDarkSpawns(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, Operation<Boolean> original) {
        if (Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PEACEFUL_SPAWNERS)){
            return (SpawnReason.isTrialSpawner(spawnReason) || isSpawnDark(world, pos, random)) && canMobSpawn(type, world, spawnReason, pos, random);
        }else{
            return false;
        }
    }

}
