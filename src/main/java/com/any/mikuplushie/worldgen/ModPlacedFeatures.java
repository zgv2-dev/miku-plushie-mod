package com.any.mikuplushie.worldgen;

import com.any.mikuplushie.MikuPlushie;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

// Feature + placed feature are plain JSON in resources/data/miku-plushie/worldgen (26.3 format).
public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> LEEK_PLACED_KEY =
        ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "leek_placed"));
}
