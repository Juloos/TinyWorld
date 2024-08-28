package ca.fxco.TinyWorld.mixin.collision.shape;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(WallSignBlock.class)
public abstract class WallSignBlockMixin extends SignBlock {

    @Mutable @Shadow @Final public static Map<Direction, VoxelShape> AABBS;

    public WallSignBlockMixin(WoodType woodType, Properties properties) {
        super(woodType, properties);
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void tiny$hasCollision(CallbackInfo ci) {
        this.hasCollision = true;
    }

    static {
        double ss = 2 / 3d;  // Sign scale
        AABBS = Maps.newEnumMap(ImmutableMap.of(
                Direction.NORTH, box(0, 16 - 17.5 * ss, 15 - ss, 16, 16 - 5.5 * ss, 15 + ss),
                Direction.SOUTH, box(0, 16 - 17.5 * ss, 1 - ss, 16, 16 - 5.5 * ss, 1 + ss),
                Direction.EAST, box(1 - ss, 16 - 17.5 * ss, 0, 1 + ss, 16 - 5.5 * ss, 16),
                Direction.WEST, box(15 - ss, 16 - 17.5 * ss, 0, 15 + ss, 16 - 5.5 * ss, 16)
        ));
    }
}
