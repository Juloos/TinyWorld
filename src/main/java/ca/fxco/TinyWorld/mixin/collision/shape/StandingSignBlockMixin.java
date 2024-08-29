package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StandingSignBlock.class)
public abstract class StandingSignBlockMixin extends BaseEntityBlock {
    @Shadow @Final public static IntegerProperty ROTATION;
    @Unique private static final VoxelShape[] SHAPES = new VoxelShape[16];

    public StandingSignBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void tiny$hasCollision(CallbackInfo ci) {
        this.hasCollision = true;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPES[blockState.getValue(ROTATION) % 8];
    }

    static {
        double angle = Math.PI / 8d;
        double ss1 = 2 / 3d;  // Sign scale
        double ss2 = ss1 * Math.sin(3 * angle) / Math.sin(2 * angle);
        double ss3 = ss1 * Math.sin(4 * angle) / Math.sin(2 * angle);

        VoxelShape postShape1 = box(8 - ss1, 0, 8 - ss1, 8 + ss1, 12 * ss1, 8 + ss1);
        VoxelShape postShape2 = box(8 - ss2, 0, 8 - ss2, 8 + ss2, 12 * ss1, 8 + ss2);
        VoxelShape postShape3 = box(8 - ss3, 0, 8 - ss3, 8 + ss3, 12 * ss1, 8 + ss3);

        SHAPES[0] = Shapes.or(postShape1, postShape1.move(0, 2 * ss1 / 16d, 0), box(0, 14 * ss1, 8 - ss1, 16, 26 * ss1, 8 + ss1));
        SHAPES[4] = Shapes.or(postShape1, postShape1.move(0, 2 * ss1 / 16d, 0), box(8 - ss1, 14 * ss1, 0, 8 + ss1, 26 * ss1, 16));

        SHAPES[1] = Shapes.or(postShape2, postShape2.move(0, 2 * ss1 / 16d, 0));
        SHAPES[2] = Shapes.or(postShape3, postShape3.move(0, 2 * ss1 / 16d, 0));
        SHAPES[3] = Shapes.or(postShape2, postShape2.move(0, 2 * ss1 / 16d, 0));

        SHAPES[5] = Shapes.or(postShape2, postShape2.move(0, 2 * ss1 / 16d, 0));
        SHAPES[6] = Shapes.or(postShape3, postShape3.move(0, 2 * ss1 / 16d, 0));
        SHAPES[7] = Shapes.or(postShape2, postShape2.move(0, 2 * ss1 / 16d, 0));

        for (int i = -11; i < 12; i += 2) {
            SHAPES[1] = Shapes.or(SHAPES[1], postShape2.move(i * Math.cos(1 * angle) * ss1 / 16d, 14 * ss1 / 16d, i * Math.sin(1 * angle) * ss1 / 16d));
            SHAPES[2] = Shapes.or(SHAPES[2], postShape3.move(i * Math.cos(2 * angle) * ss1 / 16d, 14 * ss1 / 16d, i * Math.sin(2 * angle) * ss1 / 16d));
            SHAPES[3] = Shapes.or(SHAPES[3], postShape2.move(i * Math.cos(3 * angle) * ss1 / 16d, 14 * ss1 / 16d, i * Math.sin(3 * angle) * ss1 / 16d));

            SHAPES[5] = Shapes.or(SHAPES[5], postShape2.move(i * Math.cos(5 * angle) * ss1 / 16d, 14 * ss1 / 16d, i * Math.sin(5 * angle) * ss1 / 16d));
            SHAPES[6] = Shapes.or(SHAPES[6], postShape3.move(i * Math.cos(6 * angle) * ss1 / 16d, 14 * ss1 / 16d, i * Math.sin(6 * angle) * ss1 / 16d));
            SHAPES[7] = Shapes.or(SHAPES[7], postShape2.move(i * Math.cos(7 * angle) * ss1 / 16d, 14 * ss1 / 16d, i * Math.sin(7 * angle) * ss1 / 16d));
        }
    }
}
