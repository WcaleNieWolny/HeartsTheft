package pl.wolny.heartstheft.item.crafting

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.heartstheft.item.ItemFactory

class LiveBeaconRecipe(plugin: JavaPlugin, private val itemFactory: ItemFactory) : ItemRecipe {

    private val namespace = NamespacedKey(plugin, "WLN_SPAWNER_ITEM")
    private val recipe = ShapedRecipe(namespace, itemFactory.liveSpawner())

    override fun register() {
        recipe.shape("ZEZ", "EBE", "ZEZ")
        recipe.setIngredient('Z', RecipeChoice.ExactChoice(itemFactory.heartItem(1, 0)))
        recipe.setIngredient('E', Material.EMERALD_BLOCK)
        recipe.setIngredient('B', Material.BEACON)
        Bukkit.getServer().addRecipe(recipe)
    }

    override fun unregister() {
        Bukkit.getServer().removeRecipe(namespace)
    }
}