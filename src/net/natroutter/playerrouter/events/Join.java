package net.natroutter.playerrouter.events;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.natroutter.playerrouter.PlayerRouter;
import net.natroutter.playerrouter.handlers.LitebansHandler;
import net.natroutter.playerrouter.utilities.ConfigManager;

public class Join implements Listener {
    private static final LitebansHandler lbh = PlayerRouter.getLitebans();

    private static final ConfigManager config = PlayerRouter.getCfg();

    @EventHandler(priority = 64)
    public void onJoin(ServerConnectEvent e) {
        ProxiedPlayer p = e.getPlayer();

        if (config.getGeneral().getUsePurgatory() && lbh.isBanned(p.getUniqueId())) {
            ServerInfo purgatory = ProxyServer.getInstance().getServerInfo(config.getGeneral().getPurgatoryServer());
            if (purgatory != null) {
                e.setTarget(purgatory);
                return;
            }
        }
        ServerInfo targetServer = null;
        for (Object obj : config.getGeneral().getBalancedServers()) {
            ServerInfo server = ProxyServer.getInstance().getServerInfo(obj.toString());
            if (server == null) {
                continue;
            }
            if (targetServer == null) {
                targetServer = server;
                continue;
            }
            if (server.getPlayers().size() < targetServer.getPlayers().size()) {
                targetServer = server;
            }
        }
        if (targetServer != null) {
            e.setTarget(targetServer);
        }
    }
}
