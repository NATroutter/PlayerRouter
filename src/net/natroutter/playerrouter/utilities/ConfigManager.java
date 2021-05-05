package net.natroutter.playerrouter.utilities;

import de.leonhard.storage.Config;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class ConfigManager {

    private Litebans litebans;
    private General general;

    public ConfigManager(Config config) {
        this.litebans = new Litebans(config);
        this.general = new General(config);
    }

    public General getGeneral() {
        return this.general;
    }

    public Litebans getLiteBans() {
        return this.litebans;
    }

    public class General {
        private Config config;

        public General(Config config) {
            this.config = config;
            config.setDefault("General.BalancedServers", Arrays.asList("hub", "hub2"));
            config.setDefault("General.PurgatoryServer", "Purgatory");
            config.setDefault("General.UsePurgatory", true);
        }

        public List<?> getBalancedServers() {
            return this.config.getList("General.BalancedServers");
        }

        public String getPurgatoryServer() {
            return this.config.getString("General.PurgatoryServer");
        }

        public boolean getUsePurgatory() {
            return this.config.getBoolean("General.UsePurgatory");
        }
    }

    public class Litebans {
        private Config config;

        public Litebans(Config config) {
            this.config = config;
            config.setDefault("Litebans.Name", "");
            config.setDefault("Litebans.Host", "");
            config.setDefault("Litebans.Port", 3306);
            config.setDefault("Litebans.User", "");
            config.setDefault("Litebans.Pass", "");
            config.setDefault("Litebans.Prefix", "litebans_");
        }

        public String getName() {
            return this.config.getString("Litebans.Name");
        }

        public String getHost() {
            return this.config.getString("Litebans.Host");
        }

        public Integer getPort() {
            return this.config.getInt("Litebans.Port");
        }

        public String getUser() {
            return this.config.getString("Litebans.User");
        }

        public String getPass() {
            return this.config.getString("Litebans.Pass");
        }

        public String getPrefix() {
            return this.config.getString("Litebans.Prefix");
        }
    }
}
