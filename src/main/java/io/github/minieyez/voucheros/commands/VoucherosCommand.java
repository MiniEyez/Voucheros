package io.github.minieyez.voucheros.commands;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class VoucherosCommand implements TabExecutor {

    private Set<SubCommand> subCommands;
    private VoucherosPlugin voucherosPlugin;

    public VoucherosCommand(VoucherosPlugin voucherosPlugin) {
        this.subCommands = new HashSet<>();
        this.voucherosPlugin = voucherosPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender.hasPermission("voucheros.help")) {
                sender.sendMessage(voucherosPlugin.getMessages().get("help"));
            }else{
                sender.sendMessage(voucherosPlugin.getMessages().get("permission-denied"));
            }
            return true;
        }

        subCommands.forEach((subCommand -> {
            if (subCommand.getName().equalsIgnoreCase(args[0])) {
                if (sender.hasPermission("voucheros." + subCommand.getName())) {
                    subCommand.execute(sender, command, label, args);
                }else{
                    sender.sendMessage(voucherosPlugin.getMessages().get("permission-denied"));
                }
                return;
            }
        }));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> suggestions = new ArrayList<>();
        switch (args.length) {
            case 1:
                subCommands.forEach(subCommand -> {
                    if (sender.hasPermission("voucheros." + subCommand.getName())) {
                        suggestions.add(subCommand.getName());
                    }
                });
                break;
            case 2:
                switch (args[0].toLowerCase()) {
                    case "give", "setcommand", "setdisplayname", "remove":
                        if (sender.hasPermission("voucheros." + args[0].toLowerCase())) {
                            if (voucherosPlugin.getConfig().getConfigurationSection("items") == null) {
                                break;
                            }
                            voucherosPlugin.getConfig().getConfigurationSection("items").getKeys(false).forEach(item -> suggestions.add(item));
                            break;
                        }
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("give")) {
                    if (sender.hasPermission("voucheros." + args[0].toLowerCase())) {
                        Bukkit.getOnlinePlayers().forEach(player -> suggestions.add(player.getName()));
                    }
                }
                break;
        }
        Collections.sort(suggestions);
        return suggestions;
    }

    public void addCommand(SubCommand subCommand) {
        subCommands.add(subCommand);
    }

    public Set<SubCommand> getSubCommands() {
        return subCommands;
    }
}
