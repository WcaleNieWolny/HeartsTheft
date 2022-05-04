package pl.wolny.heartstheft.item.crafting

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.heartstheft.item.ItemFactory

class HeartItemRecipe(plugin: JavaPlugin, itemFactory: ItemFactory) : ItemRecipe {

    private val namespace = NamespacedKey(plugin, "WLN_HEART_ITEM")
    private val recipe = ShapedRecipe(namespace, itemFactory.heartItem(1, 0))

    override fun register() {
        recipe.shape("DAE", "TLT", "EAD")
        recipe.setIngredient('E', Material.EMERALD_BLOCK)
        recipe.setIngredient('D', Material.DIAMOND_BLOCK)
        recipe.setIngredient('A', Material.GOLDEN_APPLE)
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING)
        recipe.setIngredient('L', Material.ELYTRA)
        Bukkit.getServer().addRecipe(recipe)
    }

    override fun unregister() {
        Bukkit.getServer().removeRecipe(namespace)
    }
}