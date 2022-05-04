package pl.wolny.heartstheft.command

import org.bukkit.attribute.Attribute
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import pl.wolny.heartstheft.extensions.getSafeAttribute
import pl.wolny.heartstheft.formatMessage
import pl.wolny.heartstheft.item.ItemFactory

class WithdrawHeart(private val itemFactory: ItemFactory) : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("Nie możesz tego zrobić!")
            return true
        }
        if (args.isEmpty() || args[0].toIntOrNull() == null) {
            sender.sendMessage(formatMessage("<red>Podaj liczbę serc do wypłacenia!"))
            return true
        }

        val toWithdraw = args[0].toInt()
        val senderHearts = sender.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH).baseValue

        if (senderHearts - 2 < toWithdraw * 2) {
            sender.sendMessage(formatMessage("<red>Nie możesz tyle wypłacić!"))
            return true
        }
        if (sender.inventory.addItem(itemFactory.heartItem(toWithdraw, 0)).isNotEmpty()) {
            sender.sendMessage(formatMessage("<red>Posiadasz za mało miejsca w ekwipunku!"))
            return true
        }
        sender.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH).baseValue =
            sender.getSafeAttribute(Attribute.GENERIC_MAX_HEALTH).baseValue - toWithdraw * 2
        sender.sendMessage(formatMessage("<green>Wypłacono $toWithdraw serc!"))
        return true
    }

}