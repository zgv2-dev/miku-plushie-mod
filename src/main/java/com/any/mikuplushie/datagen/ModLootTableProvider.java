package com.any.mikuplushie.datagen;

import com.any.mikuplushie.registry.ModBlocks;
import com.any.mikuplushie.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootSubProvider {

    protected ModLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
	public void generate() {
        //GENERATE PLUSH LOOT TABLES ENTRIES AUTOMATICALLY
        List<Block> plushBlocks = ModBlocks.PLUSH_BLOCKS;
        List<Item> plushItems = ModItems.PLUSH_ITEMS;
        for (int block = 0; block < plushBlocks.size(); block++) {
            dropOther(plushBlocks.get(block), plushItems.get(block));
        }

        LootItemCondition.Builder leekLootCondition = MatchBlock.blockMatches(blocks, ModBlocks.LEEK_CROP,
            StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(ModBlocks.LEEK_CROP, createCropDrops(ModBlocks.LEEK_CROP, ModItems.LEEK, ModItems.LEEK_SEEDS, leekLootCondition));
        LootItemCondition.Builder wildLeekLootCondition = MatchBlock.blockMatches(blocks, ModBlocks.WILD_LEEK_CROP);
        add(ModBlocks.WILD_LEEK_CROP, createCropDrops(ModBlocks.WILD_LEEK_CROP, ModItems.LEEK, ModItems.LEEK_SEEDS, wildLeekLootCondition));
	}
}
