package xyz.haroldgao.terranova.biome;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;
import terrablender.api.VanillaParameterOverlayBuilder;
import xyz.haroldgao.terranova.TerraNova;

import static terrablender.api.ParameterUtils.*;

import java.util.function.Consumer;

public class ModOverworldRegion extends Region {
    public ModOverworldRegion(Identifier name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {

        VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();

        new ParameterUtils.ParameterPointListBuilder()
                .temperature(ParameterUtils.Temperature.span(Temperature.COOL, Temperature.FROZEN))
                .humidity(Humidity.DRY)
//                .continentalness(Continentalness.INLAND)
                .depth(Climate.Parameter.span(0.7F, 1F))
                .weirdness(Climate.Parameter.span(-1, -0.5F))
//                .erosion(Climate.Parameter.span(0F, 1))
                .build().forEach(point -> builder.add(point, ModBiomes.DEEP_GLOW));

        //seed for ancient city + deep glow: -9138410490720384662

        builder.build().forEach(mapper);
    }
}
