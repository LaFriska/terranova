package xyz.haroldgao.terranova.feature;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.haroldgao.terranova.TerraNova;

public class ModFeatures {

    private static ModFeatures SINGLETON = null;

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, TerraNova.MODID);

    public static final DeferredHolder<Feature<?>, LuminescentIceSpikeFeature> LUMINESCENT_ICE_SPIKE =
            FEATURES.register(LuminescentIceSpikeFeature.NAME,
                    () -> new LuminescentIceSpikeFeature(NoneFeatureConfiguration.CODEC));

    private ModFeatures(){
    }

    public static void attachToEventBus(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }

    public static ModFeatures getInstance(){
        if(SINGLETON == null)
            SINGLETON = new ModFeatures();
        return SINGLETON;
    }

}
