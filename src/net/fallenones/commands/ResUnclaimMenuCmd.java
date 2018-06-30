package net.fallenones.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.fallenones.cityworld.Main;
import net.fallenones.cityworld.WorldGuardHandler;
import net.fallenones.command.CommandInterface;
import net.fallenones.menu.IconMenu;

public class ResUnclaimMenuCmd implements CommandInterface
{
	private static WorldGuardHandler wgHandler = Main.getWgHandler();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		Player p = (Player) sender;
		ProtectedRegion region;
    	World world = p.getWorld();
    	RegionManager regionManager = wgHandler.getWG().getRegionManager(world);
    	
		int i;
		//String[] residences = new String[4];

		//We don't have to check if the args length is equal to one, but you will have to check if it is greater than 1.
		if(args.length > 1) return false;

		if(p.hasPermission("cityworld.res.unclaim"))
		{
			IconMenu resmenu = new IconMenu("Unclaim a residence", 9, new IconMenu.OptionClickEventHandler() 
			 {
		            @Override
		            public void onOptionClick(IconMenu.OptionClickEvent event) 
		            {
		            	event.getPlayer().sendMessage("You have chosen " + event.getName());
		                event.getPlayer().performCommand(event.getCmd());
		                event.setWillClose(true);
		                event.setWillDestroy(true);
		            }
			 });
			
			Map<String, ProtectedRegion> regions = regionManager.getRegions();
			List<String> residences = new ArrayList<String>();
			
			for (Entry<String, ProtectedRegion> key : regions.entrySet())
    		{
    			region = regionManager.getRegion(key.getKey());
    			DefaultDomain RegionOwner = region.getOwners();
    			
    			if (region.hasMembersOrOwners() && RegionOwner.contains(p.getName()))
    			{
    				residences.add(key.getKey().toString());
    			}
    		}
	    	
			for (i=0; i < residences.size(); i++)
	    	{
	    		region = regionManager.getRegion(residences.get(i));
				DefaultDomain RegionOwner = region.getOwners();
				
				if (region.hasMembersOrOwners() && RegionOwner.contains(p.getName()))
				{
					resmenu.setOption(i, new ItemStack(Material.GRASS, 1), residences.get(i), "res unclaim " + residences.get(i), "Unclaim this residence!");
				}
	    	}
			resmenu.open(p);
			return true;
		}
		else
		{
			p.sendMessage("You do not have the cityworld.res.unclaim permission!");
			return true;
		}
	}
}
