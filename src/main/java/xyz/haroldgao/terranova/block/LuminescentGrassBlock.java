package xyz.haroldgao.terranova.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class LuminescentGrassBlock extends Block {

    public static final String NAME = "luminescent_grass_block";

    public static final BlockBehaviour.Properties PROPERTIES =
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(0.7F)
                    .lightLevel(s -> 7)
                    .sound(SoundType.GRAVEL);

    public LuminescentGrassBlock(Properties prop) {
        super(prop);
    }
}
