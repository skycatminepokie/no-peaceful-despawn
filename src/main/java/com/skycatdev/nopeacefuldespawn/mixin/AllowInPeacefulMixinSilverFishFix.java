package com.skycatdev.nopeacefuldespawn.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.InfestedBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Random;

import static com.skycatdev.nopeacefuldespawn.NPaaceConfig.PEACEFUL_SPAWNERS;

@Mixin(InfestedBlock.class)
public abstract class AllowInPeacefulMixinSilverFishFix {

    //possible alternative is to wrap world.spawnEntity(silverfishEntity);
    @Inject(method = "onStacksDropped", at = @At("HEAD"), cancellable = true)
    protected void noPeacefulDespawn$onStacksDroppedPeaceRules(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience, CallbackInfo ci) {
        if (world.getDifficulty() == Difficulty.PEACEFUL && (!Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PEACEFUL_SPAWNERS))) {

            Random random = new Random();
            for(int i = 0; i < 20; ++i) {
                double d = random.nextGaussian() * 0.02;
                double e = random.nextGaussian() * 0.02;
                double f = random.nextGaussian() * 0.02;
                double g = random.nextGaussian() * 0.02;
                            //spawnParticles(T parameters, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double speed) {
                world.spawnParticles(ParticleTypes.POOF, (double)pos.getX() - d * (double)10.0F, (double)pos.getY() - e * (double)10.0F, (double)pos.getZ() - f * (double)10.0F, 1, e, f, g,0.0);
            }

            ci.cancel();
        }
    }
}
