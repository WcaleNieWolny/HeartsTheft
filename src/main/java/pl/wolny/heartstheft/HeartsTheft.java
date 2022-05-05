package pl.wolny.heartstheft;

import kr.entree.spigradle.annotations.SpigotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.wolny.heartstheft.ban.BanManager;
import pl.wolny.heartstheft.command.HeartUnban;
import pl.wolny.heartstheft.command.WithdrawHeart;
import pl.wolny.heartstheft.hook.CombatLogHook;
import pl.wolny.heartstheft.item.ItemFactory;
import pl.wolny.heartstheft.item.crafting.*;
import pl.wolny.heartstheft.listeners.*;

import java.io.File;


@SpigotPlugin
public class HeartsTheft extends JavaPlugin {

    private final NamespacedKey noCraftingNameSpace = new NamespacedKey(this, "WLN_NO_CRAFTING");
    private final NamespacedKey liveItemNameSpace = new NamespacedKey(this, "WLN_CUS_LIVE");
    private final NamespacedKey liveTimeToLiveNameSpace = new NamespacedKey(this, "WLN_CUS_LIVE_TTL");
    private final ItemFactory itemFactory = new ItemFactory(noCraftingNameSpace, liveItemNameSpace, liveTimeToLiveNameSpace);
    private final HeartItemRecipe heartItemRecipe = new HeartItemRecipe(this, itemFactory);
    private final LiveBeaconRecipe liveBeaconRecipe = new LiveBeaconRecipe(this, itemFactory);
    private final UpgradedNetheriteRecipe upgradedNetheriteRecipe = new UpgradedNetheriteRecipe(this, itemFactory);
    private final NetheriteBlockRecipe netheriteBlockRecipe = new NetheriteBlockRecipe(this, itemFactory);
    private final ElytraRecipe elytraRecipe = new ElytraRecipe(this, itemFactory);
    private final WithdrawHeart withdrawHeart = new WithdrawHeart(itemFactory);
    private final BanManager banManager = new BanManager(new File(getDataFolder(), "bans.json"), this);
    private final HeartUnban heartUnban = new HeartUnban(banManager);

    @Override
    public void onEnable() {
        registerItems();

        PluginManager manager = Bukkit.getPluginManager();
        banManager.init();

        new CombatLogHook(this).register();

        manager.registerEvents(new DeathListener(itemFactory, banManager), this);
        manager.registerEvents(new CraftListener(noCraftingNameSpace, itemFactory), this);
        manager.registerEvents(new InteractListener(liveItemNameSpace, liveTimeToLiveNameSpace), this);
        manager.registerEvents(new PlaceBlockListener(itemFactory), this);
        manager.registerEvents(new DeathListener(itemFactory, banManager), this);
        manager.registerEvents(new PrepareSmithingListener(itemFactory), this);
        manager.registerEvents(new EntityToggleGlidelListener(itemFactory), this);
        manager.registerEvents(banManager, this);


        getCommand("withdrawheart").setExecutor(withdrawHeart);
        getCommand("heartunban").setExecutor(heartUnban);
        getCommand("heartUnban").setTabCompleter(heartUnban);
    }

    @Override
    public void onDisable() {
        unregisterItems();
    }

    private void registerItems() {
        heartItemRecipe.register();
        liveBeaconRecipe.register();
        upgradedNetheriteRecipe.register();
        netheriteBlockRecipe.register();
        elytraRecipe.register();
    }

    private void unregisterItems() {
        heartItemRecipe.unregister();
        liveBeaconRecipe.unregister();
        upgradedNetheriteRecipe.register();
        netheriteBlockRecipe.unregister();
        elytraRecipe.unregister();
    }
}
