package pl.wolny.heartstheft.listeners

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.ItemStack
import pl.wolny.heartstheft.item.ItemFactory


class CraftListener(private val namespacedKey: NamespacedKey, private val itemFactory: ItemFactory) : Listener {

    private var air = ItemStack(Material.AIR)

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    private fun onCraftEvent(event: PrepareItemCraftEvent) {
        val matrix = event.inventory.matrix
        if (matrix?.any { it != null && it.itemMeta.persistentDataContainer.has(namespacedKey) } == true) {
            if (event.inventory.result?.equals(itemFactory.liveSpawner()) == true) {
                return
            }
            event.inventory.result = air
            return
        }
    }

}
