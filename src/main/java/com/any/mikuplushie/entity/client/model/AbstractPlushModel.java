package com.any.mikuplushie.entity.client.model;

import com.any.mikuplushie.MikuPlushie;
import com.any.mikuplushie.entity.AbstractPlushEntity;
import com.any.mikuplushie.registry.ModBlocks;
import com.any.mikuplushie.util.ModUtil;
import net.minecraft.resources.Identifier;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class AbstractPlushModel extends GeoModel<AbstractPlushEntity> {

    // GeckoLib 5: entity is not available at render time, so copy what rendering needs into the render state.
    public static final DataTicket<String> PLUSH_NAME = DataTicket.create("miku_plush_name", String.class);
    public static final DataTicket<String> VARIANT = DataTicket.create("miku_plush_variant", String.class);
    public static final DataTicket<Float> HEALTH_FACTOR = DataTicket.create("miku_plush_health_factor", Float.class);
    public static final DataTicket<Boolean> SONG_PLAYING = DataTicket.create("miku_plush_song_playing", Boolean.class);
    public static final DataTicket<Boolean> SWINGING = DataTicket.create("miku_plush_swinging", Boolean.class);

    // resolved by GeckoLib 5 to assets/miku-plushie/geckolib/animations/plush.animation.json
    private final Identifier animations = Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "plush");

    @Override
    public void addAdditionalStateData(AbstractPlushEntity animatable, Object relatedObject, GeoRenderState renderState) {
        super.addAdditionalStateData(animatable, relatedObject, renderState);
        renderState.addGeckolibData(PLUSH_NAME, animatable.getPlushName());
        renderState.addGeckolibData(VARIANT, animatable.getVariant());
        renderState.addGeckolibData(HEALTH_FACTOR, animatable.getHealth() / animatable.getMaxHealth());
        renderState.addGeckolibData(SONG_PLAYING, animatable.isSongPlaying());
        renderState.addGeckolibData(SWINGING, animatable.isSwinging());
    }


    @Override
    public Identifier getModelResource(GeoRenderState renderState) {

        String entity = renderState.getGeckolibData(PLUSH_NAME);
        String variant = renderState.getGeckolibData(VARIANT);

        if (variant.equals(entity)){
            return Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "entity/" + entity);
        }
        //VARIANTS THAT USE THE 2ND MODEL
        else if (
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_MUSHROOM)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_WEREWOMAN)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_PATATI)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_PATATA)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_DEVIL)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_WITCH))) {
            return Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "entity/" + entity + "_2");
        }
        //VARIANTS THAT USE THE 3RD MODEL
        else if (
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_XMAS_TREE)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_SONIC)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_DIGITAL_STARS_2025)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_ROTTEN_GIRL)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_PSYCHO_MODE)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_DONT_BELIEVE_IN_T)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_STATIC)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_MOCHIMOCHI)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_MONITORING)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_HOLLOW_KNIGHT)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_HORNET)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_LUCARIO_Z))||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_PPPP))
        ) {
            return Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "entity/" + entity + "_3");
        }
        return Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID, "entity/" + entity);
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(MikuPlushie.MOD_ID,
            "textures/block/" + renderState.getGeckolibData(VARIANT).replace('_', '-') + ".png");
    }

    @Override
    public Identifier getAnimationResource(AbstractPlushEntity animatable) {
        return animations;
    }


}
