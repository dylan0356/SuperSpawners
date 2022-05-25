package me.Rakeo.SuperSpawners.Events;


import me.Rakeo.SuperSpawners.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class SpawnerEvents implements Listener {

static int spawnerLevel = 0;

@EventHandler
public static void onSpawnerInteract(PlayerInteractEvent event) {
    if (Main.config.getConfig().getBoolean("enabled") == true) {



    Player player = event.getPlayer();
    Block block = event.getClickedBlock();
    if (block == null) {return;}
    if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {return;}
    BlockState blockState = block.getState();



    //Item item = (Item) event.getItem();

    final Collection<Material> mobHeads = Arrays.stream(Material.values())
            .filter(material -> material.name().endsWith("_HEAD")).collect(Collectors.toList());
    final Collection<Material> mobSkulls = Arrays.stream(Material.values())
            .filter(material -> material.name().endsWith("_SKULL")).collect(Collectors.toList());
    final Collection<Material> mobFinal = mobSkulls;
    mobFinal.addAll(mobHeads);

    if (!(block.getType().equals(Material.SPAWNER))) {return;}

    CreatureSpawner cs = (CreatureSpawner) blockState;

        int targetx = block.getX();
        int targety = block.getY();
        int targetz = block.getZ();

        String locString = (targetx + "," + targety + "," + targetz);
        Location loc = new Location(block.getWorld(), targetx, targety, targetz);

        if (Main.data.getConfig().contains("spawners." + locString + ".level")){
            spawnerLevel = Main.data.getConfig().getInt("spawners." + locString + ".level");
        }

        //Main.data.getConfig().set("spawners." + locString + ".entity", (cs.getSpawnedType().toString()));
        Main.data.saveConfig();

    if (event.getItem() != null && event.getItem().getType().equals(Material.STICK)) {
        int currentLevel = cs.getSpawnCount() - 3;
        if (block != null && blockState instanceof CreatureSpawner) {
            player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + cs.getSpawnedType().toString() + ChatColor.GOLD + " Level: " + spawnerLevel + ChatColor.GRAY + " - Delay: " + ChatColor.DARK_GRAY + (cs.getMaxSpawnDelay())/10 + ChatColor.GRAY + " Population: " + ChatColor.DARK_GRAY +  cs.getSpawnCount() );        }
    }


//    if (event.getItem() != null && mobFinal.contains(event.getItem().getType())) {
//        if (block != null && blockState instanceof CreatureSpawner) {
//            String entityString = event.getItem().getType().toString();
//            String[] parts = entityString.split("_");
//            EntityType entity = EntityType.valueOf(parts[0]);
//            CreatureSpawner spawner = (CreatureSpawner) blockState;
//
//            int startingDelay = cs.getMaxSpawnDelay();
//            int startingCount = cs.getSpawnCount();
//            int startingMinDelay = cs.getMinSpawnDelay();
//
//            if ((startingDelay - 50) < startingMinDelay) {
//                player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + "This spawner is maxed out!");
//                return;
//            }
//            player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
//
//            cs.setMaxSpawnDelay(startingDelay - 50);
//            cs.setSpawnCount(startingCount + 1);
//            //player.sendMessage("You used a " + event.getItem().getType() + " on a " + block.getType() + " with type " + cs.getSpawnedType());
//            player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.GRAY + cs.getSpawnedType().toString() + " - Delay: " + (cs.getMaxSpawnDelay())/10 + " Population: " + cs.getSpawnCount() + " Range: " + cs.getSpawnRange());
//
//            cs.update();
//
//            //cs.setSpawnedType(entity);
//        }
//    }
}
}
@EventHandler
public static void onBlockPlace(BlockPlaceEvent event) {
    if (Main.config.getConfig().getBoolean("enabled") == true) {

    Block block = event.getBlock();
    Player player = event.getPlayer();

    if (event.getBlockAgainst() == null) {return;}
    BlockState blockState = event.getBlockAgainst().getState();




    Block target = event.getBlockAgainst();



    if (!(event.getBlockAgainst().getType() == Material.SPAWNER)) {return;}

    final Collection<Material> mobHeads = Arrays.stream(Material.values())
            .filter(material -> material.name().endsWith("_HEAD")).collect(Collectors.toList());
    final Collection<Material> mobSkulls = Arrays.stream(Material.values())
            .filter(material -> material.name().endsWith("_SKULL")).collect(Collectors.toList());
    final Collection<Material> mobFinal = mobSkulls;
    mobFinal.addAll(mobHeads);

    CreatureSpawner cs = (CreatureSpawner) blockState;

    if (mobFinal.contains(block.getType())) {

        String entityString = event.getItemInHand().getType().toString();
        String[] parts = entityString.split("_");
        EntityType entity = EntityType.valueOf(parts[0]);
        CreatureSpawner spawner = (CreatureSpawner) blockState;

        int startingDelay = cs.getMaxSpawnDelay();
        int startingCount = cs.getSpawnCount();
        int startingMinDelay = cs.getMinSpawnDelay();
        EntityType spawnerType = cs.getSpawnedType();
        int currentLevel = startingCount - 3;

        if (spawnerType != entity) {
            player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + "This is not the right head for this spawner!");
            return;
        }


        if ((startingDelay - 50) < startingMinDelay) {
            player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + "This spawner is maxed out!");
            return;
        }
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);

        int targetx = target.getX();
        int targety = target.getY();
        int targetz = target.getZ();

        String locString = (targetx + "," + targety + "," + targetz);
        Location loc = new Location(target.getWorld(), targetx, targety, targetz);

        if (Main.data.getConfig().contains("spawners." + locString + ".level")){
            spawnerLevel = Main.data.getConfig().getInt("spawners." + locString + ".level");
        }

        Main.data.getConfig().set("spawners." + locString + ".level", (spawnerLevel + 1));
        Main.data.getConfig().set("spawners." + locString + ".entity", (cs.getSpawnedType().toString()));
        Main.data.saveConfig();

        cs.setMaxSpawnDelay(startingDelay - 50);
        cs.setSpawnCount(startingCount + 1);
        //player.sendMessage("You used a " + event.getItem().getType() + " on a " + block.getType() + " with type " + cs.getSpawnedType());
        player.sendMessage(ChatColor.GREEN + "[SuperSpawners]: " + ChatColor.YELLOW + cs.getSpawnedType().toString() + ChatColor.GOLD + " Level: " + spawnerLevel + ChatColor.GRAY + " - Delay: " + ChatColor.DARK_GRAY + (cs.getMaxSpawnDelay())/10 + ChatColor.GRAY + " Population: " + ChatColor.DARK_GRAY +  cs.getSpawnCount() );
        cs.update();

        block.setType(Material.AIR);



        //cs.setSpawnedType(entity);
    }

}}
}
