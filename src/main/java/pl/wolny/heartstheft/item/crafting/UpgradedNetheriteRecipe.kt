package pl.wolny.heartstheft.item.crafting

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.heartstheft.item.ItemFactory


class UpgradedNetheriteRecipe(plugin: JavaPlugin, itemFactory: ItemFactory) : ItemRecipe {

    private val namespace = NamespacedKey(plugin, "WLN_NETHERITE_ITEM")
    private val recipe = ShapedRecipe(namespace, itemFactory.upgradedNetherite())

    override fun register() {
        recipe.shape("DND", "NGN", "DND")
        recipe.setIngredient('D', Material.DIAMOND)
        recipe.setIngredient('N', Material.NETHERITE_INGOT)
        recipe.setIngredient('G', Material.GOLD_BLOCK)
        Bukkit.getServer().addRecipe(recipe)
    }

    override fun unregister() {
        Bukkit.getServer().removeRecipe(namespace)
    }
}