package xyz.haroldgao.terranova.feature;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.haroldgao.terranova.EventBusAttachable;
import xyz.haroldgao.terranova.TerraNova;

public class ModFeatures implements EventBusAttachable {

    private static ModFeatures SINGLETON = null;

    public final DeferredRegister<Feature<?>> features = DeferredRegister.create(BuiltInRegistries.FEATURE, TerraNova.MODID);

    public final DeferredHolder<Feature<?>, LuminescentIceSpikeFeature> luminescentIceSpike =
            features.register(LuminescentIceSpikeFeature.NAME,
                    () -> new LuminescentIceSpikeFeature(NoneFeatureConfiguration.CODEC));

    private ModFeatures(){
    }


    @Override
    public void attachToEventBus(IEventBus eventBus) {
        features.register(eventBus);
    }

    public static ModFeatures getInstance(){
        if(SINGLETON == null)
            SINGLETON = new ModFeatures();
        return SINGLETON;
    }

}
