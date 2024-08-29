package ca.fxco.TinyWorld.mixin.collision.shape;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DragonEggBlock.class)
public class DragonEggBlockMixin {
    @Shadow protected static final VoxelShape SHAPE = Shapes.or(
            Block.box(1, 3, 1, 15, 8, 15),
            Block.box(2, 1, 2, 14, 11, 14),
            Block.box(3, 0, 3, 13, 13, 13),
            Block.box(5, 13, 5, 11, 15, 11),
            Block.box(6, 15, 6, 10, 16, 10)
    );
}
