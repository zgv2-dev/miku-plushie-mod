package com.any.mikuplushie.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ToolMaterial;

public class PlushToolMaterial {
    // incorrect-blocks tag, durability, speed, attack bonus, enchantability, repair items
    public static final ToolMaterial PLUSH_TOOL_MATERIAL = new ToolMaterial(
        BlockTags.INCORRECT_FOR_WOODEN_TOOL, 500, 15, 0, 25, ItemTags.DIAMOND_TOOL_MATERIALS);
}
