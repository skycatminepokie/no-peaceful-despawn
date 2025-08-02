package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

import static com.skycatdev.nopeacefuldespawn.NPaaceConfig.PEACEFUL_SPAWNS;
import static net.minecraft.entity.mob.MobEntity.canMobSpawn;

@Mixin({SlimeEntity.class})
public abstract class AllowInPeacefulMixinSlimer {
    @WrapMethod(method = "isDisallowedInPeaceful")
    private boolean noPeacefulDespawn$dontDisallowPeaceful(Operation<Boolean> original) {
        return false;
    }

    @WrapMethod(method = "canSpawn")
    private static boolean noPeacefulDespawn$dontDisallowSpawns(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, Operation<Boolean> original) {
        if (Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PEACEFUL_SPAWNS)) {
            if (SpawnReason.isAnySpawner(spawnReason)) {
                return canMobSpawn(type, world, spawnReason, pos, random);
            }

            if (world.getBiome(pos).isIn(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS) && pos.getY() > 50 && pos.getY() < 70 && random.nextFloat() < 0.5F && random.nextFloat() < world.getMoonSize() && world.getLightLevel(pos) <= random.nextInt(8)) {
                return canMobSpawn(type, world, spawnReason, pos, random);
            }

            if (!(world instanceof StructureWorldAccess)) {
                return false;
            }

            ChunkPos chunkPos = new ChunkPos(pos);
            boolean bl = ChunkRandom.getSlimeRandom(chunkPos.x, chunkPos.z, ((StructureWorldAccess) world).getSeed(), 987234911L).nextInt(10) == 0;
            if (random.nextInt(10) == 0 && bl && pos.getY() < 40) {
                return canMobSpawn(type, world, spawnReason, pos, random);
            }
        }
        return false;
    }
}
