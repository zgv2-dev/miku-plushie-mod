package com.any.mikuplushie.datagen;

import com.any.mikuplushie.MikuPlushie;
import com.any.mikuplushie.util.ModUtil;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.Property;
import java.util.Optional;

public class BlockModels {

    private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(Optional.of(Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    //PARENT MODELS
    public static final ModelTemplate LEEK_MODEL = block("leek", TextureSlot.CROSS);

    //CROP BLOCKSTATE GEN
    public static void registerCrop(BlockModelGenerators gen, Block crop, Property<Integer> ageProperty, int... ageTextureIndices) {
        if (ageProperty.getPossibleValues().size() != ageTextureIndices.length) {
            throw new IllegalArgumentException();
        }
        Int2ObjectMap<Identifier> models = new Int2ObjectOpenHashMap<>();
        PropertyDispatch<net.minecraft.client.data.models.MultiVariant> dispatch = PropertyDispatch.initial(ageProperty).generate(age -> {
            int stage = ageTextureIndices[age];
            Identifier model = models.computeIfAbsent(stage, j -> gen.createSuffixedVariant(crop, "_stage" + stage, LEEK_MODEL, TextureMapping::cross));
            return BlockModelGenerators.plainVariant(model);
        });
        gen.blockStateOutput.accept(MultiVariantGenerator.dispatch(crop).with(dispatch));
    }

    //WILD CROP BLOCKSTATE GEN
    public static void registerWildCrop(BlockModelGenerators gen, Block block, Block tamedBlock) {
        TextureMapping textureMap = TextureMapping.cross(new Material(
            Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "block/" + ModUtil.getBlockIdFromBlock(tamedBlock) + "_stage" + CropBlock.MAX_AGE)
        ));
        gen.createCrossBlock(block, BlockModelGenerators.PlantType.NOT_TINTED, textureMap);
    }
}
