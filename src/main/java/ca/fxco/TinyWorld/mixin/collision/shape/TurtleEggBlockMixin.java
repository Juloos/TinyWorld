package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TurtleEggBlock.class)
public class TurtleEggBlockMixin extends Block {

    @Unique private static final VoxelShape[] AABBS = new VoxelShape[16];

    public TurtleEggBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return AABBS[4 * Math.abs((int) RandomSource.create(blockState.getSeed(blockPos)).nextLong() % 4) + blockState.getValue(TurtleEggBlock.EGGS) - 1];
    }

    static {
        VoxelShape egg1 = box(  6, 0,   6,  10, 7,  10);
        VoxelShape egg2 = box(  6, 0,   6,  10, 5,  10);
        VoxelShape egg3 = box(6.5, 0, 6.5, 9.5, 4, 9.5);
        VoxelShape egg4 = box(  6, 0,   6,  10, 4,  10);
        for (int i = 0; i < 16; i += 4) {
            double s = (i < 8 ? 1 : -1) / 16d;
            if ((i / 4) % 2 == 0) {
                AABBS[i    ] =                         egg1.move(s *   -1, 0, s *  -2) ;
                AABBS[i + 1] = Shapes.or(AABBS[i    ], egg2.move(s *   -5, 0, s *   1));
                AABBS[i + 2] = Shapes.or(AABBS[i + 1], egg3.move(s *  4.5, 0, s * 0.5));
                AABBS[i + 3] = Shapes.or(AABBS[i + 2], egg4.move(       0, 0, s *   3));
            } else {
                AABBS[i    ] =                         egg1.move(s *    2, 0, s *  -1) ;
                AABBS[i + 1] = Shapes.or(AABBS[i    ], egg2.move(s *   -1, 0, s *  -5));
                AABBS[i + 2] = Shapes.or(AABBS[i + 1], egg3.move(s * -0.5, 0, s * 4.5));
                AABBS[i + 3] = Shapes.or(AABBS[i + 2], egg4.move(s *   -3, 0,       0));
                }
        }
    }
}
