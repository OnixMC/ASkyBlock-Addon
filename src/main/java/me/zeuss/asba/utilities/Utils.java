package me.zeuss.asba.utilities;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import me.zeuss.asba.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static String textFormat(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Island getIslandByLocation(Location loc) {
        if (loc == null) return null;
        ASkyBlockAPI api = Main.getPlugin().getApi();
        ArrayList<Island> ilhas = new ArrayList<>(api.getOwnedIslands().values());
        Optional<Island> fil = ilhas.stream().filter(i -> i.getCenter().distance(loc) <= (i.getProtectionSize() / 2)).findFirst();
        return fil.orElse(null);
    }

    public static boolean hasIslandPermission(Player player, Location loc) {
        Island is = getIslandByLocation(loc);
        if (is != null) {
            return is.getMembers().contains(player.getUniqueId());
        }
        return false;
    }

    public static boolean hasValidIten(List<ItemStack> its) {
        Optional<ItemStack> result = its.stream().filter(i -> i != null && i.getType() != Material.AIR).findFirst();
        return result.isPresent();
    }

    public static void sendMessage(Player player, String message) {
        ConfigFile config = Main.getPlugin().getConfig();
        if (config.getBoolean("messages.enable")) {
            String msg = config.getString("messages.".concat(message));
            if (msg.length() > 0) {
                player.sendMessage(textFormat(msg));
            }
        }
    }

}
