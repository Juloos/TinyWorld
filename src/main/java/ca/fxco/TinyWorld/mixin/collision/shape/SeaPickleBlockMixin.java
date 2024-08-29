package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.*;

@Mixin(SeaPickleBlock.class)
public abstract class SeaPickleBlockMixin extends BushBlock {
    @Shadow @Final protected static VoxelShape ONE_AABB;
    @Unique private static final VoxelShape[] AABBS = new VoxelShape[16];

    public SeaPickleBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        int pickles = blockState.getValue(SeaPickleBlock.PICKLES) - 1;
        if (pickles == 0)
            return ONE_AABB;
        return AABBS[4 * Math.abs((int) RandomSource.create(blockState.getSeed(blockPos)).nextLong() % 4) + pickles];
    }

    static {
        VoxelShape pickle1 = ONE_AABB;

        VoxelShape pickle2 = box(8, 0, 8, 12, 4, 12);
        AABBS[1] = Shapes.or(pickle1.move(-3 / 16d, 0, -3 / 16d), pickle2);
        AABBS[2] = Shapes.or(pickle1.move( 2 / 16d, 0, -2 / 16d), pickle1.move(       0, 0,  3 / 16d), pickle2.move(-6 / 16d, 0, -6 / 16d));
        AABBS[3] = Shapes.or(pickle1.move( 3 / 16d, 0, -4 / 16d), pickle1.move(-4 / 16d, 0, -4 / 16d), pickle2.move( 1 / 16d, 0,  2 / 16d), box(2, 0, 8, 6, 7, 12));

        pickle2 = box(4, 0, 8, 8, 4, 12);
        AABBS[5] = Shapes.or(pickle1.move( 3 / 16d, 0, -3 / 16d), pickle2);
        AABBS[6] = Shapes.or(pickle1.move( 2 / 16d, 0,  2 / 16d), pickle1.move(-3 / 16d, 0,        0), pickle2.move( 6 / 16d, 0, -6 / 16d));
        AABBS[7] = Shapes.or(pickle1.move( 4 / 16d, 0,  3 / 16d), pickle1.move( 4 / 16d, 0, -4 / 16d), pickle2.move(-2 / 16d, 0,  1 / 16d), box(4, 0, 2, 8, 7, 6));

        pickle2 = box(4, 0, 4, 8, 4, 8);
        AABBS[ 9] = Shapes.or(pickle1.move( 3 / 16d, 0,  3 / 16d), pickle2);
        AABBS[10] = Shapes.or(pickle1.move(-2 / 16d, 0,  2 / 16d), pickle1.move(       0, 0, -3 / 16d), pickle2.move( 6 / 16d, 0,  6 / 16d));
        AABBS[11] = Shapes.or(pickle1.move(-3 / 16d, 0,  4 / 16d), pickle1.move( 4 / 16d, 0,  4 / 16d), pickle2.move(-1 / 16d, 0, -2 / 16d), box(10, 0, 4, 14, 7, 8));

        pickle2 = box(8, 0, 4, 12, 4, 8);
        AABBS[13] = Shapes.or(pickle1.move(-3 / 16d, 0,  3 / 16d), pickle2);
        AABBS[14] = Shapes.or(pickle1.move(-2 / 16d, 0, -2 / 16d), pickle1.move( 3 / 16d, 0,        0), pickle2.move(-6 / 16d, 0,  6 / 16d));
        AABBS[15] = Shapes.or(pickle1.move(-4 / 16d, 0, -3 / 16d), pickle1.move(-4 / 16d, 0,  4 / 16d), pickle2.move( 2 / 16d, 0, -1 / 16d), box(8, 0, 10, 12, 7, 14));
    }
}
