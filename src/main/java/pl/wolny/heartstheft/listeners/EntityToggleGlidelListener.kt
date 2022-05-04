package pl.wolny.heartstheft.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityToggleGlideEvent
import pl.wolny.heartstheft.formatMessage
import pl.wolny.heartstheft.item.ItemFactory

class EntityToggleGlidelListener(private val itemFactory: ItemFactory) : Listener {

    @EventHandler
    fun onEntityToggleGlideEvent(event: EntityToggleGlideEvent) {
        val player = event.entity

        if (player !is Player) {
            return
        }

        val elytra = player.inventory.chestplate ?: return
        if (elytra != itemFactory.elytraItem()) {
            event.isCancelled = true
            player.sendMessage(formatMessage("<red> Nie możesz używać tej elytry do latania!"))
            player.sendMessage(formatMessage("<red> Stwórz ulepsznoną elytrę aby latać!"))
        }
    }

}