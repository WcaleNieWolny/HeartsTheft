package pl.wolny.heartstheft.item.crafting

import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.heartstheft.item.ItemFactory

class NetheriteBlockRecipe(plugin: JavaPlugin, private val itemFactory: ItemFactory) : ItemRecipe {

    private val itemToBlockNamespace = NamespacedKey(plugin, "WLN_NETHERITE_BLOCK_BUILD_ITEM")
    private val itemToBlockRecipe = ShapelessRecipe(itemToBlockNamespace, itemFactory.upgradedNetheriteBlock())

    private val blockToItemNamespace = NamespacedKey(plugin, "WLN_NETHERITE_BLOCK_DESTROY_ITEM")
    private val blockToItemRecipe = ShapelessRecipe(blockToItemNamespace, itemFactory.upgradedNetherite(9))


    override fun register() {
        itemToBlockRecipe.addIngredient(9, itemFactory.upgradedNetherite())
        blockToItemRecipe.addIngredient(1, itemFactory.upgradedNetheriteBlock())

        Bukkit.getServer().addRecipe(itemToBlockRecipe)
        Bukkit.getServer().addRecipe(blockToItemRecipe)
    }

    override fun unregister() {
        Bukkit.getServer().removeRecipe(itemToBlockNamespace)
        Bukkit.getServer().removeRecipe(blockToItemNamespace)
    }
}