package com.any.mikuplushie.datagen;

import com.any.mikuplushie.MikuPlushie;
import com.any.mikuplushie.registry.ModEntities;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {

    public ModEntityTagProvider(FabricPackOutput output,
                                CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    public static final TagKey<EntityType<?>> PLUSH_ENTITY = TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "plush_entity"));

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        //REGISTER PLUSH ENTITY TYPE TAG AUTOMATICALLY
        for (EntityType<?> plush : ModEntities.PLUSH_ENTITIES){
            builder(PLUSH_ENTITY).add(plush.builtInRegistryHolder().key());
        }
    }
}
