package ca.fxco.TinyWorld.mixin.collision.shape;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FenceBlock.class)
public class FenceBlockMixin extends CrossCollisionBlock {
    @Unique private static final VoxelShape[] staticCollisionShapeByIndex;

    public FenceBlockMixin(float f, float g, float h, float i, float j, Properties properties) {
        super(f, g, h, i, j, properties);
    }

    public MapCodec<? extends CrossCollisionBlock> codec() {
        return null;
    }


    // For physical collisions
    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return staticCollisionShapeByIndex[this.getAABBIndex(blockState)];
    }

    // For the outline shape
    @Override
    public VoxelShape getVisualShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return staticCollisionShapeByIndex[this.getAABBIndex(blockState)];
    }

    // For the camera to get obstructed correctly
    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return staticCollisionShapeByIndex[this.getAABBIndex(blockState)];
    }

    static {
        staticCollisionShapeByIndex = new VoxelShape[16];
        VoxelShape postShape = box(6, 0, 6, 10, 16, 10);
        VoxelShape sideNorthShape = Shapes.or(box(7, 6, 10, 9, 9, 16), box(7, 12, 10, 9, 15, 16));
        VoxelShape sideEastShape = Shapes.or(box(0, 6, 7, 6, 9, 9), box(0, 12, 7, 6, 15, 9));
        VoxelShape sideSouthShape = Shapes.or(box(7, 6, 0, 9, 9, 6), box(7, 12, 0, 9, 15, 6));
        VoxelShape sideWestShape = Shapes.or(box(10, 6, 7, 16, 9, 9), box(10, 12, 7, 16, 15, 9));

        for (int i = 0; i < 16; i++) {
            VoxelShape shape = postShape;
            if ((i & 1) != 0)
                shape = Shapes.or(shape, sideNorthShape);
            if ((i & 2) != 0)
                shape = Shapes.or(shape, sideEastShape);
            if ((i & 4) != 0)
                shape = Shapes.or(shape, sideSouthShape);
            if ((i & 8) != 0)
                shape = Shapes.or(shape, sideWestShape);
            staticCollisionShapeByIndex[i] = shape;
        }
    }
}
