package me.zeuss.asba.listeners;

import me.zeuss.asba.Main;
import me.zeuss.asba.manager.ItemLoader;
import me.zeuss.asba.utilities.ConfigFile;
import me.zeuss.asba.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class IslandInterract implements Listener {

    private ConfigFile config;
    private ItemLoader loader;

    public IslandInterract() {
        this.config = Main.getPlugin().getConfig();
        this.loader = Main.getPlugin().getLoader();
        Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
    }

    @EventHandler(ignoreCancelled = true)
    void onTeleport(PlayerTeleportEvent e) {
        if (config.getBoolean("prevent_teleport")) {
            String name = config.getString("prevent_teleport_world");
            World w = Bukkit.getWorld(name);
            if (w != null) {
                Player p = e.getPlayer();
                if (!Utils.hasIslandPermission(p, e.getTo())) {
                    e.setCancelled(true);
                    Utils.sendMessage(p, "prevent_teleport");
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    void onBlockClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.PHYSICAL) {
            if (config.getBoolean("block_interract")) {
                Player p = e.getPlayer();
                if (!Utils.hasIslandPermission(p, e.getClickedBlock().getLocation())) {
                    if (!loader.hasBlocks()) {
                        e.setCancelled(true);
                        e.setUseItemInHand(Event.Result.DENY);
                        e.setUseInteractedBlock(Event.Result.DENY);
                        Utils.sendMessage(p, "click_block");
                        return;
                    }
                    ItemStack item = new ItemStack(e.getClickedBlock().getType());
                    if (loader.hasBlock(item)) {
                        e.setCancelled(true);
                        e.setUseItemInHand(Event.Result.DENY);
                        e.setUseInteractedBlock(Event.Result.DENY);
                        Utils.sendMessage(p, "click_block");
                        return;
                    }
                }
            }
            if (config.getBoolean("air_interract")) {
                Player p = e.getPlayer();
                if (!Utils.hasIslandPermission(p, e.getClickedBlock().getLocation())) {
                    if (!loader.hasItens()) {
                        e.setCancelled(true);
                        e.setUseItemInHand(Event.Result.DENY);
                        e.setUseInteractedBlock(Event.Result.DENY);
                        Utils.sendMessage(p, "click_air");
                        return;
                    }
                    List<ItemStack> p_itens = Arrays.asList(p.getInventory().getItemInMainHand(), p.getInventory().getItemInOffHand());
                    if (Utils.hasValidIten(p_itens)) {
                        if (p_itens.get(0) != null) {
                            if (loader.hasItem(p_itens.get(0))) {
                                e.setCancelled(true);
                                e.setUseItemInHand(Event.Result.DENY);
                                e.setUseInteractedBlock(Event.Result.DENY);
                            }
                        } else {
                            if (loader.hasItem(p_itens.get(1))) {
                                e.setCancelled(true);
                                e.setUseItemInHand(Event.Result.DENY);
                                e.setUseInteractedBlock(Event.Result.DENY);
                            }
                        }
                    }
                }
            }
        }
    }

}
