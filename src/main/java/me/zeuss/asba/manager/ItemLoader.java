package me.zeuss.asba.manager;

import me.zeuss.asba.Main;
import me.zeuss.asba.utilities.ConfigFile;
import me.zeuss.asba.utilities.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemLoader {

    private ArrayList<ItemStack> itens;
    private ArrayList<ItemStack> blocks;

    public ItemLoader() {

        this.itens = new ArrayList<>();
        this.blocks = new ArrayList<>();

        ConfigFile config = Main.getPlugin().getConfig();
        List<String> itens = config.getStringList("itens_hand");
        List<String> blocks = config.getStringList("block_protection");

        itens.forEach(i -> {
            Material mat;
            int data = 0;
            i = i.toLowerCase();
            if (i.contains("minecraft:")) {
                i = i.replace("minecraft:", "");
            }
            mat = Material.matchMaterial(i.trim());
            ItemStack item = new ItemStack(mat);
            this.itens.add(item);
        });

        blocks.forEach(i -> {
            Material mat;
            int data = 0;
            i = i.toLowerCase();
            if (i.contains("minecraft:")) {
                i = i.replace("minecraft:", "");
            }
            mat = Material.matchMaterial(i.trim());
            ItemStack item = new ItemStack(mat);
            this.blocks.add(item);
        });

    }

    public boolean hasItens() {
        return this.itens.size() > 0;
    }

    public boolean hasBlocks() {
        return this.blocks.size() > 0;
    }

    public ArrayList<ItemStack> getItens() {
        return itens;
    }

    public ArrayList<ItemStack> getBlocks() {
        return blocks;
    }

    public boolean hasItem(ItemStack item) {
        if (hasItens()) {
            List<ItemStack> r_1 = getItens().stream().filter(i -> i.getType() == item.getType()).collect(Collectors.toList());
            return r_1.size() > 0;
        } else {
            return true;
        }
    }

    public boolean hasBlock(ItemStack item) {
        if (hasBlocks()) {
            List<ItemStack> r_1 = getBlocks().stream().filter(i -> i.getType() == item.getType()).collect(Collectors.toList());
            return r_1.size() > 0;
        } else {
            return true;
        }
    }

}
