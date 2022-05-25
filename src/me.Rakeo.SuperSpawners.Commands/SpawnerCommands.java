package me.Rakeo.SuperSpawners.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Locale;

public class SpawnerCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Player only command");
            return true;
        }

        Player player = ((Player) sender).getPlayer();

        if (cmd.getName().equalsIgnoreCase("spawner")) {

            Block block = player.getTargetBlock(null, 5);
            if (!(block.getType().equals(Material.SPAWNER))) {
                return true;
            }
            Location loc = block.getLocation();

            BlockState blockState = block.getState();
            CreatureSpawner cs = (CreatureSpawner) blockState;

            if (block != null && blockState instanceof CreatureSpawner) {

                int currentLevel = cs.getSpawnCount() - 3;

                player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + cs.getSpawnedType().toString() + ChatColor.GOLD + " Level: " + currentLevel + ChatColor.GRAY + " - Delay: " + ChatColor.DARK_GRAY + (cs.getMaxSpawnDelay())/10 + ChatColor.GRAY + " Population: " + ChatColor.DARK_GRAY +  cs.getSpawnCount() );
            }
        }

        if (cmd.getName().equalsIgnoreCase("setspawner")) {
            if (!(sender.hasPermission("superspawner.setspawner")) || !(sender.isOp())) {return true;}
            Block block = player.getTargetBlock(null, 5);
            if (!(block.getType().equals(Material.SPAWNER))) {

                return true;
            }
            Location loc = block.getLocation();

            BlockState blockState = block.getState();
            CreatureSpawner cs = (CreatureSpawner) blockState;

            if (block != null && blockState instanceof CreatureSpawner) {

                if (args == null) {
                    sender.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.RED + "You must include a valid entity!");
                    return true;
                }
                String mobToSpawn = args[0].toUpperCase();
                cs.setSpawnedType(EntityType.valueOf(mobToSpawn));

                player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.GRAY + " Spawner set to: " + ChatColor.YELLOW  + mobToSpawn);

            }
            }
        return true;
        }


        }


