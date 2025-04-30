package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.SlimeEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({HostileEntity.class, GhastEntity.class, PhantomEntity.class, SlimeEntity.class})
public abstract class AllowInPeacefulMixin {
    @WrapMethod(method = "isDisallowedInPeaceful")
    private boolean noPeacefulDespawn$dontDisallowPeaceful(Operation<Boolean> original) {
        return false;
    }
}
