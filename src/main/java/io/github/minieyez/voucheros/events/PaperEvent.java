package io.github.minieyez.voucheros.events;

import io.github.minieyez.voucheros.VoucherosPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PaperEvent implements Listener {

    private VoucherosPlugin voucherosPlugin;
    private Map<String, ItemStack> items;

    public PaperEvent(VoucherosPlugin voucherosPlugin) {
        this.voucherosPlugin = voucherosPlugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            initItems();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            if(items.containsValue(itemInHand)){
                event.setCancelled(true);
                String key = getKeyByValue(items, itemInHand);
                if(voucherosPlugin.getConfig().getString("items."+key+".command") == null){
                    player.sendMessage(voucherosPlugin.getMessages().get("error-occured"));
                    return;
                }
                String command = voucherosPlugin.getConfig().getString("items."+key+".command");
                command = command.replace("%player%", player.getName());
                player.getInventory().remove(itemInHand);
                voucherosPlugin.getLogger().info(String.format("Player \'%s\' used the paper item \'%s\' executing \'%s\'", player.getName(), key, command));
                voucherosPlugin.getServer().dispatchCommand(voucherosPlugin.getServer().getConsoleSender(), command);

            }
        }

    }

    private void initItems() {
        items = new HashMap<>();
        if (voucherosPlugin.getConfig().getConfigurationSection("items") == null) {
            return;
        }
        voucherosPlugin.getConfig().getConfigurationSection("items").getKeys(false).forEach(key -> items.put(key, voucherosPlugin.getConfig().getItemStack("items."+key+".item")));
    }

    private <String, ItemStack> String getKeyByValue(Map<String, ItemStack> map, ItemStack value) {
        for (Map.Entry<String, ItemStack> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
