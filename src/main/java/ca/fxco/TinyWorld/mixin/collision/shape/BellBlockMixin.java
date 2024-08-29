package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BellBlock.class)
public class BellBlockMixin {
    @Mutable @Shadow @Final private static VoxelShape NORTH_SOUTH_FLOOR_SHAPE;
    @Mutable @Shadow @Final private static VoxelShape EAST_WEST_FLOOR_SHAPE;
    @Shadow @Final private static VoxelShape NORTH_SOUTH_BETWEEN;
    @Shadow @Final private static VoxelShape EAST_WEST_BETWEEN;
    @Shadow @Final private static VoxelShape BELL_SHAPE;

    static {
        VoxelShape postShapeZ = Block.box(0, 0, 6, 2, 16, 10);
        VoxelShape postShapeX = Block.box(6, 0, 0, 10, 16, 2);
        NORTH_SOUTH_FLOOR_SHAPE = Shapes.or(BELL_SHAPE, EAST_WEST_BETWEEN, postShapeZ, postShapeZ.move(14 / 16d, 0, 0));
        EAST_WEST_FLOOR_SHAPE = Shapes.or(BELL_SHAPE, NORTH_SOUTH_BETWEEN, postShapeX, postShapeX.move(0, 0, 14 / 16d));
    }
}
