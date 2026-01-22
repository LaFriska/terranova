package xyz.haroldgao.terranova.item;

import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.haroldgao.terranova.EventBusAttachable;

import static xyz.haroldgao.terranova.TerraNova.MODID;

/**
 * This class holds the {@link DeferredRegister} for items, and also references to all
 * registered items in this mod. This class is a singleton, which is instantiated on
 * the mod's entry point.
 * */
public final class ModItems {

    private static ModItems SINGLETON = null;

    private ModItems(){
    }

    /** {@link DeferredRegister} for all additional items. */
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> LUMINESCENCE
            = ITEMS.registerSimpleItem("luminescence", p -> new Item.Properties());

    public static void attachToEventBus(IEventBus eventBus){
        ITEMS.register(eventBus);
        eventBus.addListener(getInstance()::addCreative);
    }

    public static ModItems getInstance(){
        if(SINGLETON == null){
            SINGLETON = new ModItems();
        }
        return SINGLETON;
    }

    /**
     * Handler which listens for {@link BuildCreativeModeTabContentsEvent}, adding
     * all items to the appropriate creative mode tab.
     * */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(LUMINESCENCE.get());
        }
    }

}
