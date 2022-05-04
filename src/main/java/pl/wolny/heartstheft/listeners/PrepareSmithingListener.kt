package pl.wolny.heartstheft.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareSmithingEvent
import org.bukkit.inventory.ItemStack
import pl.wolny.heartstheft.item.ItemFactory

class PrepareSmithingListener(private val itemFactory: ItemFactory) : Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    fun onPrepareSmithingEvent(event: PrepareSmithingEvent) {
        val material = event.inventory.inputMineral ?: return
        if (material != itemFactory.upgradedNetherite()) {
            event.result = ItemStack(Material.AIR)
        }
    }

}