package pl.wolny.heartstheft.listeners

import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.persistence.PersistentDataType
import pl.wolny.heartstheft.extensions.getSafeAttribute
import pl.wolny.heartstheft.formatMessage

class InteractListener(
    private val liveItemNameSpace: NamespacedKey,
    private val liveTimeToLiveNameSpace: NamespacedKey
) : Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    fun onInteractEvent(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val player = event.player
        if (item.itemMeta.persistentDataContainer.has(liveItemNameSpace)) {

            val ttl = item.itemMeta.persistentDataContainer.get(liveTimeToLiveNameSpace, PersistentDataType.LONG)
                ?: return

            if ((ttl > 0) && (System.currentTimeMillis() > ttl)) {
                player.sendMessage(formatMessage("<red>Te serce wygasło!"))
                player.inventory.remove(item)
                event.isCancelled = true
                return
            }

            if (depositLive(player, item.amount)) {
                player.inventory.remove(item)
            }
            event.isCancelled = true
        }
    }

    private fun depositLive(player: Player, ammount: Int): Boolean {
        val attribute = player.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH)
        if (attribute.baseValue == 40.0 || attribute.baseValue + ammount * 2 > 40) {
            player.sendMessage(formatMessage("<red>Nie możesz tyle wpłacić"))
            return false
        }
        player.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH).baseValue =
            player.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH).baseValue + ammount * 2
        player.sendMessage(formatMessage("<green>Wpłacono $ammount serca!"))
        return true
    }
}