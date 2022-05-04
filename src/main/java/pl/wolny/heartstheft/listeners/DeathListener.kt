package pl.wolny.heartstheft.listeners

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.PlayerDeathEvent
import pl.wolny.heartstheft.ban.BanManager
import pl.wolny.heartstheft.extensions.getSafeAttribute
import pl.wolny.heartstheft.formatMessage
import pl.wolny.heartstheft.item.ItemFactory

class DeathListener(private val itemFactory: ItemFactory, private val banManager: BanManager) : Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    fun onDeathEvent(event: PlayerDeathEvent) {
        val player = event.player
        val lastDamage = player.lastDamageCause ?: return

        if (lastDamage is EntityDamageByEntityEvent) {
            val killer = lastDamage.damager
            if (killer is Player) {
                exchangeHearts(player, killer, event)
                return
            }

            if (killer is Projectile) {
                val shooter = killer.shooter
                if (shooter is Player) {
                    exchangeHearts(player, shooter, event)
                    return
                }
            }
        }
        dropHeart(event)
    }

    private fun exchangeHearts(victim: Player, killer: Player, event: PlayerDeathEvent) {
        if (victim.equals(killer)) {
            return
        }
        val killerHearts = killer.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH)
        val victimHearts = victim.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH)
        if (killerHearts.baseValue == 40.0) {
            killer.sendMessage(formatMessage("<red>Posiadasz 20 serc! Serce wypadnie na podłogę!"))
            dropHeart(event)
            return
        }
        checkBan(victim)
        killerHearts.baseValue = killerHearts.baseValue + 2
        victimHearts.baseValue = victimHearts.baseValue - 2

    }

    private fun dropHeart(event: PlayerDeathEvent) {
        event.drops.add(itemFactory.heartItem(1, 30000))
        val victimHearts = event.player.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH)
        checkBan(event.player)
        victimHearts.baseValue = victimHearts.baseValue - 2
    }

    fun checkBan(player: Player) {
        if ((player.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH).baseValue - 2) == 0.0) {
            banManager.ban(player)
        }
    }
}