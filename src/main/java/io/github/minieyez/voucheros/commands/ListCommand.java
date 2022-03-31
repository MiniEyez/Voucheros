package io.github.minieyez.voucheros.commands;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListCommand extends SubCommand {

    private VoucherosPlugin voucherosPlugin;

    public ListCommand(VoucherosPlugin voucherosPlugin) {
        super("list");
        this.voucherosPlugin = voucherosPlugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {

            if(voucherosPlugin.getConfig().getConfigurationSection("items") == null){
                sender.sendMessage(voucherosPlugin.getMessages().get("no-items"));
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(voucherosPlugin.getMessages().get("list-header") + "\n");
            for (String key : voucherosPlugin.getConfig().getConfigurationSection("items").getKeys(false)) {
                String itemName = key;
                if(voucherosPlugin.getConfig().getItemStack("items." + key + ".item") == null){
                    sender.sendMessage(voucherosPlugin.getMessages().get("error-occured"));
                    return;
                }
                ItemStack item = voucherosPlugin.getConfig().getItemStack("items." + key + ".item");
                ItemMeta itemMeta = item.getItemMeta();
                String displayName = itemMeta.getDisplayName();
                String uuid = itemMeta.getLocalizedName();
                String commandString = "null";
                if(voucherosPlugin.getConfig().getString("items." + key + ".command") != null){
                    commandString = voucherosPlugin.getConfig().getString("items." + key + ".command");
                }
                stringBuilder.append(voucherosPlugin.getMessages().get("list-format", itemName, displayName, commandString, uuid) + "\n");
            }
            sender.sendMessage(stringBuilder.toString());
            return;
        }
        sender.sendMessage(voucherosPlugin.getMessages().get("incorrect-params"));
        return;
    }
}
