package ca.fxco.TinyWorld.mixin.scale;

import ca.fxco.TinyWorld.bridge.BlockParticleOptionBridge;
import net.minecraft.core.particles.BlockParticleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockParticleOption.class)
public class BlockParticleOptionMixin implements BlockParticleOptionBridge {
    @Unique
    protected double scale;

    @Override
    public void tiny$setScale(double f) {
        scale = f;
    }

    @Override
    public double tiny$getScale() {
        return scale;
    }
}
