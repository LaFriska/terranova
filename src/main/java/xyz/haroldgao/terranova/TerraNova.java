package xyz.haroldgao.terranova;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import terrablender.api.SurfaceRuleManager;
import xyz.haroldgao.terranova.biome.ModTerrablender;
import xyz.haroldgao.terranova.biome.surface.ModSurfaceRules;
import xyz.haroldgao.terranova.block.ModBlocks;
import xyz.haroldgao.terranova.feature.ModFeatures;
import xyz.haroldgao.terranova.item.ModItems;

@Mod(TerraNova.MODID)
public class TerraNova {

    /**
     * The mod-ID used across the project. Every reference to the mod-ID should be
     * made through this attribute.
     */
    public static final String MODID = "terranova";

    /**
     * slf4j logger used across this project.
     */
    public static final Logger LOGGER = LogUtils.getLogger();


    public TerraNova(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.getInstance().attachToEventBus(modEventBus);
        ModBlocks.getInstance().attachToEventBus(modEventBus);
        ModFeatures.getInstance().attachToEventBus(modEventBus);
        ModTerrablender.registerBiomes();
//        ConfiguredFeatureRegistry.CONFIGURED.register(modEventBus);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modEventBus.addListener(this::commonSetup);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, ModSurfaceRules.makeRules());
        });
    }

}
