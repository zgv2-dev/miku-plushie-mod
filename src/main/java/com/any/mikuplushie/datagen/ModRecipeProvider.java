package com.any.mikuplushie.datagen;

import com.any.mikuplushie.registry.ModBlocks;
import com.any.mikuplushie.registry.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.recipes.RecipeProvider;
//? if >=26.3 {
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.advancements.Advancement;
//?}
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    //? if >=26.3 {
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new Recipes(recipes, advancements);
    }
    //?} else {
    /*protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new Recipes(registries, output);
    }
    *///?}

    @Override
    public String getName() {
        return "Miku Plushie Recipes";
    }

    private static class Recipes extends RecipeProvider {
    //? if >=26.3 {
    Recipes(BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        super(recipes, advancements);
    }
    //?} else {
    /*Recipes(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }
    *///?}

    @Override
    public void buildRecipes() {
        RecipeOutput exporter = output;

        shaped(RecipeCategory.FOOD, ModItems.CANUDINHO)
            .pattern("p")
            .pattern("p")
            .define('p', Items.PAPER)
            .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
            .save(exporter);

        shaped(RecipeCategory.FOOD, ModItems.BAGUETTE)
            .pattern("www")
            .pattern("www")
            .define('w', Items.WHEAT)
            .unlockedBy(getHasName(Items.WHEAT), has(Items.WHEAT))
            .save(exporter);

        simpleShapeless(exporter, ModItems.AKITA_NERU_PHONE, Items.GOLD_INGOT, Items.REDSTONE, Items.STAINED_GLASS.black());

        shaped(RecipeCategory.FOOD, ModItems.VOCALOID_HEART)
            .pattern("LN")
            .pattern("BP")
            .define('L', ModItems.LEEK)
            .define('N', Items.NOTE_BLOCK)
            .define('B', ModItems.BAGUETTE)
            .define('P', ModItems.AKITA_NERU_PHONE)
            .unlockedBy(getHasName(ModItems.LEEK), has(ModItems.LEEK))
            .unlockedBy(getHasName(Items.NOTE_BLOCK), has(Items.NOTE_BLOCK))
            .unlockedBy(getHasName(ModItems.BAGUETTE), has(ModItems.BAGUETTE))
            .unlockedBy(getHasName(ModItems.AKITA_NERU_PHONE), has(ModItems.AKITA_NERU_PHONE))
            .save(exporter);

        //AIKO
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.AIKO_PLUSH)
                .pattern("121")
                .pattern("131")
                .define('1', Items.WOOL.blue())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.green())
                .unlockedBy(getHasName(Items.WOOL.blue()), has(Items.WOOL.blue()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.green()), has(Items.WOOL.green()))
                .save(exporter);
        }

        //NERU
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.AKITA_NERU_PLUSH)
                .pattern("121")
                .pattern("131")
                .define('1', Items.WOOL.yellow())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.brown())
                .unlockedBy(getHasName(Items.WOOL.yellow()), has(Items.WOOL.yellow()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.brown()), has(Items.WOOL.brown()))
                .save(exporter);

            plushShapeless(exporter, ModBlocks.AKITA_NERU_PLUSH_TAILS, ModBlocks.AKITA_NERU_PLUSH, Items.WOOL.yellow(), Items.REDSTONE);
        }

        //RIN
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.RIN_PLUSH)
                .pattern("121")
                .pattern("121")
                .define('1', Items.WOOL.yellow())
                .define('2', Items.WOOL.white())
                .unlockedBy(getHasName(Items.WOOL.yellow()), has(Items.WOOL.yellow()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .save(exporter);
        }

        //LEN
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.LEN_PLUSH)
                .pattern("121")
                .pattern("131")
                .define('1', Items.WOOL.yellow())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.gray())
                .unlockedBy(getHasName(Items.WOOL.yellow()), has(Items.WOOL.yellow()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.gray()), has(Items.WOOL.gray()))
                .save(exporter);
        }

        //KONOHA
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.KONOHA_PLUSH)
                .pattern("121")
                .pattern("323")
                .define('1', Items.WOOL.white())
                .define('2', Items.WOOL.lightGray())
                .define('3', Items.WOOL.lime())
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.lightGray()), has(Items.WOOL.lightGray()))
                .unlockedBy(getHasName(Items.WOOL.lime()), has(Items.WOOL.lime()))
                .save(exporter);
        }

        //LUKA
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.LUKA_PLUSH)
                .pattern("121")
                .pattern("131")
                .define('1', Items.WOOL.pink())
                .define('2', Items.WOOL.yellow())
                .define('3', Items.WOOL.brown())
                .unlockedBy(getHasName(Items.WOOL.pink()), has(Items.WOOL.pink()))
                .unlockedBy(getHasName(Items.WOOL.yellow()), has(Items.WOOL.yellow()))
                .unlockedBy(getHasName(Items.WOOL.brown()), has(Items.WOOL.brown()))
                .save(exporter);
        }

        //MIKU
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.MIKU_PLUSH)
                .pattern("121")
                .pattern("131")
                .define('1', Items.WOOL.cyan())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.gray())
                .unlockedBy(getHasName(Items.WOOL.cyan()), has(Items.WOOL.cyan()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.gray()), has(Items.WOOL.gray()))
                .save(exporter);

            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR, ModBlocks.MIKU_PLUSH, Items.WOOL.yellow(), Items.WOOL.green());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_BA, ModBlocks.MIKU_PLUSH, Items.WOOL.red(), Items.WOOL.white(), Items.WOOL.blue());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BIK, ModBlocks.MIKU_PLUSH, Items.WOOL.blue(), Items.WATER_BUCKET);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_BEACH, ModBlocks.MIKU_PLUSH, Items.WOOL.yellow(), Items.WOOL.green(), Items.SAND);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_BRAID, ModBlocks.MIKU_PLUSH, Items.WOOL.yellow(), Items.WOOL.green(), Items.GLOWSTONE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_BA_DRUM, ModBlocks.MIKU_PLUSH, Items.WOOL.red(), Items.WOOL.white(), Items.WOOL.blue(), Items.NOTE_BLOCK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_PA, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.CORNFLOWER);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_SP, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.red(), Items.WOOL.black(), Items.CONCRETE.gray());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_MG, ModBlocks.MIKU_PLUSH, Items.WOOL.brown(), Items.WOOL.red(), Items.GOLD_NUGGET);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_BROWN_BRO, ModBlocks.MIKU_PLUSH, Items.WOOL.brown(), Items.WOOL.black(), Items.IRON_NUGGET);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_ELECTRICIAN, ModBlocks.MIKU_PLUSH, Items.WOOL.brown(), Items.WOOL.blue(), Items.REDSTONE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_BIK_ORANGE, ModBlocks.MIKU_PLUSH, Items.WOOL.orange(), Items.WATER_BUCKET);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_AM, ModBlocks.MIKU_PLUSH, Items.WOOL.green(), Items.WOOL.yellow(), Items.WOOL.blue(), Items.JUNGLE_SAPLING);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_FUT_FLA, ModBlocks.MIKU_PLUSH, Items.WOOL.red(), Items.WOOL.black());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_FUT_CAM, ModBlocks.MIKU_PLUSH, Items.WOOL.lightGray(), Items.WOOL.black());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_FUT_CRVG, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.black(), Items.CARTOGRAPHY_TABLE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_GO, ModBlocks.MIKU_PLUSH, Items.WOOL.yellow(), Items.WOOL.green(), Items.LEAD);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_SCHOOL_PE, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.blue(), Items.TUBE_CORAL_FAN);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_BR_RS, ModBlocks.MIKU_PLUSH, Items.WOOL.gray(), Items.WOOL.red(), Items.MOSS_BLOCK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_FROG, ModBlocks.MIKU_PLUSH, Items.WOOL.lightBlue(), Items.TADPOLE_BUCKET);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_MUSHROOM, ModBlocks.MIKU_PLUSH, Items.MOSS_BLOCK, Items.RED_MUSHROOM_BLOCK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_SENBONZAKURA, ModBlocks.MIKU_PLUSH, Items.WOOL.green(), Items.CHERRY_LOG);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_URAOTOMELOVERS, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.black());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_PERSONADANCING, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.black(), Items.NOTE_BLOCK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_HELLOPLANET, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.lime(), Items.WOOL.magenta());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_HACHUNE, ModBlocks.MIKU_PLUSH, Items.LILY_OF_THE_VALLEY);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_ZATSUNE, ModBlocks.MIKU_PLUSH, Items.WOOL.black(), Items.WOOL.black());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_INFINITY, ModBlocks.MIKU_PLUSH, Items.ENDER_EYE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_VAMPIRE, ModBlocks.MIKU_PLUSH, Items.FERMENTED_SPIDER_EYE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_WEREWOMAN, ModBlocks.MIKU_PLUSH, Items.BONE, Items.MUTTON);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_JASON, ModBlocks.MIKU_PLUSH, Items.BIRCH_PLANKS, Items.WOOL.brown(), Items.IRON_SWORD);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_MICHAEL_MYERS, ModBlocks.MIKU_PLUSH, Items.WOOL.brown(), Items.WOOL.blue(), Items.IRON_SWORD);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_PUMPKIN, ModBlocks.MIKU_PLUSH, Items.CARVED_PUMPKIN);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_GHOSTFACE, ModBlocks.MIKU_PLUSH, Items.BIRCH_PLANKS, Items.WOOL.black(), Items.IRON_SWORD);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_FRANKENSTEIN, ModBlocks.MIKU_PLUSH, Items.WOOL.brown(), Items.WOOL.green(), Items.LIGHTNING_ROD.weathering().unaffected());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_MUMMY, ModBlocks.MIKU_PLUSH, Items.WOOL.black(), Items.PAPER, Items.PAPER);

            shaped(RecipeCategory.DECORATIONS, ModBlocks.MIKU_PLUSH_GHOST)
                .pattern("121")
                .pattern("131")
                .define('1', Items.STAINED_GLASS.cyan())
                .define('2', Items.STAINED_GLASS.white())
                .define('3', Items.STAINED_GLASS.gray())
                .unlockedBy(getHasName(Items.STAINED_GLASS.cyan()), has(Items.STAINED_GLASS.cyan()))
                .unlockedBy(getHasName(Items.STAINED_GLASS.white()), has(Items.STAINED_GLASS.white()))
                .unlockedBy(getHasName(Items.STAINED_GLASS.gray()), has(Items.STAINED_GLASS.gray()))
                .save(exporter);

            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_PATATI, ModBlocks.MIKU_PLUSH, Items.WOOL.yellow(), Items.WOOL.lightBlue(), Items.WOOL.white());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_PATATA, ModBlocks.MIKU_PLUSH, Items.WOOL.yellow(), Items.WOOL.lime(), Items.WOOL.red());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_DEVIL, ModBlocks.MIKU_PLUSH, Items.MAGMA_BLOCK, Items.NETHERRACK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_WITCH, ModBlocks.MIKU_PLUSH, Items.WOOL.purple(), Items.WOOL.green(), Items.STICK, Items.WHEAT);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_SANTA, ModBlocks.MIKU_PLUSH, Items.WOOL.red(), Items.WOOL.white(), Items.SNOW_BLOCK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_REINDEER, ModBlocks.MIKU_PLUSH, Items.WOOL.brown(), Items.REDSTONE_TORCH);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_SANTA_ELF, ModBlocks.MIKU_PLUSH, Items.WOOL.lime(), Items.WOOL.red());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_XMAS_TREE, ModBlocks.MIKU_PLUSH, Items.SPRUCE_LEAVES, Items.WOOL.red());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_SONIC_CROSSWORLDS, ModBlocks.MIKU_PLUSH, Items.WOOL.magenta(), Items.WOOL.black());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_FORTNITE_NEKO, ModBlocks.MIKU_PLUSH, Items.WOOL.pink(), Items.WOOL.lightBlue());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_V4, ModBlocks.MIKU_PLUSH, Items.IRON_INGOT);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_MESMERIZER, ModBlocks.MIKU_PLUSH, Items.WOOL.lightBlue(), Items.WOOL.lightBlue());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_SONIC, ModBlocks.MIKU_PLUSH, Items.WOOL.blue(), Items.REDSTONE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_DIGITAL_STARS_2025, ModBlocks.MIKU_PLUSH, Items.NOTE_BLOCK, Items.GOLD_NUGGET, Items.GOLD_NUGGET, Items.GOLD_NUGGET, Items.GOLD_NUGGET);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_ROTTEN_GIRL, ModBlocks.MIKU_PLUSH, Items.ROTTEN_FLESH);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_PSYCHO_MODE, ModBlocks.MIKU_PLUSH, Items.AMETHYST_SHARD);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_DONT_BELIEVE_IN_T, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.lightBlue());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_STATIC, ModBlocks.MIKU_PLUSH, Items.DYE.yellow(), Items.DYE.magenta(), Items.DYE.cyan(), Items.WOOL.blue());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_MOCHIMOCHI, ModBlocks.MIKU_PLUSH, Items.WOOL.lightBlue(), Items.WOOL.pink(), Items.PINK_PETALS);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_MONITORING, ModBlocks.MIKU_PLUSH, Items.WOOL.brown(), Items.SPYGLASS);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_HOLLOW_KNIGHT, ModBlocks.MIKU_PLUSH, Items.WOOL.black(), Items.IRON_SWORD, Items.BONE_BLOCK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_HORNET, ModBlocks.MIKU_PLUSH, Items.WOOL.red(), Items.IRON_SWORD, Items.BONE_BLOCK);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_WORLD_IS_MINE, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.GOLD_INGOT, Items.CAKE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_ROLLING_GIRL, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WOOL.brown());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_DEEP_SEA_GIRL, ModBlocks.MIKU_PLUSH, Items.TUBE_CORAL, Items.BUBBLE_CORAL);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_LUCARIO_Z, ModBlocks.MIKU_PLUSH, Items.IRON_BARS, Items.WOOL.white(), Items.REDSTONE);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_PPPP, ModBlocks.MIKU_PLUSH, Items.WOOL.cyan(), Items.WOOL.white());
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_LINK, ModBlocks.MIKU_PLUSH, Items.WOOL.green(), Items.EMERALD);
            plushShapeless(exporter, ModBlocks.MIKU_PLUSH_RENAISSANCE, ModBlocks.MIKU_PLUSH, Items.WOOL.white(), Items.WRITABLE_BOOK);
        }

        //TETO
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.TETO_PLUSH)
                .pattern("121")
                .pattern("131")
                .define('1', Items.WOOL.red())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.lightGray())
                .unlockedBy(getHasName(Items.WOOL.red()), has(Items.WOOL.red()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.lightGray()), has(Items.WOOL.lightGray()))
                .save(exporter);
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE, ModBlocks.TETO_PLUSH);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_MESMERIZER, ModBlocks.TETO_PLUSH, Items.WOOL.red(), Items.WOOL.red());
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_MESMERIZER, ModBlocks.TETO_PLUSH_MESMERIZER);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_SHADOW, ModBlocks.TETO_PLUSH, Items.WOOL.black(), Items.REDSTONE);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_BIRDBRAIN, ModBlocks.TETO_PLUSH, Items.WHEAT_SEEDS, Items.EGG);
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_BIRDBRAIN, ModBlocks.TETO_PLUSH_BIRDBRAIN);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_REGRET_ROCK, ModBlocks.TETO_PLUSH, Items.DYE.purple());
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_REGRET_ROCK, ModBlocks.TETO_PLUSH_REGRET_ROCK);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_DONT_BELIEVE_IN_T, ModBlocks.TETO_PLUSH, Items.WOOL.white(), Items.WOOL.red());
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_DONT_BELIEVE_IN_T, ModBlocks.TETO_PLUSH_DONT_BELIEVE_IN_T);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_LIAR_DANCER, ModBlocks.TETO_PLUSH, Items.STAINED_GLASS.black(), Items.STAINED_GLASS.black(), Items.WOOL.white());
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_LIAR_DANCER, ModBlocks.TETO_PLUSH_LIAR_DANCER);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_WHATCHACALLITSNAME, ModBlocks.TETO_PLUSH, Items.GLASS, Items.GLASS, Items.WOOL.red(), Items.WOOL.orange());
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_WHATCHACALLITSNAME, ModBlocks.TETO_PLUSH_WHATCHACALLITSNAME);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_SOME_MORE_OF_THAT_SONG, ModBlocks.TETO_PLUSH, Items.WOOL.lightBlue());
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_SOME_MORE_OF_THAT_SONG, ModBlocks.TETO_PLUSH_SOME_MORE_OF_THAT_SONG);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_LOBSTER, ModBlocks.TETO_PLUSH, Items.SEAGRASS, Items.SEAGRASS);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_SYNTHV, ModBlocks.TETO_PLUSH, Items.IRON_INGOT);
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_SYNTHV, ModBlocks.TETO_PLUSH_SYNTHV);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_SPOKEN_FOR, ModBlocks.TETO_PLUSH, Items.DYE.pink(), Items.GLOWSTONE_DUST);
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_SPOKEN_FOR, ModBlocks.TETO_PLUSH_SPOKEN_FOR);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_PPPP, ModBlocks.TETO_PLUSH, Items.WOOL.red(), Items.WOOL.white());
            pickaxeRecipe(exporter, ModItems.TETO_PICKAXE_PPPP, ModBlocks.TETO_PLUSH_PPPP);

            plushShapeless(exporter, ModBlocks.TETO_PLUSH_SHRIMP, ModBlocks.TETO_PLUSH, Items.KELP, Items.KELP);

        }

        //MEIKO
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.MEIKO_PLUSH)
                .pattern("121")
                .pattern(" 3 ")
                .define('1', Items.WOOL.brown())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.red())
                .unlockedBy(getHasName(Items.WOOL.brown()), has(Items.WOOL.brown()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.red()), has(Items.WOOL.red()))
                .save(exporter);

            plushShapeless(exporter, ModBlocks.MEIKO_PLUSH_V3, ModBlocks.MEIKO_PLUSH, Items.IRON_INGOT);
            plushShapeless(exporter, ModBlocks.MEIKO_PLUSH_V4, ModBlocks.MEIKO_PLUSH, Items.IRON_INGOT, Items.IRON_INGOT);
        }

        //GUMI
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.GUMI_PLUSH)
                .pattern("121")
                .pattern(" 3 ")
                .define('1', Items.WOOL.lime())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.orange())
                .unlockedBy(getHasName(Items.WOOL.lime()), has(Items.WOOL.lime()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.orange()), has(Items.WOOL.orange()))
                .save(exporter);

            plushShapeless(exporter, ModBlocks.GUMI_PLUSH_V3, ModBlocks.GUMI_PLUSH, Items.IRON_INGOT);
            plushShapeless(exporter, ModBlocks.GUMI_PLUSH_V4, ModBlocks.GUMI_PLUSH, Items.IRON_INGOT, Items.IRON_INGOT);
            plushShapeless(exporter, ModBlocks.GUMI_PLUSH_V6, ModBlocks.GUMI_PLUSH, Items.IRON_INGOT, Items.IRON_INGOT, Items.REDSTONE);
        }

        //KAITO
        {
            shaped(RecipeCategory.DECORATIONS, ModBlocks.KAITO_PLUSH)
                .pattern("121")
                .pattern(" 3 ")
                .define('1', Items.WOOL.blue())
                .define('2', Items.WOOL.white())
                .define('3', Items.WOOL.orange())
                .unlockedBy(getHasName(Items.WOOL.blue()), has(Items.WOOL.blue()))
                .unlockedBy(getHasName(Items.WOOL.white()), has(Items.WOOL.white()))
                .unlockedBy(getHasName(Items.WOOL.orange()), has(Items.WOOL.orange()))
                .save(exporter);
            plushShapeless(exporter, ModBlocks.KAITO_PLUSH_V3, ModBlocks.KAITO_PLUSH, Items.IRON_INGOT);
            plushShapeless(exporter, ModBlocks.KAITO_PLUSH_V4, ModBlocks.KAITO_PLUSH, Items.IRON_INGOT, Items.IRON_INGOT);
        }

    }

    public void pickaxeRecipe (RecipeOutput exporter, ItemLike result, ItemLike ingredient) {
        shaped(RecipeCategory.TOOLS, result)
            .pattern("121")
            .define('1', Items.DIAMOND)
            .define('2', ingredient)
            .unlockedBy(getHasName(ingredient), has(ingredient))
            .save(exporter);
    }

    public void plushShapeless(RecipeOutput exporter, ItemLike result, ItemLike plush, ItemLike... ingredients) {
        //CREATE SHAPELESS RECIPE WITH A PLUSH
        ShapelessRecipeBuilder shapeless =
            shapeless(RecipeCategory.DECORATIONS, result)
            .requires(plush);

        //ADD EXTRA INGREDIENTS AS NEEDED
        for (ItemLike ingredient : ingredients) {
            shapeless.requires(ingredient);
        }

        //ADD RECIPE UNLOCK REQUIREMENT AND EXPORT RECIPE
        shapeless.unlockedBy(getHasName(plush), has(plush))
        .save(exporter);
    }

    public void simpleShapeless(RecipeOutput exporter, ItemLike result, ItemLike... ingredients) {
        //CREATE SHAPELESS RECIPE WITH A PLUSH
        ShapelessRecipeBuilder shapeless =
            shapeless(RecipeCategory.DECORATIONS, result);

        //ADD EXTRA INGREDIENTS AS NEEDED
        for (ItemLike ingredient : ingredients) {
            shapeless.requires(ingredient);
            shapeless.unlockedBy(getHasName(ingredient), has(ingredient));
        }

        //ADD RECIPE UNLOCK REQUIREMENT AND EXPORT RECIPE
        shapeless.save(exporter);
    }
}
}
