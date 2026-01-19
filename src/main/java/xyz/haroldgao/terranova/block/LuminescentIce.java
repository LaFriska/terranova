package xyz.haroldgao.terranova.block;

import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class LuminescentIce extends HalfTransparentBlock {

    public static final String ID = "luminescent_ice";

    public static final BlockBehaviour.Properties PROPERTIES =
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.ICE)
                    .strength(3.5F)
                    .friction(0.989F)
                    .lightLevel(s -> 15)
                    .sound(SoundType.GLASS)
                    .isRedstoneConductor((bs,bg,bp) -> false)
                    .noOcclusion();

    public LuminescentIce(Properties prop) {
        super(prop);
    }
}
