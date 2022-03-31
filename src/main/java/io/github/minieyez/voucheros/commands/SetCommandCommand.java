package io.github.minieyez.voucheros.commands;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SetCommandCommand extends SubCommand {

    private VoucherosPlugin voucherosPlugin;

    public SetCommandCommand(VoucherosPlugin voucherosPlugin) {
        super("setcommand");
        this.voucherosPlugin = voucherosPlugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            String itemCommand = Arrays.stream(args).skip(2).filter(arg -> arg != null).collect(Collectors.joining(" "));
            String paperName = args[1];
            if (voucherosPlugin.getConfig().getConfigurationSection("items." + paperName) == null) {
                sender.sendMessage(voucherosPlugin.getMessages().get("item-doesnt-exist", args[1]));
                return;
            }
            voucherosPlugin.getConfig().set("items." + paperName + ".command", itemCommand);
            sender.sendMessage(voucherosPlugin.getMessages().get("command-set", itemCommand, paperName));
            voucherosPlugin.saveConfig();
            return;
        }
        sender.sendMessage(voucherosPlugin.getMessages().get("incorrect-params"));
        return;
    }
}
