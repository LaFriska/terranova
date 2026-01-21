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
public final class ModItems implements EventBusAttachable {

    private static ModItems SINGLETON = null;

    private ModItems(){
    }

    /** {@link DeferredRegister} for all additional items. */
    public final DeferredRegister.Items items = DeferredRegister.createItems(MODID);

    public final DeferredItem<Item> luminescence
            = items.registerSimpleItem("luminescence", p -> new Item.Properties());

    /**
     * Handler which listens for {@link BuildCreativeModeTabContentsEvent}, adding
     * all items to the appropriate creative mode tab.
     * */
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(luminescence.get());
        }
    }

    @Override
    public void attachToEventBus(IEventBus eventBus){
        items.register(eventBus);
        eventBus.addListener(this::addCreative);
    }

    public static ModItems getInstance(){
        if(SINGLETON == null){
            SINGLETON = new ModItems();
        }
        return SINGLETON;
    }

}
