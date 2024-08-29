package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BrewingStandBlock.class)
public class BrewingStandBlockMixin {
    @Mutable @Shadow @Final protected static final VoxelShape SHAPE;

    static {
        VoxelShape plateShape = Block.box(9, 0, 5, 15, 2, 11);
        SHAPE = Shapes.or(plateShape, plateShape.move(-8 / 16d, 0, -4 / 16d), plateShape.move(-8 / 16d, 0, 4 / 16d), Block.box(7, 0, 7, 9, 14, 9));
    }
}
