package ca.fxco.TinyWorld.mixin.collision.shape;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.*;

import static net.minecraft.world.level.block.FenceGateBlock.*;

@Mixin(FenceGateBlock.class)
public class FenceGateBlockMixin extends HorizontalDirectionalBlock {

    public FenceGateBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Unique private static final VoxelShape[] staticShapesByIndex = new VoxelShape[16];

    static {
        VoxelShape northGateWindShape = Shapes.or(
                box(0, 5, 7, 2, 16, 9),
                Shapes.join(box(0, 6, 7, 8, 15, 9), box(2, 9, 7, 6, 12, 9), BooleanOp.ONLY_FIRST)
        );
        VoxelShape southGateWindShape = Shapes.or(
                box(14, 5, 7, 16, 16, 9),
                Shapes.join(box(8, 6, 7, 16, 15, 9), box(10, 9, 7, 14, 12, 9), BooleanOp.ONLY_FIRST)
        );
        VoxelShape westGateWindShape = Shapes.or(
                box(7, 5, 0, 9, 16, 2),
                Shapes.join(box(7, 6, 0, 9, 15, 8), box(7, 9, 2, 9, 12, 6), BooleanOp.ONLY_FIRST)
        );
        VoxelShape eastGateWindShape = Shapes.or(
                box(7, 5, 14, 9, 16, 16),
                Shapes.join(box(7, 6, 8, 9, 15, 16), box(7, 9, 10, 9, 12, 14), BooleanOp.ONLY_FIRST)
        );

        staticShapesByIndex[0] = Shapes.or(northGateWindShape, southGateWindShape);  // Z
        staticShapesByIndex[1] = Shapes.or(westGateWindShape, eastGateWindShape);  // X
        staticShapesByIndex[2] = staticShapesByIndex[0].move(0, -3/16d, 0);  // Z + low
        staticShapesByIndex[3] = staticShapesByIndex[1].move(0, -3/16d, 0);  // X + low
        staticShapesByIndex[4] = Shapes.or(eastGateWindShape.move(-7/16d, 0, -7/16d), eastGateWindShape.move(7/16d, 0, -7/16d));  // North
        staticShapesByIndex[5] = Shapes.or(southGateWindShape.move(-7/16d, 0, -7/16d), southGateWindShape.move(-7/16d, 0, 7/16d));  // West
        staticShapesByIndex[6] = staticShapesByIndex[4].move(0, -3/16d, 0);  // North + low
        staticShapesByIndex[7] = staticShapesByIndex[5].move(0, -3/16d, 0);  // West + low

        staticShapesByIndex[12] = Shapes.or(westGateWindShape.move(-7/16d, 0, 7/16d), westGateWindShape.move(7/16d, 0, 7/16d));  // South
        staticShapesByIndex[13] = Shapes.or(northGateWindShape.move(7/16d, 0, -7/16d), northGateWindShape.move(7/16d, 0, 7/16d));  // East
        staticShapesByIndex[14] = staticShapesByIndex[12].move(0, -3/16d, 0);  // South + low
        staticShapesByIndex[15] = staticShapesByIndex[13].move(0, -3/16d, 0);  // East + low
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return getShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        int i = (blockState.getValue(FACING).getAxis() == Direction.Axis.X) ? 1 : 0;
        i += (blockState.getValue(IN_WALL)) ? 2 : 0;
        i += (blockState.getValue(OPEN)) ? 4 : 0;
        i += (blockState.getValue(OPEN) && blockState.getValue(FACING).getAxisDirection() == Direction.AxisDirection.POSITIVE) ? 8 : 0;
        return staticShapesByIndex[i];
    }
}
