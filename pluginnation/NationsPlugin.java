package fr.nations.plugin;

import fr.nations.plugin.commands.*;
import fr.nations.plugin.listeners.*;
import fr.nations.plugin.managers.*;
import org.bukkit.plugin.java.JavaPlugin;

public class NationsPlugin extends JavaPlugin {

    private static NationsPlugin instance;
    private NationManager nationManager;
    private ColonyManager colonyManager;
    private LevelManager levelManager;
    private StorageManager storageManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // Init managers
        this.storageManager = new StorageManager(this);
        this.levelManager = new LevelManager(this);
        this.nationManager = new NationManager(this);
        this.colonyManager = new ColonyManager(this);

        storageManager.loadAll();

        // Register commands
        getCommand("nation").setExecutor(new NationCommand(this));
        getCommand("nation").setTabCompleter(new NationTabCompleter(this));

        // Register listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockListener(this), this);
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);

        getLogger().info("§a[Nations] Plugin activé avec succès !");
    }

    @Override
    public void onDisable() {
        if (storageManager != null) storageManager.saveAll();
        getLogger().info("§c[Nations] Plugin désactivé.");
    }

    public static NationsPlugin getInstance() { return instance; }
    public NationManager getNationManager() { return nationManager; }
    public ColonyManager getColonyManager() { return colonyManager; }
    public LevelManager getLevelManager() { return levelManager; }
    public StorageManager getStorageManager() { return storageManager; }
}
