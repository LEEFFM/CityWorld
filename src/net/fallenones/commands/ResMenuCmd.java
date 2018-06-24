package net.fallenones.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.fallenones.cityworld.Main;
import net.fallenones.command.CommandInterface;
import net.fallenones.menu.IconMenu;

public class ResMenuCmd implements CommandInterface
{
	private Main plugin = Main.getPlugin(Main.class);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		Player p = (Player) sender;

		//We don't have to check if the args length is equal to one, but you will have to check if it is greater than 1.
		if(args.length > 1) return false;

		if(p.hasPermission("cityworld.res.cp"))
		{
			IconMenu resmenu = new IconMenu("Residence CP", 9, new IconMenu.OptionClickEventHandler() 
			 {
		            @Override
		            public void onOptionClick(IconMenu.OptionClickEvent event) 
		            {
		                event.getPlayer().sendMessage("You have chosen " + event.getName());
		                event.setWillDestroy(true);
		            }
			 }, plugin)
		        .setOption(0, new ItemStack(Material.GRASS, 1), "Claim", "res claim", "Claim the next availabe residence!")
		        .setOption(1, new ItemStack(Material.ROTTEN_FLESH, 1), "Unclaim", "res unclaim", "Unclaim your residence! ")
		        .setOption(2, new ItemStack(Material.EMERALD, 1), "Money", "", "Money brings happiness");
			
			resmenu.open(p);
			return true;
		}
		else
		{
			p.sendMessage("You do not have the cityworld.res.cp permission!");
			return true;
		}
	}
}
