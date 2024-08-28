package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(WallBlock.class)
public class WallBlockMixin {
    @Shadow @Final private Map<BlockState, VoxelShape> shapeByIndex;

    @Redirect(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/WallBlock;makeShapes(FFFFFF)Ljava/util/Map;",
                    ordinal = 1
            )
    )
    public Map<BlockState, VoxelShape> tiny$rescaleHeight(WallBlock instance, float f, float g, float h, float i, float j, float k) {
        return this.shapeByIndex;
    }
}
