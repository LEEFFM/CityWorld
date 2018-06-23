package net.fallenones.command;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResidenceCommandHandler implements CommandExecutor
{
	//This is where we will store the commands
	private static HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();

	//Register method. When we register commands in our onEnable() we will use this.
	public void register(String name, CommandInterface cmd) 
	{
		//When we register the command, this is what actually will put the command in the hashmap.
		commands.put(name, cmd);
	}

	//This will be used to check if a string exists or not.
	public boolean exists(String name) 
	{
		//To actually check if the string exists, we will return the hashmap
		return commands.containsKey(name);
	}

	//Getter method for the Executor.
	public CommandInterface getExecutor(String name) 
	{
		//Returns a command in the hashmap of the same name.
		return commands.get(name);
	}

	//This will be a template. All commands will have this in common.
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		//For example, in each command, it will check if the sender is a player and if not, send an error message.
		if(sender instanceof Player) 
		{
			//What if there are arguments in the command? Such as /example args
			if(args.length > 0) 
			{
				//If that argument exists in our registration in the onEnable();
				if(exists(args[0]))
				{
					//the command /res args, args is our args[0].
					getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
					return true;
				}
				else
				{
					//We want to send a message to the sender if the command doesn't exist.
					sender.sendMessage("The command /residence " + args[0] + " doesn't exist!");
					return true;
				}
			}
		} 
		else 
		{
			sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
			return true;
		}
		return false;
	}
}
