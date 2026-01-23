package xyz.haroldgao.terranova.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.haroldgao.terranova.TerraNova;
import xyz.haroldgao.terranova.item.ModItems;

import java.util.function.Function;

public final class ModBlocks {

    private static ModBlocks SINGLETON = null;

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TerraNova.MODID);

    public static final DeferredBlock<LuminescentIceBlock> LUMINESCENT_ICE = registerBlock(LuminescentIceBlock.ID, LuminescentIceBlock::new, LuminescentIceBlock.PROPERTIES);
    public static final DeferredBlock<GlowDirtBlock> GLOW_DIRT = registerBlock(GlowDirtBlock.NAME, GlowDirtBlock::new, GlowDirtBlock.PROPERTIES);
    public static final DeferredBlock<LuminescentGrassBlock> LUMINESCENT_GRASS_BLOCK = registerBlock(LuminescentGrassBlock.NAME, LuminescentGrassBlock::new, LuminescentGrassBlock.PROPERTIES);

    private ModBlocks(){
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name,
                                                             Function<BlockBehaviour.Properties, T> func,
                                                             BlockBehaviour.Properties prop){
        return registerBlock(name, r -> func.apply(prop.setId(ResourceKey.create(Registries.BLOCK, r))));
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<Identifier, ? extends T> func) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, func);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.registerSimpleBlockItem(name, block, Item.Properties::new);
    }

    public static void attachToEventBus(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        eventBus.addListener(getInstance()::addCreative);
    }

    public static ModBlocks getInstance(){
        if(SINGLETON == null)
            SINGLETON = new ModBlocks();
        return SINGLETON;
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(LUMINESCENT_ICE.get());
            event.accept(GLOW_DIRT.get());
            event.accept(LUMINESCENT_GRASS_BLOCK.get());
        }
    }

}
