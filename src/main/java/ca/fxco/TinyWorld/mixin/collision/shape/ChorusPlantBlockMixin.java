package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.ChorusPlantBlock;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChorusPlantBlock.class)
public abstract class ChorusPlantBlockMixin extends PipeBlock {
    public ChorusPlantBlockMixin(float f, Properties properties) {
        super(f, properties);
    }

    @Unique private VoxelShape shapeCache = null;

    @Unique
    private void updateShapeCache(BlockState blockState, BlockPos blockPos) {
        shapeCache = box(4, 4, 4, 12, 12, 12);
        // four choices, but one has a weight of 2, and two are identical
        // Multipart uses a random long from the block's seed as another seed for the random of each weighted baked model it has
        int choice = Math.abs((int) RandomSource.create(RandomSource.create(blockState.getSeed(blockPos)).nextLong()).nextLong()) % 5;

        // reference for understanding the magic numbers: assets/minecraft/blockstates/chorus_plant.json
        if (blockState.getValue(DOWN))
            shapeCache = Shapes.or(shapeCache, box(4, 0, 4, 12, 4, 12));  // Down connection
        else if (choice < 3) {
            if (choice == 1)
                shapeCache = Shapes.or(shapeCache, box(5, 2, 5, 11, 4, 11));  // Long down
            else
                shapeCache = Shapes.or(shapeCache, box(4, 3, 4, 12, 4, 12));  // Large down
        }

        if (blockState.getValue(UP))
            shapeCache = Shapes.or(shapeCache, box(4, 12, 4, 12, 16, 12));  // Up connection
        else if (choice > 1) {
            if (choice == 4)
                shapeCache = Shapes.or(shapeCache, box(5, 12, 5, 11, 14, 11));  // Long (protuberance) up
            else
                shapeCache = Shapes.or(shapeCache, box(4, 12, 4, 12, 13, 12));  // Large up
        }

        if (blockState.getValue(NORTH))
            shapeCache = Shapes.or(shapeCache, box(4, 4, 0, 12, 12, 4));  // North connection
        else if (choice > 1) {
            if (choice == 3)
                shapeCache = Shapes.or(shapeCache, box(5, 5, 2, 11, 11, 4));  // Long north
            else
                shapeCache = Shapes.or(shapeCache, box(4, 4, 3, 12, 12, 4));  // Large north
        }

        if (blockState.getValue(SOUTH))
            shapeCache = Shapes.or(shapeCache, box(4, 4, 12, 12, 12, 16));  // South connection
        else if (choice != 2 && choice != 3) {
            if (choice == 0)
                shapeCache = Shapes.or(shapeCache, box(5, 5, 12, 11, 11, 14));  // Long south
            else
                shapeCache = Shapes.or(shapeCache, box(4, 4, 12, 12, 12, 13));  // Large south
        }

        if (blockState.getValue(WEST))
            shapeCache = Shapes.or(shapeCache, box(0, 4, 4, 4, 12, 12));  // West connection
        else if (choice != 1 && choice != 2) {
            if (choice == 4)
                shapeCache = Shapes.or(shapeCache, box(2, 5, 5, 4, 11, 11));  // Long west
            else
                shapeCache = Shapes.or(shapeCache, box(3, 4, 4, 4, 12, 12));  // Large west
        }

        if (blockState.getValue(EAST))
            shapeCache = Shapes.or(shapeCache, box(12, 4, 4, 16, 12, 12));  // East connection
        else if (choice < 3) {
            if (choice == 1)
                shapeCache = Shapes.or(shapeCache, box(12, 5, 5, 14, 11, 11));  // Long east
            else
                shapeCache = Shapes.or(shapeCache, box(12, 4, 4, 13, 12, 12));  // Large east
        }
    }

    @Inject(
            method = "updateShape",
            at = @At("HEAD")
    )
    private void tiny$updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2, CallbackInfoReturnable<BlockState> cir) {
        shapeCache = null;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (shapeCache == null)
            updateShapeCache(blockState, blockPos);
        return shapeCache;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (shapeCache == null)
            updateShapeCache(blockState, blockPos);
        return shapeCache;
    }
}
