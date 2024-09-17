package ca.fxco.TinyWorld.mixin.scale;

import ca.fxco.TinyWorld.bridge.BlockParticleOptionBridge;
import ca.fxco.TinyWorld.bridge.TerrainParticleBridge;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TerrainParticle.class)
public abstract class TerrainParticleMixin extends TextureSheetParticle implements TerrainParticleBridge {

    protected TerrainParticleMixin(ClientLevel clientLevel, double d, double e, double f) {
        super(clientLevel, d, e, f);
    }

    @Override
    public void tiny$rescale(double f) {
        quadSize *= (float) f;
        gravity *= (float) f;
        xd *= f;
        yd *= f;
        zd *= f;
    }

    @Redirect(
            method = "createTerrainParticle",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/client/particle/TerrainParticle"
            )
    )
    private static TerrainParticle tiny$injectCreateTerrainParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, BlockState blockState, BlockParticleOption blockParticleOption) {
        TerrainParticle terrainParticle = new TerrainParticle(clientLevel, d, e, f, g, h, i, blockState);
        ((TerrainParticleBridge) terrainParticle).tiny$rescale(((BlockParticleOptionBridge) blockParticleOption).tiny$getScale());
        return terrainParticle;
    }
}
