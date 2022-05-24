

import commands.SpawnerCommands;
import events.SpawnerEvents;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();


        SpawnerCommands commands = new SpawnerCommands();
        getCommand("spawner").setExecutor(commands);
        getCommand("setspawner").setExecutor(commands);



        getServer().getPluginManager().registerEvents(new SpawnerEvents(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[SuperSpawners]: SuperSpawners is enabled");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[SuperSpawners]: SuperSpawners is disabled");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("superspawners")) {
            if (!sender.hasPermission("superspawner.reload")) {
                sender.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + "You do not have permission for this command");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + "Usage: /superspawners reload");
                return true;
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("reload.message")));
                    this.reloadConfig();
                }
            }
        }
        return true;
    }




}