package pl.wolny.heartstheft.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import pl.wolny.heartstheft.ban.BanManager
import pl.wolny.heartstheft.formatMessage

class HeartUnban(private val banManager: BanManager) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(formatMessage("<red> Podaj gracza do odbanowania!"))
            return true
        }
        if (!banManager.banList.contains(args[0])) {
            sender.sendMessage(formatMessage("<red>Taki gracz nie ma bana!"))
            return true
        }
        banManager.unban(args[0])
        sender.sendMessage(formatMessage("<green>Odbanowałem ${args[0]}"))
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String> {
        return banManager.banList
    }
}