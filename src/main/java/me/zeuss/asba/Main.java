package me.zeuss.asba;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.zeuss.asba.listeners.IslandInterract;
import me.zeuss.asba.manager.ItemLoader;
import me.zeuss.asba.utilities.ConfigFile;
import me.zeuss.asba.utilities.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ASkyBlockAPI api = null;
    private ConfigFile config;
    private ItemLoader loader;

    @Override
    public void onEnable() {
        this.config = new ConfigFile("config.yml", this);
        Plugin plugin = Bukkit.getPluginManager().getPlugin("ASkyblock");
        if (plugin == null || !plugin.isEnabled()) {
            Bukkit.getConsoleSender().sendMessage(Utils.textFormat("&4[Erro]: &cPlugin ASkyblock Não encontrado."));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        } else {
            api = ASkyBlockAPI.getInstance();
            Bukkit.getConsoleSender().sendMessage(Utils.textFormat("&2[Hook]: &aPlugin ASkyblock encontrado, hook ativado."));
        }
        loader = new ItemLoader();
        new IslandInterract();
    }

    @Override
    public void onDisable() {
    }

    public static Main getPlugin() {
        return Main.getPlugin(Main.class);
    }

    public ASkyBlockAPI getApi() {
        return this.api;
    }

    public ConfigFile getConfig() {
        return this.config;
    }

    public ItemLoader getLoader() {
        return this.loader;
    }

}
