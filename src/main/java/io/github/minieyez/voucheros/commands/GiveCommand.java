package io.github.minieyez.voucheros.commands;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand extends SubCommand {

    private VoucherosPlugin voucherosPlugin;

    public GiveCommand(VoucherosPlugin voucherosPlugin) {
        super("give");
        this.voucherosPlugin = voucherosPlugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(voucherosPlugin.getMessages().get("not-player"));
                return;
            }
            Player player = (Player) sender;
            if (voucherosPlugin.getConfig().getConfigurationSection("items." + args[1]) == null) {
                player.sendMessage(voucherosPlugin.getMessages().get("item-doesnt-exist", args[1]));
                return;
            }
            player.getInventory().addItem(voucherosPlugin.getConfig().getItemStack("items." + args[1] + ".item"));
            player.sendMessage(voucherosPlugin.getMessages().get("success-recieved", args[1]));
            return;
        }
        if (args.length == 3) {
            if (voucherosPlugin.getConfig().getConfigurationSection("items." + args[1]) == null) {
                sender.sendMessage(voucherosPlugin.getMessages().get("item-doesnt-exist", args[1]));
                return;
            }
            Player target = null;
            if (Bukkit.getPlayerExact(args[2]) == null) {
                sender.sendMessage(voucherosPlugin.getMessages().get("target-doesnt-exist", args[2]));
                return;
            }
            target = Bukkit.getPlayerExact(args[2]);
            target.getInventory().addItem(voucherosPlugin.getConfig().getItemStack("items." + args[1] + ".item"));
            target.sendMessage(voucherosPlugin.getMessages().get("success-recieved", args[1]));
            sender.sendMessage(voucherosPlugin.getMessages().get("success-given", args[1], args[2]));
            return;
        }
        sender.sendMessage(voucherosPlugin.getMessages().get("incorrect-params"));
        return;
    }
}
