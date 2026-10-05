package com.any.mikuplushie.item;

import com.any.mikuplushie.MikuPlushie;
import com.any.mikuplushie.datagen.ModItemTagProvider;
import com.any.mikuplushie.registry.ModBlocks;
import com.any.mikuplushie.util.ModUtil;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.component.TooltipDisplay;
import java.util.function.Consumer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class MikuPlushieBlockItem extends BlockItem {

	public MikuPlushieBlockItem(Block block, Properties settings) {
		super(block, settings);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
		tooltip.accept(Component.translatable("item." + MikuPlushie.MOD_ID + "." + net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()) + ".tooltip"));
		super.appendHoverText(stack, context, display, tooltip, type);
	}

	public static void PlayMikuSound(LivingEntity entity){
		ItemStack stack = entity.getItemInHand(entity.getUsedItemHand());

		if (stack.is(ModItemTagProvider.PLUSHIES)){
			String currentPlush = ModUtil.getBlockIdFromItem(stack.getItem());
			ModUtil.playPlushSound(entity.level(), entity.blockPosition(), currentPlush, "dor");
		}

	}

}
