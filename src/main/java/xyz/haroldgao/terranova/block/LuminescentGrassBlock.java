package xyz.haroldgao.terranova.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.material.MapColor;

public class LuminescentGrassBlock extends SpreadingSnowyDirtBlock {

    public static final MapCodec<LuminescentGrassBlock> CODEC = BlockBehaviour.simpleCodec(LuminescentGrassBlock::new);

    public static final String NAME = "luminescent_grass_block";

    public static final BlockBehaviour.Properties PROPERTIES =
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(0.7F)
                    .lightLevel(s -> 7)
                    .sound(SoundType.GRAVEL)
                    .randomTicks();

    public LuminescentGrassBlock(Properties prop) {
        super(prop);
    }

    @Override
    protected MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return CODEC;
    }

    private static boolean canPropagate(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos above = pos.above();
        return canBeGrass(state, level, pos) && !level.getFluidState(above).is(FluidTags.WATER);
    }

    private static boolean canBeGrass(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos abovePos = pos.above();
        BlockState aboveState = levelReader.getBlockState(abovePos);
        if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (aboveState.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int blockingLight = LightEngine.getLightBlockInto(state, aboveState, Direction.UP, aboveState.getLightBlock());
            return blockingLight < 15;
        }
    }

    @Override
    protected void randomTick(BlockState blockstate, ServerLevel serverLevel, BlockPos pos, RandomSource rs) {

        if (!canBeGrass(blockstate, serverLevel, pos)) {
            if (!serverLevel.isAreaLoaded(pos, 1))
                return;
            // Can't be grass block, so turn into glow dirt
            serverLevel.setBlockAndUpdate(pos, ModBlocks.GLOW_DIRT.get().defaultBlockState());
            return;
        }

        if (!serverLevel.isAreaLoaded(pos, 3)) {
            return;
        }

        // Spreading logic
        if (serverLevel.getMaxLocalRawBrightness(pos.above()) >= 9) {
            BlockState defaultBlockState = this.defaultBlockState();

            for(int i = 0; i < 4; ++i) {
                BlockPos blockpos = pos.offset(rs.nextInt(3) - 1, rs.nextInt(5) - 3, rs.nextInt(3) - 1);
                if (serverLevel.getBlockState(blockpos).is(ModBlocks.GLOW_DIRT.get()) && canPropagate(defaultBlockState, serverLevel, blockpos)) {
                    serverLevel.setBlockAndUpdate(blockpos, defaultBlockState.setValue(SNOWY, isSnowySetting(serverLevel.getBlockState(blockpos.above()))));
                }
            }
        }

    }
}
