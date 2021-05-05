package net.natroutter.playerrouter;


import de.leonhard.storage.Config;
import de.leonhard.storage.Yaml;
import net.md_5.bungee.api.plugin.Plugin;
import net.natroutter.playerrouter.events.Join;
import net.natroutter.playerrouter.handlers.LitebansHandler;
import net.natroutter.playerrouter.utilities.ConfigManager;

public class PlayerRouter extends Plugin {

    private static ConfigManager cfg;
    private static LitebansHandler lbh;

    public static ConfigManager getCfg() {
        return cfg;
    }

    public static LitebansHandler getLitebans() {
        return lbh;
    }

    public void onEnable() {
        Config config = new Config("config", "plugins/PlayerRouter");
        cfg = new ConfigManager(config);
        lbh = new LitebansHandler(this, cfg.getLiteBans());
        getProxy().getPluginManager().registerListener(this, new Join());
    }
}