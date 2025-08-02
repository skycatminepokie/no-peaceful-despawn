package com.skycatdev.nopeacefuldespawn;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class NPaaceConfig {
    // Create and register a boolean game rule with the name "shouldPigsFly" which is true by default.
    public static GameRules.Key<GameRules.BooleanRule> PEACEFUL_SPAWNS;

    public static void init() {
        PEACEFUL_SPAWNS =             GameRuleRegistry.register("do_peaceful_spawns", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
    }
}
