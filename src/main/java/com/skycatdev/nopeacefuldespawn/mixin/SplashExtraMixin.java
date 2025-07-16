package com.skycatdev.nopeacefuldespawn.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;

@Mixin(SplashTextResourceSupplier.class)

public class SplashExtraMixin {

    public final SplashTextRenderer SLIME_FRIENDS = new SplashTextRenderer("Baby Slimes are your friends!");
    public final SplashTextRenderer Z_PIG = new SplashTextRenderer("Peaceful Zombified Piglins!");

    public Identifier splashes = Identifier.ofVanilla("texts/splashes.txt");
    ResourceManager manager = MinecraftClient.getInstance().getResourceManager();

    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    public void onGet(CallbackInfoReturnable<SplashTextRenderer> cir) throws IOException {

        //Note for updaters, remember to add to this list
        SplashTextRenderer[] Added_Entries = {
            SLIME_FRIENDS,
            Z_PIG,
        };

        Random random = new Random();

        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(manager.getResourceOrThrow(splashes).getReader())) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int randomIndex = random.nextInt(lineCount + Added_Entries.length);
        if (randomIndex >= lineCount) {
            int newSplash = random.nextInt(Added_Entries.length);
            cir.setReturnValue(Added_Entries[newSplash]);
        }

        //For debug
        //cir.setReturnValue(new SplashTextRenderer((lineCount + added_string_count) + " with chance of 1 /  " + (randomIndex)));
    }
}