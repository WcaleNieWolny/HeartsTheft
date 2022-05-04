package pl.wolny.heartstheft.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import pl.wolny.heartstheft.item.ItemFactory

class PlaceBlockListener(private val itemFactory: ItemFactory) : Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    fun onBlockPlaceEvent(event: BlockPlaceEvent) {
        if (
            event.itemInHand == itemFactory.liveSpawner() ||
            event.itemInHand == itemFactory.upgradedNetheriteBlock()
        ) {
            event.isCancelled = true
        }
    }
}