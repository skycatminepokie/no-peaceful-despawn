package com.skycatdev.nopeacefuldespawn;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class NoPeacefulDespawnConfig {
    // Create and register a boolean game rule with the name "doPeacefulMobSpawners" which is false by default.
    public static GameRules.Key<GameRules.BooleanRule> PEACEFUL_SPAWNERS;

    public static void init() {
        PEACEFUL_SPAWNERS = GameRuleRegistry.register("doPeacefulMobSpawners", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
    }
}
