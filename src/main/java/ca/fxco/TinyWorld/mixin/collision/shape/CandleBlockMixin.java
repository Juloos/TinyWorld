package ca.fxco.TinyWorld.mixin.collision.shape;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CandleBlock.class)
public class CandleBlockMixin extends AbstractCandleBlock {

    @Shadow @Final private static VoxelShape ONE_AABB;
    @Mutable @Shadow @Final private static VoxelShape TWO_AABB;
    @Mutable @Shadow @Final private static VoxelShape THREE_AABB;
    @Mutable @Shadow @Final private static VoxelShape FOUR_AABB;

    public CandleBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends AbstractCandleBlock> codec() {
        return null;
    }

    @Override
    public Iterable<Vec3> getParticleOffsets(BlockState blockState) {
        return null;
    }

    static {
        VoxelShape candle1 = ONE_AABB;
        VoxelShape candle2 = box(5, 0, 7, 7, 5, 9);
        VoxelShape candle3 = box(7, 0, 9, 9, 3, 11);
        VoxelShape candle4 = box(9, 0, 8, 11, 5, 10);

        TWO_AABB = Shapes.or(candle1.move(2 / 16d, 0, -1 / 16d), candle2);
        THREE_AABB = Shapes.or(candle1.move(1 / 16d, 0, -1 / 16d), candle2, candle3);
        FOUR_AABB = Shapes.or(candle1.move(1 / 16d, 0, -2 / 16d), candle2.move(0, 0, -2 / 16d), candle3.move(-1 / 16d, 0, -1 / 16d), candle4);
    }
}
