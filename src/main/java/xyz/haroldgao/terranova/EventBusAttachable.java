package xyz.haroldgao.terranova;

import net.neoforged.bus.api.IEventBus;

public interface EventBusAttachable {

    void attachToEventBus(IEventBus eventBus);

}
