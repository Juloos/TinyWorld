package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BambooStalkBlock.class)
public class BambooStalkBlockMixin extends Block {
    @Shadow @Final public static IntegerProperty AGE;
    @Shadow @Final protected static VoxelShape COLLISION_SHAPE;
    @Unique private static final VoxelShape[] COLLISION_SHAPES = new VoxelShape[]{
            Block.box(7, 0, 7, 9, 16, 9),  // Small
            COLLISION_SHAPE,  // Large
    };

    public BambooStalkBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        Vec3 vec3 = blockState.getOffset(blockGetter, blockPos);
        return COLLISION_SHAPES[blockState.getValue(AGE)].move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return getShape(blockState, blockGetter, blockPos, collisionContext);
    }
}
