package xyz.haroldgao.terranova.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class GlowDirtBlock extends Block {

    public static final String NAME = "glow_dirt";

    public static final BlockBehaviour.Properties PROPERTIES =
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(0.7F)
                    .lightLevel(s -> 5)
                    .sound(SoundType.GRAVEL);

    public GlowDirtBlock(Properties prop) {
        super(prop);
    }
}
