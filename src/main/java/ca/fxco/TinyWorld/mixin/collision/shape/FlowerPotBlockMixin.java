package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FlowerPotBlock.class)
public class FlowerPotBlockMixin extends Block {
    @Shadow @Final private Block potted;

    @Shadow protected static final VoxelShape SHAPE = Shapes.join(
            box(5, 0, 5, 11, 6, 11),
            box(6, 4, 6, 10, 6, 10),
            BooleanOp.ONLY_FIRST
    );
    @Unique private static final VoxelShape POTTED_CACTUS_SHAPE = Shapes.or(
            box(5, 0, 5, 11, 6, 11),
            box(6, 6, 6, 10, 16, 10)
    );
    @Unique private static final VoxelShape POTTED_BAMBOO_SHAPE = Shapes.or(
            SHAPE,
            box(7, 4, 7, 9, 16, 9)
    );
    @Unique private static final VoxelShape POTTED_AZALEA_SHAPE = Shapes.or(
            POTTED_BAMBOO_SHAPE,
            box(4, 10, 4, 12, 16, 12)
    );

    public FlowerPotBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (potted == Blocks.CACTUS)
            return POTTED_CACTUS_SHAPE;
        if (potted == Blocks.BAMBOO)
            return POTTED_BAMBOO_SHAPE;
        if (potted == Blocks.AZALEA || potted == Blocks.FLOWERING_AZALEA)
            return POTTED_AZALEA_SHAPE;
        return SHAPE;
    }
}
