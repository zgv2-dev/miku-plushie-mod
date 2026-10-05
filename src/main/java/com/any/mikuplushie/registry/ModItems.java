package com.any.mikuplushie.registry;

import com.any.mikuplushie.MikuPlushie;
import com.any.mikuplushie.item.MikuPlushieBlockItem;
import com.any.mikuplushie.item.ModFoodComponents;
import com.any.mikuplushie.item.PlushToolMaterial;
import com.any.mikuplushie.util.ModUtil;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.equipment.Equippable;
import java.util.function.Function;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static List<Item> REGULAR_ITEMS = new ArrayList<>();
    public static List<Item> PLUSH_ITEMS = new ArrayList<>();
    public static List<Item> PICKAXE_ITEMS = new ArrayList<>();

    //CREATE ITEM GROUP
	public static final ResourceKey<CreativeModeTab> MIKU_GROUP_KEY =
        ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(),Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "item_group")
	);
	public static final CreativeModeTab MIKU_GROUP = FabricCreativeModeTab.builder()
		.icon(() -> new ItemStack(ModBlocks.MIKU_PLUSH))
		.title(Component.translatable("item.group.miku_plushies"))
		.build();


    //REGISTER REGULAR ITEMS
	public static final Item CANUDINHO =
        register("canudinho", Item::new, new Item.Properties().rarity(Rarity.RARE));
	public static final Item BAGUETTE =
        register("baguette", Item::new, new Item.Properties().food(ModFoodComponents.BAGUETTE));

    public static final Item LEEK_SEEDS =
        register("leek_seeds", p -> new BlockItem(ModBlocks.LEEK_CROP, p), new Item.Properties().useItemDescriptionPrefix());
    public static final Item LEEK =
        register("leek", Item::new, new Item.Properties().food(ModFoodComponents.LEEK));

    public static final Item AKITA_NERU_PHONE =
        register("akita_neru_phone", Item::new, new Item.Properties());

    public static final Item VOCALOID_HEART =
        register("vocaloid_heart", Item::new, new Item.Properties());

    //REGISTER TETO PICKAXE ITEMS
    public static final Item TETO_PICKAXE = registerPickaxe("teto_pickaxe");
    public static final Item TETO_PICKAXE_MESMERIZER = registerPickaxe("teto_pickaxe_mesmerizer");
    public static final Item TETO_PICKAXE_BIRDBRAIN = registerPickaxe("teto_pickaxe_birdbrain");
    public static final Item TETO_PICKAXE_REGRET_ROCK = registerPickaxe("teto_pickaxe_regret_rock");
    public static final Item TETO_PICKAXE_DONT_BELIEVE_IN_T = registerPickaxe("teto_pickaxe_dont_believe_in_t");
    public static final Item TETO_PICKAXE_LIAR_DANCER = registerPickaxe("teto_pickaxe_liar_dancer");
    public static final Item TETO_PICKAXE_WHATCHACALLITSNAME = registerPickaxe("teto_pickaxe_whatchacallitsname");
    public static final Item TETO_PICKAXE_SOME_MORE_OF_THAT_SONG = registerPickaxe("teto_pickaxe_some_more_of_that_song");
    public static final Item TETO_PICKAXE_SYNTHV = registerPickaxe("teto_pickaxe_synthv");
    public static final Item TETO_PICKAXE_SPOKEN_FOR = registerPickaxe("teto_pickaxe_spoken_for");
    public static final Item TETO_PICKAXE_PPPP = registerPickaxe("teto_pickaxe_pppp");

    //REGISTER PLUSH ITEMS
    public static Item registerPlush(String name) {
        Block plushBlock = null;
        for (int block = 0; block < ModBlocks.PLUSH_BLOCKS.size(); block++) {
            //MATCH ITEM TO THE RIGHT BLOCK
            Block registeredPlushBlock = ModBlocks.PLUSH_BLOCKS.get(block);
            if (ModUtil.getBlockIdFromBlock(registeredPlushBlock).matches(name)){
                plushBlock = registeredPlushBlock;
            }
        }
        final Block block = plushBlock;
        SoundEvent equipSound = name.equals("konoha_plush") ? SoundEvents.WOOL_PLACE : ModUtil.getPlushSoundEvent(name, "equip");
        return register(name, p -> new MikuPlushieBlockItem(block, p), new Item.Properties()
            .useBlockDescriptionPrefix()
            .component(net.minecraft.core.component.DataComponents.EQUIPPABLE,
                Equippable.builder(EquipmentSlot.HEAD).setEquipSound(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(equipSound)).build()));
    }

    //REGISTER PICKAXES HELPER
    public static Item registerPickaxe(String name) {
        return register(name, Item::new, new Item.Properties().pickaxe(PlushToolMaterial.PLUSH_TOOL_MATERIAL, 1f, -2.8F));
    }

    //REGISTER NORMAL ITEM
	public static Item register(String id, Function<Item.Properties, Item> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, id));
        Item item = factory.apply(properties.setId(key));
        Item register = Registry.register(BuiltInRegistries.ITEM, key, item);
        String itemName = ModUtil.getBlockIdFromItem(item);

        //ADD TETO PICKAXES TO THE PICKAXES LIST
        if (itemName.contains("pickaxe")){
            PICKAXE_ITEMS.add(item);
        }
        //ADD REGULAR ITEMS TOO
        else if (!itemName.contains("plush")){
            REGULAR_ITEMS.add(item);
        }

        return register;
	}


	public static void initialize() {
        MikuPlushie.LOGGER.info("Registering " + MikuPlushie.MOD_ID + " Items");

        //REGISTER ITEM GROUP
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, MIKU_GROUP_KEY, MIKU_GROUP);

        //CREATE A ITEM FOR EVERY PLUSH BLOCK
        for (Block plushblock : ModBlocks.PLUSH_BLOCKS) {
            Item plushItem = registerPlush(ModUtil.getBlockIdFromBlock(plushblock));
            PLUSH_ITEMS.add(plushItem);
        }

        //POPULATE ITEM GROUP
		CreativeModeTabEvents.modifyOutputEvent(MIKU_GROUP_KEY).register(itemGroup -> {

            REGULAR_ITEMS.forEach(itemGroup::accept);
            PLUSH_ITEMS.forEach(itemGroup::accept);
            PICKAXE_ITEMS.forEach(itemGroup::accept);

		});
	}
}
