package net.fallenones.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fallenones.cityworld.Main;
import net.fallenones.cityworld.WorldGuardHandler;
import net.fallenones.command.CommandInterface;

public class SetCityWorldCmd implements CommandInterface {
	
	private static WorldGuardHandler wgHandler;
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		Player p = (Player) sender;

		//We don't have to check if the args length is equal to one, but you will have to check if it is greater than 1.
		if(args.length > 1) return false;

		if(p.hasPermission("cityworld.admin.setup"))
		{
			wgHandler = Main.getWgHandler();
			wgHandler.setCityWorld(sender);
			//wgHandler.resetResFlags(sender,"__global__");
			return true;
		}
		else
		{
			p.sendMessage("You do not have the cityworld.admin.setup permission!");
			return true;
		}
		//return false;
	}
}
