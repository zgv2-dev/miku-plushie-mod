package com.any.mikuplushie;

import com.any.mikuplushie.entity.AbstractPlushEntity;
import com.any.mikuplushie.entity.client.render.*;
import com.any.mikuplushie.particle.PlushSpawnParticle;
import com.any.mikuplushie.registry.ModBlocks;
import com.any.mikuplushie.registry.ModEntities;
import com.any.mikuplushie.registry.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.world.entity.EntityType;
import com.geckolib.loading.math.MathParser;

@Environment(EnvType.CLIENT)
public class MikuPlushieClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        // 26.x picks block render layers automatically from texture transparency.

        //GLIB QUERY
        MathParser.setVariable("query.miku.is_game", () -> 90 / Math.PI);

        //ENTITIES RENDERERS
        for (EntityType<? extends AbstractPlushEntity> plushEntity : ModEntities.PLUSH_ENTITIES) {
            EntityRendererRegistry.register(plushEntity, AbstractPlushRender::new);
        }

        //PARTICLE
        ParticleProviderRegistry.getInstance().register(ModParticles.MIKU_SPAWN, PlushSpawnParticle.Factory::new);
	}
}
