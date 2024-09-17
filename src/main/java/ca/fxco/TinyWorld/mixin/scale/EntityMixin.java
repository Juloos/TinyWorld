package ca.fxco.TinyWorld.mixin.scale;

import ca.fxco.TinyWorld.bridge.BlockParticleOptionBridge;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract boolean isAlive();

    @Redirect(
            method = "spawnSprintParticle",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/core/particles/BlockParticleOption"
            )
    )
    private BlockParticleOption tiny$rescaleTerrainParticles(ParticleType<BlockParticleOption> particleType, BlockState blockState) {
        BlockParticleOption blockParticleOption = new BlockParticleOption(particleType, blockState);
        if (isAlive())
            ((BlockParticleOptionBridge) blockParticleOption).tiny$setScale(
                    Objects.requireNonNull(((LivingEntity) (Object) this).getAttribute(Attributes.SCALE)).getValue()
            );
        return blockParticleOption;
    }

    @ModifyConstant(
            method = "spawnSprintParticle",
            constant = @Constant(doubleValue = 0.1)
    )
    private double tiny$modifyParticleScaleE(double e) {
        if (!isAlive())
            return e;
        return e * Objects.requireNonNull(((LivingEntity) (Object) this).getAttribute(Attributes.SCALE)).getValue();
    }

//    @ModifyConstant(
//            method = "spawnSprintParticle",
//            constant = @Constant(doubleValue = 1.5)
//    )
//    private double tiny$modifyParticleScaleH(double h) {
//        if (!isAlive())
//            return h;
//        return h * Objects.requireNonNull(((LivingEntity) (Object) this).getAttribute(Attributes.SCALE)).getValue();
//    }
}
