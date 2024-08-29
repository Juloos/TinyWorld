package ca.fxco.TinyWorld.mixin.collision.shape;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BrewingStandBlock.class)
public class BrewingStandBlockMixin extends BaseEntityBlock {
    @Mutable @Shadow @Final protected static final VoxelShape SHAPE;

    protected BrewingStandBlockMixin(Properties properties) {
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

    static {
        VoxelShape plateShape = box(9, 0, 5, 15, 2, 11);
        SHAPE = Shapes.or(plateShape, plateShape.move(-8 / 16d, 0, -4 / 16d), plateShape.move(-8 / 16d, 0, 4 / 16d), box(7, 0, 7, 9, 14, 9));
    }
}
