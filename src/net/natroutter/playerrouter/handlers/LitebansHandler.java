package net.natroutter.playerrouter.handlers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import net.md_5.bungee.api.plugin.Plugin;
import net.natroutter.playerrouter.utilities.ConfigManager;

public class LitebansHandler {

    private Plugin pl;
    private ConfigManager.Litebans db;
    private HikariConfig hikConfig;
    private HikariDataSource hikData;

    private boolean valid = false;

    public boolean getValid() {
        return this.valid;
    }

    public LitebansHandler(Plugin pl, ConfigManager.Litebans db) {
        this.pl = pl;
        this.db = db;
        if (db.getHost().length() < 1 || db.getUser().length() < 1 || db.getPass().length() < 1 || db.getName().length() < 1) {
            pl.getLogger().info("\n[PlayerRouter][LitebansHandler] Invalid Database credentials you are missing host, user, pass or name\n");
            return;
        }
        this.hikConfig = new HikariConfig();
        this.hikConfig.setJdbcUrl("jdbc:mysql://" + db.getHost() + ":" + db.getPort() + "/" + db.getName());
        this.hikConfig.setUsername(db.getUser());
        if (db.getPass().length() > 1) {
            this.hikConfig.setPassword(db.getPass());
        }
        this.hikConfig.addDataSourceProperty("cachePrepStmts", "true");
        this.hikConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        this.hikConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.hikConfig.setPoolName("LiteBansHandler");
        this.hikData = new HikariDataSource(this.hikConfig);
        this.valid = true;
    }

    public boolean isBanned(UUID uuid) {
        if (!valid) {return false;}

        String sql = "SELECT * FROM " + db.getPrefix() + "bans WHERE uuid=? AND active=1";

        try (Connection con = hikData.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, uuid.toString());
            ResultSet rs = st.executeQuery();

            return rs.next();
        } catch (Exception e) {
            pl.getLogger().info("[PlayerRouter][LitebansHandler] Failed to retrive data from database for UUID: " + uuid.toString());
            e.printStackTrace();
        }
        return false;
    }

}
