package pl.wolny.heartstheft

import net.kyori.adventure.text.minimessage.MiniMessage

val MINI_MESSAGE = MiniMessage.miniMessage()

fun formatMessage(string: String) = MINI_MESSAGE.deserialize(string)