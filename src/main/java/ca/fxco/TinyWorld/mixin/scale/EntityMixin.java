package ca.fxco.TinyWorld.mixin.scale;

import ca.fxco.TinyWorld.bridge.BlockParticleOptionBridge;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract boolean isAlive();

    @Shadow public abstract Vec3 getDeltaMovement();

    @Shadow protected abstract float getBlockSpeedFactor();

    @Shadow public abstract double getX();

    @Shadow public abstract double getZ();

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

//    @Inject(
//            method = "moveRelative",
//            at = @At("HEAD")
//    )
//    private void tiny$moveRelative1(float f, Vec3 vec3, CallbackInfo ci) {
//        System.out.print(getDeltaMovement() + " -> ");
//    }
//
//    @Inject(
//            method = "moveRelative",
//            at = @At("RETURN")
//    )
//    private void tiny$moveRelative2(float f, Vec3 vec3, CallbackInfo ci) {
//        System.out.print(getDeltaMovement() + "\n");
//    }

//    @Inject(
//            method = "setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V",
//            at = @At("HEAD")
//    )
//    private void test(Vec3 vec3, CallbackInfo ci) {
//        if (vec3.x == 0 && Math.abs(vec3.z) > 0)
//            Thread.dumpStack();
//    }
}
