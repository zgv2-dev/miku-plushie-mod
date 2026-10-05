package com.any.mikuplushie.entity.client.render;

import com.any.mikuplushie.entity.AbstractPlushEntity;
import com.any.mikuplushie.entity.client.model.AbstractPlushModel;
import com.any.mikuplushie.entity.client.model.animations.PlushAnimations;
import com.any.mikuplushie.registry.ModBlocks;
import com.any.mikuplushie.util.ModUtil;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.builtin.ItemInHandGeoLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class AbstractPlushRender<R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<AbstractPlushEntity, R> {

    public static final String LEFT_HAND = "left_hand";
    public static final String RIGHT_HAND = "right_hand";

    public AbstractPlushRender(EntityRendererProvider.Context context) {
        super(context, new AbstractPlushModel());
        // held item rendering
        withRenderLayer(new ItemInHandGeoLayer<>(context, this, RIGHT_HAND, LEFT_HAND));
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        //USE TRANSLUCENT RENDER ON SPECIFIC VARIATION
        String variant = renderState.getOrDefaultGeckolibData(AbstractPlushModel.VARIANT, "");
        if (
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.MIKU_PLUSH_GHOST)) ||
            variant.equals(ModUtil.getBlockIdFromBlock(ModBlocks.TETO_PLUSH_WHATCHACALLITSNAME))
        ){
            return RenderTypes.entityTranslucent(texture);
        }
        return super.getRenderType(renderState, texture);
    }

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
        PlushAnimations.apply(renderPassInfo.renderState(), snapshots);
    }
}
