package io.github.minieyez.voucheros.util;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

public class Messages {

    private VoucherosPlugin voucherosPlugin;
    private Map<String, String> configMessages;


    public Messages(VoucherosPlugin voucherosPlugin) {
        this.voucherosPlugin = voucherosPlugin;
        initConfigMessages();
    }

    private void initConfigMessages() {
        ConfigurationSection messagesSection = voucherosPlugin.getConfig().getConfigurationSection("messages");
        configMessages = messagesSection.getKeys(false).stream().collect(Collectors.toMap(key -> key, key -> messagesSection.getString(key)));
    }

    public String get(String key) {
        try {
            return ChatColor.translateAlternateColorCodes('&', configMessages.get(key));
        } catch (Exception exception) {
            return key;
        }
    }

    public String get(String key, Object... objects) {
        String message = get(key);
        try {
            MessageFormat messageFormat = new MessageFormat(message);
            return messageFormat.format(objects);
        } catch (Exception exception) {
            return message;
        }

    }
}
