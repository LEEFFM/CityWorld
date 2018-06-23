package net.fallenones.cityworld;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.fallenones.command.CityWorldCommandHandler;
import net.fallenones.command.ResidenceCommandHandler;
import net.fallenones.commands.CityWorldCmd;
import net.fallenones.commands.ClaimCmd;
import net.fallenones.commands.CreateCmd;
import net.fallenones.commands.CreateSpawnCmd;
import net.fallenones.commands.RemoveAllRegionsCmd;
import net.fallenones.commands.RemoveAllResidencesCmd;
import net.fallenones.commands.ResCmd;
import net.fallenones.commands.ResListCmd;
import net.fallenones.commands.SetCityWorldCmd;
import net.fallenones.commands.UnclaimCmd;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

public class Main extends JavaPlugin implements Listener
{
	private static WorldGuardHandler wgHandler;
	private static WorldEditHandler weHandler;
	
	@Override
    public void onEnable()
	{
		// if WorldGuard is not found, disable CityWorld!
		if (getWorldGuard() == null) 
		{
            getLogger().warning("WorldGuard not found! Please ensure you have the correct version of WorldGuard in order to use CityWorld!");
            getPluginLoader().disablePlugin(this);
            return;
        } 
		else
		{
			wgHandler = new WorldGuardHandler(getWorldGuard());
		}
		
		// if WorldEdit is not found, disable CityWorld!
		if (getWorldEdit() == null) 
		{
            getLogger().warning("WorldEdit not found! Please ensure you have the correct version of WorldEdit in order to use CityWorld!");
            getPluginLoader().disablePlugin(this);
            return;
        }
		else
		{
			weHandler = new WorldEditHandler(getWorldEdit());
		}
		
		// register plugin commands
		this.registerCommands();
    }
	
	private WorldGuardPlugin getWorldGuard()
	{
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
        
        if (!(plugin instanceof WorldGuardPlugin))
        {
            return null;
        }
        
        return (WorldGuardPlugin) plugin;
    }
	
	public WorldEditPlugin getWorldEdit()
	{
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");
        
        if (!(plugin instanceof WorldEditPlugin))
        {
            return null;
        }
        
        return (WorldEditPlugin) plugin;
    }
	
	public static WorldGuardHandler getWgHandler()
	{
        return wgHandler;
    }
	
	public static WorldEditHandler getWeHandler()
	{
        return weHandler;
    }
	
	//Because there may be LOTS of commands, we are going to make a separate method to keep
    //the onEnable() nice and clean.
	public void registerCommands() 
    {
    	//For convenience' sake, we will initialize a variable.
        CityWorldCommandHandler cwhandler = new CityWorldCommandHandler();
        ResidenceCommandHandler reshandler = new ResidenceCommandHandler();
        
        //Register /cityworld base command
        cwhandler.register("cityworld", new CityWorldCmd());
        
        //Register /residence base command
        reshandler.register("res", new ResCmd());
        
        //Register /cityworld [command]
        cwhandler.register("setup", new SetCityWorldCmd());
        
        //Register /res [command]
        reshandler.register("claim", new ClaimCmd());
        reshandler.register("unclaim", new UnclaimCmd());
        reshandler.register("create", new CreateCmd());
        reshandler.register("createspawn", new CreateSpawnCmd());
        reshandler.register("list", new ResListCmd());
        reshandler.register("removeallregions", new RemoveAllRegionsCmd());
        reshandler.register("removeallresidences", new RemoveAllResidencesCmd());
        
        getCommand("cityworld").setExecutor(cwhandler);
        getCommand("res").setExecutor(reshandler);
    }
}
