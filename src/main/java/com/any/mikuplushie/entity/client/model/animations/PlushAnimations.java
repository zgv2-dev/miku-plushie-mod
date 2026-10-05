package com.any.mikuplushie.entity.client.model.animations;

import com.any.mikuplushie.entity.client.model.AbstractPlushModel;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

// Procedural limb/head/hair motion layered on top of the JSON animations (GeckoLib 5 bone snapshots).
public class PlushAnimations {
    private static final float TO_RAD = (float) (Math.PI / 180);

    public static <R extends LivingEntityRenderState & GeoRenderState> void apply(R state, BoneSnapshots bones) {
        String name = state.getOrDefaultGeckolibData(AbstractPlushModel.PLUSH_NAME, "");
        if (name.contains("miku") || name.contains("teto") || name.contains("neru")) {
            hairMovement(state, bones);
        }
        limbAnimations(state, bones);
    }

    public static <R extends LivingEntityRenderState & GeoRenderState> void limbAnimations(R state, BoneSnapshots bones) {
        //LIMB ANIM VARIABLES
        float limbSwing = state.walkAnimationPos;
        float swingAmm = state.walkAnimationSpeed;
        float swingSpeed = 1F;

        //HEALTH DISPLAY
        float healthFactor = state.getOrDefaultGeckolibData(AbstractPlushModel.HEALTH_FACTOR, 1F);
        int bendAmount = 25;
        float healthBend = ((healthFactor) - 1) * bendAmount;

        //ROOT ANIMATION
        bones.ifPresent("root_offset", root -> {
            root.setRotZ((float) Math.sin(limbSwing * swingSpeed) * (swingAmm * 5 * TO_RAD));
            root.setTranslateY((float) Math.sin(limbSwing * swingSpeed * 2) * (swingAmm * 1) + (swingAmm * 1));
        });

        //DISABLE ARM ANIMATIONS WHEN DANCING AND ATTACKING
        boolean armsStill = state.getOrDefaultGeckolibData(AbstractPlushModel.SONG_PLAYING, false)
            || state.getOrDefaultGeckolibData(AbstractPlushModel.SWINGING, false);
        bones.ifPresent("left_arm_offset", arm -> arm.setRotX(armsStill ? 0 :
            (float) Math.sin(limbSwing * swingSpeed) * (swingAmm * 50 * TO_RAD) - (healthBend * TO_RAD)));
        bones.ifPresent("right_arm_offset", arm -> arm.setRotX(armsStill ? 0 :
            (float) Math.sin(limbSwing * swingSpeed) * (swingAmm * -50 * TO_RAD) - (healthBend * TO_RAD)));

        //LEGS ANIMATION
        bones.ifPresent("left_leg_offset", leg -> leg.setRotX((float) Math.sin(limbSwing * swingSpeed) * (swingAmm * -50 * TO_RAD)));
        bones.ifPresent("right_leg_offset", leg -> leg.setRotX((float) Math.sin(limbSwing * swingSpeed) * (swingAmm * 50 * TO_RAD)));

        //BODY ANIMATION
        bones.ifPresent("body_offset", body -> body.setRotX(healthBend * TO_RAD));

        //HEAD ANIM
        bones.ifPresent("head_offset", head -> {
            head.setRotX((state.xRot - healthBend) * TO_RAD);
            head.setRotY(state.yRot * TO_RAD);
        });
    }

    public static <R extends LivingEntityRenderState & GeoRenderState> void hairMovement(R state, BoneSnapshots bones) {
        float limbSwing = state.walkAnimationPos;
        float swingAmm = state.walkAnimationSpeed;
        float swingSpeed = 1F;

        bones.ifPresent("hair_offset", hair -> {
            hair.setRotX(-state.xRot * TO_RAD);
            hair.setRotZ((float) Math.sin(limbSwing * swingSpeed - (45/20F)) * (swingAmm * -10 * TO_RAD));
        });
    }
}
