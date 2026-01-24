package xyz.haroldgao.terranova.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import net.minecraft.world.level.biome.Climate;

import static net.minecraft.world.level.biome.Climate.Parameter.span;

import java.util.function.Consumer;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(Identifier name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterUtils.ParameterPointListBuilder()
                .temperature(span(-0.05F, 0.05F))
                .humidity(span(0.03F, 0.04F))
                .erosion(span(-0.7F, -0.5F))
                .depth(span(1F, 1.2F))
                .build().forEach(point -> builder.add(point, ModBiomes.DEEP_GLOW));


        builder.build().forEach(mapper);
    }
}
