package fr.lediamantrouge.actionbyip.event;

import fr.lediamantrouge.actionbyip.Main;
import fr.lediamantrouge.actionbyip.model.Action;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class JoinEvent implements Listener {

    HashMap<String, String> ip = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(ServerConnectEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if (ip.containsKey(player.getPendingConnection().getVirtualHost().getHostString())) {
            String host = ip.get(player.getPendingConnection().getVirtualHost().getHostString());
            Action action = Main.getInstance().getActionManager().getAction(host);
            if (action != null) {
                if (action.getType().getName().equals("server") && action.getArgs().size() == 1) {
                    ServerInfo serverInfo = Main.getInstance().getProxy().getServerInfo(action.getArgs().get(0));
                    e.setTarget(serverInfo);
                } else if (action.getType().getName().equals("command") && action.getArgs().size() == 2) {
                    if (action.getArgs().get(0).equals("console")) {
                        Main.getInstance().getProxy().getPluginManager().dispatchCommand(Main.getInstance().getProxy().getConsole(),
                                action.getArgs().get(1)
                                        .replace("%player_name%", player.getName()));
                    } else if (action.getArgs().get(0).equals("player")) {
                        Main.getInstance().getProxy().getPluginManager().dispatchCommand(player,
                                action.getArgs().get(1)
                                .replace("%player_name%", player.getName()));
                    }
                }
                ip.remove(player.getPendingConnection().getVirtualHost().getHostString());
            }
        }
    }

    @EventHandler
    public void onHandshake(PlayerHandshakeEvent e) {
        if (e.getHandshake().getHost() != null) {
            ip.put(e.getConnection().getVirtualHost().getHostString(), e.getHandshake().getHost());
        }
    }
}
