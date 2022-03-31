package io.github.minieyez.voucheros.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    protected final String name;

    protected SubCommand(String name) {
        this.name = name;
    }

    public abstract void execute(CommandSender sender, Command command, String label, String[] args);

    public String getName() {
        return name;
    }

}
