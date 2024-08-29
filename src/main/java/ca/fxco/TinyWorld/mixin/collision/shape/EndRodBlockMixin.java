package ca.fxco.TinyWorld.mixin.collision.shape;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.RodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EndRodBlock.class)
public class EndRodBlockMixin extends RodBlock {
    @Unique
    private static final VoxelShape NORTH_SHAPE;
    @Unique
    private static final VoxelShape SOUTH_SHAPE;
    @Unique
    private static final VoxelShape EAST_SHAPE;
    @Unique
    private static final VoxelShape WEST_SHAPE;
    @Unique
    private static final VoxelShape UP_SHAPE;
    @Unique
    private static final VoxelShape DOWN_SHAPE;

    public EndRodBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends RodBlock> codec() {
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            case UP -> UP_SHAPE;
            case DOWN -> DOWN_SHAPE;
        };
    }

    static {
        VoxelShape rodPostX = box(0, 7, 7, 16, 9, 9);
        VoxelShape rodPostY = box(7, 0, 7, 9, 16, 9);
        VoxelShape rodPostZ = box(7, 7, 0, 9, 9, 16);

        VoxelShape rodBaseX = box(0, 6, 6, 1, 10, 10);
        VoxelShape rodBaseY = box(6, 0, 6, 10, 1, 10);
        VoxelShape rodBaseZ = box(6, 6, 0, 10, 10, 1);

        SOUTH_SHAPE = Shapes.or(rodPostZ, rodBaseZ);
        NORTH_SHAPE = Shapes.or(rodPostZ, rodBaseZ.move(0, 0, 15 / 16d));
        EAST_SHAPE = Shapes.or(rodPostX, rodBaseX);
        WEST_SHAPE = Shapes.or(rodPostX, rodBaseX.move(15 / 16d, 0, 0));
        UP_SHAPE = Shapes.or(rodPostY, rodBaseY);
        DOWN_SHAPE = Shapes.or(rodPostY, rodBaseY.move(0, 15 / 16d, 0));
    }
}
