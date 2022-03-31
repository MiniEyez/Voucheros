package io.github.minieyez.voucheros;

import io.github.minieyez.voucheros.commands.*;
import io.github.minieyez.voucheros.events.PaperEvent;
import io.github.minieyez.voucheros.util.Messages;
import org.bukkit.plugin.java.JavaPlugin;

public class VoucherosPlugin extends JavaPlugin {

    private Messages messages;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        messages = new Messages(this);
        registerCommands();
        getServer().getPluginManager().registerEvents(new PaperEvent(this), this);

    }

    private void registerCommands(){
        VoucherosCommand voucherosCommand = new VoucherosCommand(this);
        voucherosCommand.addCommand(new CreateCommand(this));
        voucherosCommand.addCommand(new GiveCommand(this));
        voucherosCommand.addCommand(new RemoveCommand(this));
        voucherosCommand.addCommand(new SetCommandCommand(this));
        voucherosCommand.addCommand(new SetDisplaynameCommand(this));
        voucherosCommand.addCommand(new ListCommand(this));

        this.getCommand("voucheros").setTabCompleter(voucherosCommand);
        this.getCommand("voucheros").setExecutor(voucherosCommand);
    }

    public Messages getMessages() {
        return messages;
    }
}
