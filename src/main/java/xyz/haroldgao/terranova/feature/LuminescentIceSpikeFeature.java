package xyz.haroldgao.terranova.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.IceSpikeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import xyz.haroldgao.terranova.block.ModBlocks;

public class LuminescentIceSpikeFeature extends IceSpikeFeature {

    public static final String NAME = "luminescent_ice_spike";

    /**
     * Rarity of luminescent ice spawning in the spikes, the ratio between
     * luminescent ice and packed ice is 1:LUMINESCENCE_RARITY.
     * */
    public static final int LUMINESCENCE_RARITY = 40;


    public LuminescentIceSpikeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    /**
     * Placement rule. Whoever wrote {@link IceSpikeFeature} does not understand
     * basic code hygiene, so this method override both inserts luminescent ice,
     * and also cleans up Mojang's shitty Java code.
     * */
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> placeContext) {
        BlockPos blockpos = placeContext.origin();
        RandomSource randomsource = placeContext.random();

        //Lower worldGenLe
        WorldGenLevel worldgenlevel = placeContext.level();
        while(worldgenlevel.isEmptyBlock(blockpos) && blockpos.getY() > worldgenlevel.getMinY() + 2) {
            blockpos = blockpos.below();
        }

        //Cancel place attempt if not on snow block
        if (!worldgenlevel.getBlockState(blockpos).is(Blocks.SNOW_BLOCK))
            return false;

        blockpos = blockpos.above(randomsource.nextInt(4));

        int i = randomsource.nextInt(4) + 7;
        int j = i / 4 + randomsource.nextInt(2);
        if (j > 1 && randomsource.nextInt(60) == 0) {
            blockpos = blockpos.above(10 + randomsource.nextInt(30));
        }

        for(int y = 0; y < i; ++y) {
            float f = (1.0F - (float)y / (float)i) * (float)j;
            int l = Mth.ceil(f);
            for(int x = -l; x <= l; ++x) {
                float f1 = (float)Mth.abs(x) - 0.25F;

                for(int z = -l; z <= l; ++z) {
                    float f2 = (float)Mth.abs(z) - 0.25F;
                    if ((x == 0 && z == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (x != -l && x != l && z != -l && z != l || !(randomsource.nextFloat() > 0.75F))) {
                        BlockState blockstate = worldgenlevel.getBlockState(blockpos.offset(x, y, z));
                        if (isValidPlacementBlock(blockstate)) {
                            place(worldgenlevel, blockpos.offset(x, y, z), randomsource);
                        }

                        if (y != 0 && l > 1) {
                            blockstate = worldgenlevel.getBlockState(blockpos.offset(x, -y, z));
                            if (isValidPlacementBlock(blockstate)) {
                                place(worldgenlevel, blockpos.offset(x, -y, z), randomsource);
                            }
                        }
                    }
                }
            }
        }

        int k1 = j - 1;
        if (k1 < 0) {
            k1 = 0;
        } else if (k1 > 1) {
            k1 = 1;
        }

        for(int x = -k1; x <= k1; ++x) {
            for(int z = -k1; z <= k1; ++z) {
                BlockPos blockpos1 = blockpos.offset(x, -1, z);
                int j2 = 50;
                if (Math.abs(x) == 1 && Math.abs(z) == 1) {
                    j2 = randomsource.nextInt(5);
                }

                while(blockpos1.getY() > 50) {
                    BlockState blockstate1 = worldgenlevel.getBlockState(blockpos1);
                    if (!isValidPlacementBlock(blockstate1) && !blockstate1.is(Blocks.PACKED_ICE)) {
                        break;
                    }
                    place(worldgenlevel, blockpos1, randomsource);
                    blockpos1 = blockpos1.below();
                    --j2;
                    if (j2 <= 0) {
                        blockpos1 = blockpos1.below(randomsource.nextInt(5) + 1);
                        j2 = randomsource.nextInt(5);
                    }
                }
            }
        }
        return true;
    }

    private void place(WorldGenLevel worldgenlevel, BlockPos blockpos, RandomSource randomSource) {
        BlockState placedBlock = randomSource.nextInt(LUMINESCENCE_RARITY) == 0 ? ModBlocks.getInstance().LUMINESCENT_ICE.get().defaultBlockState()
                                                                                : Blocks.PACKED_ICE.defaultBlockState();
        this.setBlock(worldgenlevel, blockpos, placedBlock);
    }

    private static boolean isValidPlacementBlock(BlockState blockstate) {
        return blockstate.isAir() || isDirt(blockstate) || blockstate.is(Blocks.SNOW_BLOCK) || blockstate.is(Blocks.ICE);
    }
}
