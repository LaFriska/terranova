package xyz.haroldgao.terranova.biome;

import net.minecraft.resources.Identifier;
import terrablender.api.RegionType;
import terrablender.api.Regions;
import xyz.haroldgao.terranova.TerraNova;

public class ModTerrablender {
    public static void registerBiomes() {
        Regions.register(new ModOverworldRegion(Identifier.fromNamespaceAndPath(TerraNova.MODID, "overworld"),
                RegionType.OVERWORLD,
                5));
    }
}
