package pl.wolny.heartstheft.ban

import com.google.gson.Gson
import com.okkero.skedule.SynchronizationContext
import com.okkero.skedule.schedule
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.heartstheft.formatMessage
import java.io.File
import java.io.FileWriter
import java.util.*


class BanManager(
        private val banFile: File,
        private val plugin: JavaPlugin
) : Listener {

    val banList = mutableListOf<String>()
    private val gson = Gson()
    private val scheduler = Bukkit.getScheduler()

    private val banComponent = formatMessage("<red> Straciłeś wszystkie życia! Jesteś zbanowany!")

    fun init() {
        if (!banFile.exists()) {
            banFile.parentFile.mkdirs()
            banFile.createNewFile()
        } else {
            val scanner = Scanner(banFile)
            if (scanner.hasNext()) {
                val list = gson.fromJson(scanner.nextLine(), arrayListOf<String>()::class.java)
                banList.addAll(list)
            }
        }
    }

    private fun saveBanListAsync() {
        scheduler.schedule(plugin, SynchronizationContext.ASYNC) {
            val writer = FileWriter(banFile)
            writer.write(gson.toJson(banList))
            writer.close()
        }
    }

    fun ban(player: Player) {
        player.kick(banComponent)
        banList.add(player.name)
        saveBanListAsync()
    }

    fun unban(name: String) {
        banList.remove(name)
        saveBanListAsync()
    }

    @EventHandler(priority = EventPriority.NORMAL)
    fun onConnectionEvent(event: AsyncPlayerPreLoginEvent) {
        if (banList.contains(event.name)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, banComponent)
        }
    }

}