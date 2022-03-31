package io.github.minieyez.voucheros.commands;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RemoveCommand extends SubCommand {

    private VoucherosPlugin voucherosPlugin;

    public RemoveCommand(VoucherosPlugin voucherosPlugin) {
        super("remove");
        this.voucherosPlugin = voucherosPlugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            String paperName = args[1];
            if (voucherosPlugin.getConfig().getConfigurationSection("items." + paperName) == null) {
                sender.sendMessage(voucherosPlugin.getMessages().get("item-doesnt-exist", args[1]));
                return;
            }
            voucherosPlugin.getConfig().set("items." + paperName, null);
            sender.sendMessage(voucherosPlugin.getMessages().get("item-deleted", args[1]));
            voucherosPlugin.saveConfig();
            return;
        }
        sender.sendMessage(voucherosPlugin.getMessages().get("incorrect-params"));
        return;
    }
}
