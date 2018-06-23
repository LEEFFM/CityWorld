package net.fallenones.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.fallenones.cityworld.Main;
import net.fallenones.cityworld.WorldGuardHandler;
import net.fallenones.command.CommandInterface;

public class CreateCmd implements CommandInterface
{
	private static WorldGuardHandler wgHandler;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		Player p = (Player) sender;

		//We don't have to check if the args length is equal to one, but you will have to check if it is greater than 1.
		if(args.length > 2) return false;
		
		if (args.length > 1)
		{
		
			if(p.hasPermission("cityworld.res.create"))
			{
				wgHandler = Main.getWgHandler();
				wgHandler.create(sender, args[1]);
				return true;
			}
			else
			{
				p.sendMessage("You do not have the cityworld.res.create permission!");
				return true;
			}
		}
		return false;
	}

}
