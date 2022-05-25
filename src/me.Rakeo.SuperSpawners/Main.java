package me.Rakeo.SuperSpawners;

import me.Rakeo.SuperSpawners.Commands.SpawnerCommands;
import me.Rakeo.SuperSpawners.Events.SpawnerEvents;
import me.Rakeo.SuperSpawners.Files.ConfigManager;
import me.Rakeo.SuperSpawners.Files.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static DataManager data;
    public static ConfigManager config;

    @Override
    public void onEnable() {

        this.config = new ConfigManager(this);
        this.data = new DataManager(this);



        SpawnerCommands commands = new SpawnerCommands();
        getCommand("spawner").setExecutor(commands);
        getCommand("setspawner").setExecutor(commands);



        getServer().getPluginManager().registerEvents(new SpawnerEvents(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SuperSpawners]: SuperSpawners is enabled");
        if (config.getConfig().getBoolean("enabled") == false)
            getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[SuperSpawners]: SuperSpawners is disabled in the config!");

    }

    @Override
    public void onDisable() {

        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SuperSpawners]: SuperSpawners is disabled");
    }








}