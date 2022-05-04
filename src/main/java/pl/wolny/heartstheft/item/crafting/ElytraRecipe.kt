package pl.wolny.heartstheft.item.crafting

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.plugin.java.JavaPlugin
import pl.wolny.heartstheft.item.ItemFactory

class ElytraRecipe(plugin: JavaPlugin, private val itemFactory: ItemFactory) : ItemRecipe {

    private val namespace = NamespacedKey(plugin, "WLN_ELYTRA_ITEM")
    private val recipe = ShapedRecipe(namespace, itemFactory.elytraItem())

    override fun register() {
        recipe.shape("NSN", "TET", "NSN")
        recipe.setIngredient('N', RecipeChoice.ExactChoice(itemFactory.upgradedNetherite()))
        recipe.setIngredient('S', Material.NETHER_STAR)
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING)
        recipe.setIngredient('E', Material.ELYTRA)
        Bukkit.getServer().addRecipe(recipe)
    }

    override fun unregister() {
        Bukkit.getServer().removeRecipe(namespace)
    }
}