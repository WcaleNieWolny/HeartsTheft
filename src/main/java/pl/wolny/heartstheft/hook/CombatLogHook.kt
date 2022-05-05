package pl.wolny.heartstheft.hook

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent
import com.github.sirblobman.combatlogx.api.ICombatLogX
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import pl.wolny.heartstheft.formatMessage


class CombatLogHook(private val main: Plugin): Listener {

    private lateinit var plugin: Plugin

    fun register(){
        val plugin = Bukkit.getPluginManager().getPlugin("CombatLogX") ?: return
        if(!plugin.isEnabled) {
            return
        }

        this.plugin = plugin

        Bukkit.getPluginManager().registerEvents(this, main)
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private fun onPlayerElytraBoostEvent(event: PlayerElytraBoostEvent){
        val plugin = plugin as ICombatLogX
        val combatManager = plugin.combatManager
        val player = event.player
        if(combatManager.isInCombat(player)){
            event.isCancelled = true
            player.sendMessage(formatMessage("<red> Nie możesz tego zrobić podczas walki!"))
        }
    }

}