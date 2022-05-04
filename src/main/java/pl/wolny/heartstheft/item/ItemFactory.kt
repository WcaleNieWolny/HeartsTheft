package pl.wolny.heartstheft.item

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import pl.wolny.heartstheft.formatMessage

class ItemFactory(
        private val noCraftingNameSpace: NamespacedKey,
        private val liveItemNameSpace: NamespacedKey,
        private val liveTimeToLiveNameSpace: NamespacedKey
) {

    fun heartItem(ammount: Int, ttl: Long): ItemStack {
        val item = ItemStack(Material.RED_DYE, ammount)
        val meta = item.itemMeta
        with(meta) {
            lore(listOf(Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<red>Mityczny przedmiot prosto z serca boga wojny!"))))
            displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<#e31b4a>SERCE ARESA"))) //EROSA
            persistentDataContainer.set(
                    noCraftingNameSpace,
                    PersistentDataType.SHORT,
                    1
            )
            persistentDataContainer.set(
                    liveItemNameSpace,
                    PersistentDataType.SHORT,
                    1
            )
            if (ttl != 0.toLong()) {
                persistentDataContainer.set(
                        liveTimeToLiveNameSpace,
                        PersistentDataType.LONG,
                        System.currentTimeMillis() + ttl
                )
            } else {
                persistentDataContainer.set(
                        liveTimeToLiveNameSpace,
                        PersistentDataType.LONG,
                        0
                )
            }

            addEnchant(Enchantment.THORNS, 1, true)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        item.itemMeta = meta

        return item
    }

    fun liveSpawner(): ItemStack {
        val item = ItemStack(Material.SPAWNER)
        val meta = item.itemMeta
        with(meta) {
            lore(listOf(
                    Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<green>Przywróci każdego z grobu!")),
                    Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<green>Napisz do administracji aby użyć ;)")),
                    Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<green>Czas odpowiedzi wynosi do 48 godzin"))
            ))
            displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<#14d746>SPAWNER DIONIZOUSA"))) //<#14d746cc>
            addEnchant(Enchantment.THORNS, 1, true)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        item.itemMeta = meta
        return item
    }

    fun upgradedNetherite(ammount: Int = 1): ItemStack {
        val item = ItemStack(Material.NETHERITE_INGOT, ammount)
        val meta = item.itemMeta
        with(meta) {
            lore(listOf(
                    Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<light_purple>Magiczna sztabka boga kowali!")),
                    Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<light_purple>Używana do ulepszania zbroi")),
            ))
            displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<#14d746cc>SZTABKA HEFAJSTOSA"))) //<#14d746cc>
            addEnchant(Enchantment.THORNS, 1, true)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        item.itemMeta = meta
        return item
    }

    fun upgradedNetheriteBlock(): ItemStack {
        val item = ItemStack(Material.NETHERITE_BLOCK)
        val meta = item.itemMeta
        with(meta) {
            lore(listOf(
                    Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<light_purple>Magiczny blok boga kowali!"))
            ))
            displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<#14d746cc>BLOK HEFAJSTOSA"))) //<#14d746cc>
            addEnchant(Enchantment.THORNS, 1, true)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
        item.itemMeta = meta
        return item
    }

    fun elytraItem(): ItemStack {
        val item = ItemStack(Material.ELYTRA)
        val meta = item.itemMeta
        with(meta) {
            lore(listOf(
                    Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<light_purple>Skrzydła prosto z greckiego boga złodzei!")),
            ))
            displayName(Component.empty().decoration(TextDecoration.ITALIC, false).append(formatMessage("<#E63C96>SKRZYDŁA HERMERSA"))) //<#14d746cc>

            isUnbreakable = true
            addEnchant(Enchantment.THORNS, 5, true)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
            addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
        }
        item.itemMeta = meta
        return item
    }
}