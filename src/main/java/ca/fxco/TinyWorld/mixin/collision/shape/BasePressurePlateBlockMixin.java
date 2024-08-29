package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BasePressurePlateBlock.class)
public abstract class BasePressurePlateBlockMixin extends Block {

    public BasePressurePlateBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void tiny$hasCollision(CallbackInfo ci) {
        this.hasCollision = true;
    }
}
