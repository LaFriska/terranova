package xyz.haroldgao.terranova.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.haroldgao.terranova.TNRegistry;
import xyz.haroldgao.terranova.TerraNova;
import xyz.haroldgao.terranova.item.ItemRegistry;

import java.util.function.Function;

public final class BlockRegistry implements TNRegistry {

    private static BlockRegistry SINGLETON = null;

    public final DeferredRegister.Blocks blocks = DeferredRegister.createBlocks(TerraNova.MODID);

    public final DeferredBlock<HalfTransparentBlock> luminescent_ice
            = registerBlock(LuminescentIceBlock.ID, LuminescentIceBlock::new, LuminescentIceBlock.PROPERTIES);

    private BlockRegistry(){
    }

    private <T extends Block> DeferredBlock<T> registerBlock(String name,
                                                             Function<BlockBehaviour.Properties, T> func,
                                                             BlockBehaviour.Properties prop){
        return registerBlock(name, r -> func.apply(prop.setId(ResourceKey.create(Registries.BLOCK, r))));
    }

    private <T extends Block> DeferredBlock<T> registerBlock(String name, Function<Identifier, ? extends T> func) {
        DeferredBlock<T> toReturn = blocks.register(name, func);
        registerBlockItem(name, toReturn);
        return toReturn;
    }


    private <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ItemRegistry.getInstance().items.registerSimpleBlockItem(name, block, Item.Properties::new);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(luminescent_ice.get());
        }
    }

    @Override
    public void attachToEventBus(IEventBus eventBus) {
        blocks.register(eventBus);
        eventBus.addListener(this::addCreative);
    }

    public static BlockRegistry getInstance(){
        if(SINGLETON == null)
            SINGLETON = new BlockRegistry();
        return SINGLETON;
    }

}
