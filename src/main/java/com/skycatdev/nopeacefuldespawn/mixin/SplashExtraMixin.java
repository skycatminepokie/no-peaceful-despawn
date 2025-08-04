package com.skycatdev.nopeacefuldespawn.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;
import java.util.List;


@Mixin(SplashTextResourceSupplier.class)

public class SplashExtraMixin {
    @WrapOperation(
                method = "apply(Ljava/util/List;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V",
                at = @At
                        (
                                value = "INVOKE",
                                target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z"
                        )
        )
   private boolean appendList(List<String> instance, Collection<String> collection, Operation<Boolean> original) {
        var splashes = original.call(instance, collection);
        instance.add("BABY SLIMES are your friends!");
        instance.add("Peaceful ZOMBIFIED PIGLINS!");
        instance.add("UWA much friends?");
        instance.add("Friendly creatures brought to you by SkyCatMinePokie");
        instance.add("Friendly creatures brought to you by ActuallyTheOwner");
        instance.add("Friendly creatures brought to you by....? [[MINECRAFT! Please add peaceful monster spawners!]]");
        instance.add(instance.size() + " MANY SPLASHES!");
        return splashes;
    }
}