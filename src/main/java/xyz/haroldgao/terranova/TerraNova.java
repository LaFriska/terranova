package xyz.haroldgao.terranova;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import static xyz.haroldgao.terranova.item.Items.ITEMS;
import static xyz.haroldgao.terranova.item.Items.LUMINESCENCE_SHARDS;

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

//        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
//        CREATIVE_MODE_TABS.register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(LUMINESCENCE_SHARDS.get());
        }
    }
}
