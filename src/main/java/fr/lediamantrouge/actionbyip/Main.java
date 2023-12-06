package fr.lediamantrouge.actionbyip;

import fr.lediamantrouge.actionbyip.event.JoinEvent;
import fr.lediamantrouge.actionbyip.manager.ActionManager;
import fr.lediamantrouge.actionbyip.manager.IActionManager;
import fr.lediamantrouge.actionbyip.manager.IConfigManager;
import fr.lediamantrouge.actionbyip.manager.YamlConfigManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Logger;

@Getter
public final class Main extends Plugin {

    @Getter
    private static Main instance;

    IActionManager actionManager;
    IConfigManager configManager;
    Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        actionManager = new ActionManager();
        configManager = new YamlConfigManager();

        configManager.loadConfig();

        Main.getInstance().getProxy().getPluginManager().registerListener(this, new JoinEvent());
    }

    @Override
    public void onDisable() {

    }
}
