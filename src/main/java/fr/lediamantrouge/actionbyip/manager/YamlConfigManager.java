package fr.lediamantrouge.actionbyip.manager;

import com.google.common.io.ByteStreams;
import fr.lediamantrouge.actionbyip.Main;
import fr.lediamantrouge.actionbyip.enumeration.Type;
import fr.lediamantrouge.actionbyip.model.Action;
import lombok.SneakyThrows;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

public class YamlConfigManager implements IConfigManager {

    @SneakyThrows
    @Override
    public void loadConfig() {
        File file = new File(Main.getInstance().getDataFolder(), "actions.yml");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            try (InputStream is = Main.getInstance().getResourceAsStream("actions.yml");
                 OutputStream os = Files.newOutputStream(file.toPath())) {
                ByteStreams.copy(is, os);
            }
        }

        Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Main.getInstance().getDataFolder(), "actions.yml"));


        if (configuration.getSection("actions") == null) return;

        for (String ip : configuration.getSection("actions").getKeys()) {
            String type = configuration.getString("actions." + ip + ".type");
            String executor = configuration.getString("actions." + ip + ".executor");
            String name = configuration.getString("actions." + ip + ".name");
            String cmd = configuration.getString("actions." + ip + ".cmd");

            Action action = null;

            if (type.equals("server")) {
                action = new Action(ip.replace("_", "."), Type.SERVER, Collections.singletonList(name));
            } else if (type.equals("command")) {
                action = new Action(ip.replace("_", "."), Type.COMMAND, Arrays.asList(executor, cmd));
            }

            if (action == null) {
                Main.getInstance().getLogger().severe("Cannot found server (" + type + ") type for : " + ip);
            }

            Main.getInstance().getActionManager().createAction(action);
        }
    }
}
