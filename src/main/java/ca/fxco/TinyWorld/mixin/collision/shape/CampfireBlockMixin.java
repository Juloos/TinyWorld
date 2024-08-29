package ca.fxco.TinyWorld.mixin.collision.shape;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin extends BaseEntityBlock {

    @Unique private static final VoxelShape Z_SHAPE;
    @Unique private static final VoxelShape X_SHAPE;

    public CampfireBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return switch (blockState.getValue(CampfireBlock.FACING)) {
            case NORTH, SOUTH -> Z_SHAPE;
            case EAST, WEST -> X_SHAPE;
            default -> Shapes.empty();
        };
    }

    static {
        VoxelShape logShapeZ = box(1, 0, 0, 5, 4, 16);
        VoxelShape logShapeX = box(0, 0, 1, 16, 4, 5);
        logShapeZ = Shapes.or(logShapeZ, logShapeZ.move(10 / 16d, 0, 0));
        logShapeX = Shapes.or(logShapeX, logShapeX.move(0, 0, 10 / 16d));
        Z_SHAPE = Shapes.or(box(1, 0, 0, 15, 1, 16), logShapeZ, logShapeX.move(0, 3 / 16d, 0));
        X_SHAPE = Shapes.or(box(0, 0, 1, 16, 1, 15), logShapeX, logShapeZ.move(0, 3 / 16d, 0));
    }
}
