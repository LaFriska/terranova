package xyz.haroldgao.terranova;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import xyz.haroldgao.terranova.block.BlockRegistry;
import xyz.haroldgao.terranova.item.ItemRegistry;

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
        ItemRegistry.getInstance().attachToEventBus(modEventBus);
        BlockRegistry.getInstance().attachToEventBus(modEventBus);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

}
