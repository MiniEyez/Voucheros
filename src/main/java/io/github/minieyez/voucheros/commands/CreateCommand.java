package io.github.minieyez.voucheros.commands;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class CreateCommand extends SubCommand {

    private VoucherosPlugin voucherosPlugin;

    public CreateCommand(VoucherosPlugin voucherosPlugin) {
        super("create");
        this.voucherosPlugin = voucherosPlugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            String paperName = args[1];

            if(voucherosPlugin.getConfig().getConfigurationSection("items."+paperName) != null){
                sender.sendMessage(voucherosPlugin.getMessages().get("already-exists", paperName));
                return;
            }
            ItemStack paper = new ItemStack(Material.PAPER);
            ItemMeta paperMeta = paper.getItemMeta();
            UUID paperUUID = UUID.randomUUID();
            paperMeta.setLocalizedName(String.valueOf(paperUUID));
            paperMeta.setDisplayName(paperName);
            paper.setItemMeta(paperMeta);
            voucherosPlugin.getConfig().set("items." + paperName + ".item", paper);
            voucherosPlugin.getConfig().set("items." + paperName + ".uuid", String.valueOf(paperUUID));
            voucherosPlugin.getConfig().set("items." + paperName + ".creator", sender.getName());

            voucherosPlugin.saveConfig();

            sender.sendMessage(voucherosPlugin.getMessages().get("created", paperName, String.valueOf(paperUUID)));
            return;
        }
        sender.sendMessage(voucherosPlugin.getMessages().get("incorrect-params"));
        return;
    }
}
