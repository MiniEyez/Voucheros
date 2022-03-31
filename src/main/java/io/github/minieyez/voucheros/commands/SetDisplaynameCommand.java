package io.github.minieyez.voucheros.commands;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class SetDisplaynameCommand extends SubCommand{

    private VoucherosPlugin voucherosPlugin;

    public SetDisplaynameCommand(VoucherosPlugin voucherosPlugin) {
        super("setdisplayname");
        this.voucherosPlugin = voucherosPlugin;
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 2) {
            String displayName = Arrays.stream(args).skip(2).filter(Objects::nonNull).collect(Collectors.joining(" "));
            String paperName = args[1];
            if (voucherosPlugin.getConfig().getConfigurationSection("items." + paperName) == null) {
                sender.sendMessage(voucherosPlugin.getMessages().get("item-doesnt-exist", args[1]));
                return;
            }

            ItemStack paper = voucherosPlugin.getConfig().getItemStack("items."+paperName+".item");
            ItemMeta paperMeta = paper.getItemMeta();
            paperMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
            paper.setItemMeta(paperMeta);
            voucherosPlugin.getConfig().set("items."+paperName+".item", paper);
            sender.sendMessage(voucherosPlugin.getMessages().get("displayname-set", displayName, paperName));
            voucherosPlugin.saveConfig();
            return;
        }
        sender.sendMessage(voucherosPlugin.getMessages().get("incorrect-params"));
        return;
    }
}
