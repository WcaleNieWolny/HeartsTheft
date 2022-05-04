package pl.wolny.heartstheft.extensions

import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.entity.Player

fun Player.getSafeAttribute(attribute: Attribute): AttributeInstance {
    var attributeFromPlayer = getAttribute(attribute)
    if (attributeFromPlayer == null) {
        registerAttribute(attribute)
        attributeFromPlayer = getAttribute(attribute)!!
    }
    return attributeFromPlayer
}