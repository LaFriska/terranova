package xyz.haroldgao.terranova.item;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

import static xyz.haroldgao.terranova.TerraNova.MODID;

public final class Items {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> LUMINESCENCE_SHARDS
            = ITEMS.registerSimpleItem("luminescence_shards", simplePropertySupplier());

    private static UnaryOperator<Item.Properties> simplePropertySupplier(){
        return p -> new Item.Properties();
    }

}
