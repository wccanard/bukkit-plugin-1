/*
 * wccanard's plugin. Look for the line full of *****s and
 * just under that are the little programs that make
 * the commands work.
 */
package com.github.wccanard;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author wccanard
 */
public class Bukkitplugin1 extends JavaPlugin {

    Logger log;

    public void onEnable() {
        log = this.getLogger();
        log.info("flying creatures plugin enabled!");
    }

    public void onDisable() {        
        log.info("flying creatures plugin disabled");
    }

    private void dropcow(Player player) {
        Location loc = player.getLocation(); // variable loc is where the player is.
        World w = loc.getWorld(); // variable w is which world the player is in.   
        loc.setY(loc.getY() + 20); // This adds 20 to loc's Y variable
        // so loc is now pointing a long way above the
        // player who typed /flyingcow.
        // OK so now let's spawn a cow at location loc.
        w.spawnCreature(loc, EntityType.COW);
        Bukkit.broadcastMessage(ChatColor.BLUE + "Watch out " + player.getName() + " for falling cow!");
    }
    
    static final EntityType[] listOfEntityTypes = {
     EntityType.BLAZE ,EntityType.CAVE_SPIDER ,
     EntityType.CHICKEN ,EntityType.COW ,EntityType.CREEPER ,   
     EntityType.ENDER_DRAGON ,
     EntityType.ENDERMAN ,
     EntityType.GHAST ,EntityType.GIANT ,
     EntityType.IRON_GOLEM ,EntityType.MAGMA_CUBE ,
     EntityType.MUSHROOM_COW ,EntityType.OCELOT ,
     EntityType.PIG ,EntityType.PIG_ZOMBIE ,
     EntityType.SHEEP ,EntityType.SILVERFISH ,EntityType.SKELETON ,EntityType.SLIME ,
     EntityType.SNOWMAN ,EntityType.SPIDER ,
     EntityType.SQUID ,
     EntityType.VILLAGER ,EntityType.WOLF ,
     EntityType.ZOMBIE            
    };
    
    public EntityType randomEntity() {
        int i = (int) (Math.random() * listOfEntityTypes.length);
        return listOfEntityTypes[i];
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        // *********************************************************************
        //                 The fun starts here.
        //
        if (cmd.getName().equalsIgnoreCase("cheese")) { // i.e. if the player typed /cheese
            if (args.length > 0) { // then the player typed /cheese and then some more stuff
                // e.g. something like /cheese fushide5330
                // and that's wrong, so we print an error message in red
                // and then return false (so nothing happens).
                sender.sendMessage(ChatColor.RED + "/cheese: too many arguments!");
                sender.sendMessage(ChatColor.RED + "Syntax: /cheese");
                return false;
            }
            if (player == null) { // then it was the console, not a player
                // and the console can't use the cheese command
                // so tell them. In red. And return false.
                sender.sendMessage(ChatColor.RED + "/cheese command can only be run by a player");
            } else { // If we get this far then a player has typed /cheese.         
                Location loc = player.getLocation(); // variable loc is where the player is.
                World w = loc.getWorld(); // variable w is which world the player is in.
                loc.setY(loc.getY() + 3); // This changes loc: it adds 3 to the Y variable [which means "go 3 up"]
                loc.setZ(loc.getZ() - 2); // This subtracts 2 from its Z variable [which means "go 2 along"]
                // so loc is now pointing 3 along and 2 up from the
                // player who typed /cheese.
                Block b = w.getBlockAt(loc); // b is now the block at location loc.
                b.setTypeId(1); // Change b to type 1 i.e. stone or something? Not sure
                // -- I'm on a train and can't see the list :-)
                sender.sendMessage(ChatColor.BLUE + "Block changed to type 1!");
            }
            // Command has terminated successfully.
            return true;
        } else if (cmd.getName().equalsIgnoreCase("flyingcow")) {
            // i.e. if the player typed /flyingcow
            if (args.length == 0) { // the player has typed "/flyingcow" and no other stuff.
                // Now if that "player" is the console then that command makes no sense.
                if (player == null) { // that's the console
                    sender.sendMessage(ChatColor.RED
                            + "/flyingcow with no arguments can only be run by a player.");
                    return false;
                } else {
                    // If we get this far then a player typed /flyingcow,
                    // so we hit them.
                    dropcow(player);
                    return true;
                }
            } else {
                // If we're here then the player typed /flyingcow and some other stuff too
                // e.g. /flyingcow fushide5330 or /flyingcow all.
                // First let's test for all.
                if (args.length == 1 && "all".equals(args[0])) {
                    // Drop cows on the lot of them!
                    for (Player player2 : Bukkit.getOnlinePlayers()) {
                        // object player2 loops through the online players.
                        // now drop a cow on player2.
                        dropcow(player2);
                    }
                    return true;
                } else { // it must be a list of players.
                    for (String s : args) {
                        Player player2 = Bukkit.getPlayer(s);
                        if (player2 != null) { // player exists so let's drop a cow.
                            dropcow(player2);
                        } else { // player didn't exist!
                            sender.sendMessage(ChatColor.RED
                                    + "can't see player " + s);
                        }
                    }
                    return true;
                }


            }
            // Command has terminated successfully.          
        } else if (cmd.getName().equalsIgnoreCase("mobcannon")) {
            if (player == null) { // then it was the console, not a player
                // and the console can't use the cheese command
                // so tell them. In red. And return false.
                sender.sendMessage(ChatColor.RED + "mobcannon command can only be run by a player");
                return false;
            }     
            final EntityType mobtype;
            if (args.length == 0) {
                mobtype=randomEntity();
            } else {
                int i=Integer.parseInt(args[0]);
                sender.sendMessage("i="+i);
                log.info("i="+i);
                if (i >= 0 && i < listOfEntityTypes.length) {
                    mobtype=listOfEntityTypes[i];
                } else
                    mobtype=listOfEntityTypes[0];
            }
            log.info("mobcannon called. Mobtype is "+mobtype.getName());
            sender.sendMessage("mobcannon called. Mobtype is "+mobtype.getName());
            final LivingEntity mob = player.getWorld().spawnCreature(player.getEyeLocation(), mobtype);
            if (mob == null) {
                log.info("failed to create mob");
                sender.sendMessage("failed to create mob");
                return false;
            }
            log.info("mob created.");
            sender.sendMessage("mob created.");
            // final int i = (int) (Math.random() * Ocelot.Type.values().length);
            // mob.setCatType(Ocelot.Type.values()[i]);
            // mob.setTamed(true); but cows aren't tameable.
            mob.setVelocity(player.getEyeLocation().getDirection().multiply(2));
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

                @Override
                public void run() {
                    final Location loc = mob.getLocation();
                    mob.remove();
                    if (mobtype == EntityType.ENDER_DRAGON) {
                        log.info("ender end");
                        loc.getWorld().createExplosion(loc,0F);
                        loc.setY(loc.getY() + 10);
                        loc.getWorld().createExplosion(loc,0F);
                        loc.setY(loc.getY() + 10);
                        loc.getWorld().createExplosion(loc,0F);
                        loc.setX(loc.getX() + 10);
                        loc.getWorld().createExplosion(loc,0F);
                        loc.setY(loc.getY() + 10);
                        loc.getWorld().createExplosion(loc,0F);
                        loc.setZ(loc.getZ() + 10);
                        loc.getWorld().createExplosion(loc,0F);
                        loc.setY(loc.getY() + 10);
                        loc.getWorld().createExplosion(loc,0F);
                    } else {
                        loc.getWorld().createExplosion(loc, 0F);
                    }
                }
            }, 20);
            return true;
        }
        return false;
    }
}
