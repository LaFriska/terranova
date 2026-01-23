package xyz.haroldgao.terranova.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import xyz.haroldgao.terranova.TerraNova;
import xyz.haroldgao.terranova.biome.ModBiomes;
import xyz.haroldgao.terranova.block.ModBlocks;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource GLOW_DIRT = makeStateRule(ModBlocks.GLOW_DIRT.get());
    private static final SurfaceRules.RuleSource LUM_GRASS_BLOCK = makeStateRule(ModBlocks.LUMINESCENT_GRASS_BLOCK.get());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return sequence(
                sequence(ifTrue(isBiome(ModBiomes.DEEP_GLOW), ifTrue(ON_FLOOR, LUM_GRASS_BLOCK)),
                                ifTrue(ON_CEILING, GLOW_DIRT),
                                ifTrue(UNDER_CEILING, GLOW_DIRT),
                                ifTrue(UNDER_FLOOR, GLOW_DIRT)
                ),

                ifTrue(ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
