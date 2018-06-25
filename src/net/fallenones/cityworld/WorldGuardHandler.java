package net.fallenones.cityworld;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import net.fallenones.cityworld.flags.Flags;

public class WorldGuardHandler
{
	private WorldGuardPlugin wg;
	private WorldEditHandler weHandler;

    public WorldGuardHandler(WorldGuardPlugin wg)
    {
        this.wg = wg;
    }
    
    public WorldGuardPlugin getWG()
    {
        return wg;
    }
    
    public void setDefaultResFlags (CommandSender sender, String regionName, boolean bSpawnArea)
    {
    	ProtectedRegion region;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	region = regionManager.getRegion(regionName);
    	
    	if (!bSpawnArea)
    	{
    		region.setFlag(Flags.Spawn_Area, false);
    		// set flags that effect everyone here
    		region.setFlag (DefaultFlag.GREET_MESSAGE, "You are now entering " + player.getName() + "'s residence!" );
    		region.setFlag (DefaultFlag.FAREWELL_MESSAGE, "You are now leaving " + player.getName() + "'s residence!" );
    	
    		region.setFlag (DefaultFlag.CREEPER_EXPLOSION, State.DENY);
    		region.setFlag (DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE, State.DENY);
    		region.setFlag (DefaultFlag.GHAST_FIREBALL, State.DENY);
    		region.setFlag (DefaultFlag.OTHER_EXPLOSION, State.DENY);
    		region.setFlag (DefaultFlag.FIRE_SPREAD, State.DENY);
    		region.setFlag (DefaultFlag.LAVA_FIRE, State.DENY);
    		region.setFlag (DefaultFlag.LIGHTNING, State.DENY);
    		region.setFlag (DefaultFlag.SNOW_FALL, State.ALLOW);
    		region.setFlag (DefaultFlag.SNOW_MELT, State.ALLOW);
    		region.setFlag (DefaultFlag.ICE_FORM, State.ALLOW);
    		region.setFlag (DefaultFlag.ICE_MELT, State.ALLOW);
    		region.setFlag (DefaultFlag.MUSHROOMS, State.ALLOW);
    		region.setFlag (DefaultFlag.LEAF_DECAY, State.ALLOW);
    		region.setFlag (DefaultFlag.GRASS_SPREAD, State.ALLOW);
    		region.setFlag (DefaultFlag.MYCELIUM_SPREAD, State.ALLOW);
    		region.setFlag (DefaultFlag.VINE_GROWTH, State.ALLOW);
    		region.setFlag (DefaultFlag.SOIL_DRY, State.ALLOW);
    		region.setFlag (DefaultFlag.ENDER_BUILD, State.DENY);
    		region.setFlag (DefaultFlag.ENTITY_PAINTING_DESTROY, State.DENY);
    		region.setFlag (DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, State.DENY);
    		region.setFlag (DefaultFlag.FALL_DAMAGE, State.DENY);
    		region.setFlag (DefaultFlag.ENDERPEARL, State.DENY);
    	
    		// set residence owner flags here
    		region.setFlag (DefaultFlag.BUILD, State.ALLOW);
    		region.setFlag (DefaultFlag.BUILD.getRegionGroupFlag(), RegionGroup.OWNERS);
    	
    		region.setFlag (DefaultFlag.WATER_FLOW, State.ALLOW);
    		region.setFlag (DefaultFlag.WATER_FLOW.getRegionGroupFlag(), RegionGroup.OWNERS);
    	
    		region.setFlag (DefaultFlag.LAVA_FLOW, State.ALLOW);
    		region.setFlag (DefaultFlag.LAVA_FLOW.getRegionGroupFlag(), RegionGroup.OWNERS);

    		region.setFlag (DefaultFlag.MOB_SPAWNING, State.ALLOW);
    		region.setFlag (DefaultFlag.MOB_SPAWNING.getRegionGroupFlag(), RegionGroup.OWNERS);
    	
    		region.setFlag (DefaultFlag.PISTONS, State.ALLOW);
    		region.setFlag (DefaultFlag.PISTONS.getRegionGroupFlag(), RegionGroup.OWNERS);
    	
    		region.setFlag (DefaultFlag.INVINCIBILITY, State.ALLOW);
    		region.setFlag (DefaultFlag.INVINCIBILITY.getRegionGroupFlag(), RegionGroup.OWNERS);
    	}
    	else if (bSpawnArea)
    	{
    		region.setFlag (DefaultFlag.GREET_MESSAGE, "You are now entering " + regionName + "!" );
        	region.setFlag (DefaultFlag.FAREWELL_MESSAGE, "You are now leaving " + regionName + "!" );
    		region.setFlag (DefaultFlag.BUILD, State.DENY);
        	region.setFlag (DefaultFlag.INTERACT, State.ALLOW);
        	region.setFlag (DefaultFlag.MOB_SPAWNING, State.DENY);
        	region.setFlag (DefaultFlag.FALL_DAMAGE, State.DENY);
        	region.setFlag(Flags.Spawn_Area, true);
    	}
    }
    
    public void resetResFlags (CommandSender sender, String regionName, boolean bSpawnArea)
    {
    	ProtectedRegion region;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	
    	region = regionManager.getRegion(regionName);
    	
    	if (!bSpawnArea)
    	{
    		region.setFlag (DefaultFlag.BUILD, null);
    		region.setFlag(Flags.Spawn_Area, false);
    		region.setFlag (DefaultFlag.GREET_MESSAGE, "You are now entering " + regionName + "!" );
    		region.setFlag (DefaultFlag.FAREWELL_MESSAGE, "You are now leaving " + regionName + "!" );
    		region.setFlag (DefaultFlag.MOB_DAMAGE, null);
    		region.setFlag (DefaultFlag.MOB_SPAWNING, null);
    		region.setFlag (DefaultFlag.CREEPER_EXPLOSION, null);
    		region.setFlag (DefaultFlag.ENDERDRAGON_BLOCK_DAMAGE, null);
    		region.setFlag (DefaultFlag.GHAST_FIREBALL, null);
    		region.setFlag (DefaultFlag.OTHER_EXPLOSION, null);
    		region.setFlag (DefaultFlag.FIRE_SPREAD, null);
    		region.setFlag (DefaultFlag.LAVA_FIRE, null);
    		region.setFlag (DefaultFlag.LIGHTNING, null);
    		region.setFlag (DefaultFlag.WATER_FLOW, null);
    		region.setFlag (DefaultFlag.LAVA_FLOW, null);
    		region.setFlag (DefaultFlag.PISTONS, null);
    		region.setFlag (DefaultFlag.SNOW_FALL, null);
    		region.setFlag (DefaultFlag.SNOW_MELT, null);
    		region.setFlag (DefaultFlag.ICE_FORM, null);
    		region.setFlag (DefaultFlag.ICE_MELT, null);
    		region.setFlag (DefaultFlag.MUSHROOMS, null);
    		region.setFlag (DefaultFlag.LEAF_DECAY, null);
    		region.setFlag (DefaultFlag.GRASS_SPREAD, null);
    		region.setFlag (DefaultFlag.MYCELIUM_SPREAD, null);
    		region.setFlag (DefaultFlag.VINE_GROWTH, null);
    		region.setFlag (DefaultFlag.SOIL_DRY, null);
    		region.setFlag (DefaultFlag.ENDER_BUILD, null);
    		region.setFlag (DefaultFlag.INVINCIBILITY, null);
    		region.setFlag (DefaultFlag.SEND_CHAT, null);
    		region.setFlag (DefaultFlag.RECEIVE_CHAT, null);
    		region.setFlag (DefaultFlag.ENTRY, null);
    		region.setFlag (DefaultFlag.EXIT, null);
    		region.setFlag (DefaultFlag.ENDERPEARL, null);
    		region.setFlag (DefaultFlag.ENTITY_PAINTING_DESTROY, null);
    		region.setFlag (DefaultFlag.ENTITY_ITEM_FRAME_DESTROY, null);
    		region.setFlag (DefaultFlag.FALL_DAMAGE, null);
    	}
    	else if (bSpawnArea)
    	{
    		region.setFlag (DefaultFlag.GREET_MESSAGE, "You are now entering " + regionName + "!" );
        	region.setFlag (DefaultFlag.FAREWELL_MESSAGE, "You are now leaving " + regionName + "!" );
    		region.setFlag (DefaultFlag.BUILD, State.DENY);
        	region.setFlag (DefaultFlag.INTERACT, State.ALLOW);
        	region.setFlag (DefaultFlag.MOB_SPAWNING, State.DENY);
        	region.setFlag (DefaultFlag.FALL_DAMAGE, State.DENY);
        	region.setFlag(Flags.Spawn_Area, true);
    	}
    }
    
    public boolean RegionExsists (CommandSender sender, String regionName)
    {
    	ProtectedRegion region;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	
    	region = regionManager.getRegion(regionName);
    	
    	if (region != null)
    	{
    		player.sendMessage(regionName + " Already exsists!");
    		return true;
    	}
    	return false;
    }
    
    public BlockVector convertToWEBlockVector(Location location)
    {
    	return new BlockVector(location.getX(),location.getY(),location.getZ());
	}
    
    public void create (CommandSender sender, String regionName)
    {
    	ProtectedRegion region;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	Selection ResSelection;
    	Location Loc1;
    	Location Loc2;
    	BlockVector BV1;
    	BlockVector BV2;
    	
    	//boolean bSpawnArea = false;
    	
    	weHandler = Main.getWeHandler();
    	if (RegionExsists(sender,regionName)) return;

    	ResSelection = weHandler.getSelecion(player);
    	if (ResSelection == null)
    	{
    		player.sendMessage("Please select an area with the WorldEdit wand!");
    		return;
    	}
    	Loc1 = ResSelection.getMinimumPoint();
    	Loc2 = ResSelection.getMaximumPoint();
    	BV1 = convertToWEBlockVector(Loc1);
    	BV2 = convertToWEBlockVector(Loc2);
    	
    	region = new ProtectedCuboidRegion(regionName ,BV1,BV2);
    	
    	regionManager.addRegion(region);
    	
    	resetResFlags(sender,regionName,false);
    	
    	player.sendMessage("You have created " + regionName + "!");
    }
    
    public void setCityWorld (CommandSender sender)
    {
    	Player player = (Player)sender;
    	
    	File resFile = new File("plugins\\WorldGuard\\worlds\\" + player.getWorld().getName() + "\\config.yml");
    	
    	YamlConfiguration resYml = YamlConfiguration.loadConfiguration(resFile);
  
    	resYml.createSection("regions.high-frequency-flags");
    	resYml.createSection("regions.protect-against-liquid-flow");
    
		resYml.set("regions.high-frequency-flags",true);
		resYml.set("regions.protect-against-liquid-flow",true);
		
		try
        {
        	resYml.save("plugins\\WorldGuard\\worlds\\" + player.getWorld().getName() + "\\config.yml");
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rg flag __global__ build -w " + player.getWorld().getName() + " deny");
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rg flag __global__ fall-damage -w " + player.getWorld().getName() + " deny"); 
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rg flag __global__ mob-spawning -w " + player.getWorld().getName() + " deny"); 
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rg flag __global__ pvp -w " + player.getWorld().getName() + " deny"); 
    }
    
    public void createSpawn (CommandSender sender, String regionName)
    {
    	ProtectedRegion region;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	Selection ResSelection;
    	Location Loc1;
    	Location Loc2;
    	BlockVector BV1;
    	BlockVector BV2;
    	
    	weHandler = Main.getWeHandler();
    	if (RegionExsists(sender,regionName)) return;

    	ResSelection = weHandler.getSelecion(player);
    	if (ResSelection == null)
    	{
    		player.sendMessage("Please select an area with the WorldEdit wand!");
    		return;
    	}
    	Loc1 = ResSelection.getMinimumPoint();
    	Loc2 = ResSelection.getMaximumPoint();
    	BV1 = convertToWEBlockVector(Loc1);
    	BV2 = convertToWEBlockVector(Loc2);
    	
    	region = new ProtectedCuboidRegion(regionName ,BV1,BV2);
    	
    	regionManager.addRegion(region);
    	
    	setDefaultResFlags(sender,regionName,true);
    	
    	player.sendMessage("You have created " + regionName + "!");
    }

	public void claim (CommandSender sender, String regionName)
    {
    	ProtectedRegion region;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	
    	if (regionName != null)
    	{    	
    		region = regionManager.getRegion(regionName);
    	
    		if (region == null)
    		{
    			player.sendMessage(regionName + " Doesnt exsist!");
    			return;
    		}
    		
    		boolean bSpawnArea = region.getFlag(Flags.Spawn_Area);
    	
    		DefaultDomain RegionOwner = region.getOwners();
    	
    		if (region.hasMembersOrOwners())
    		{
    			player.sendMessage(regionName + " is already owned!, You cannot claim this residence!");
    			return;
    		}
    		else if (bSpawnArea)
    		{
    			player.sendMessage("You cannot claim " + regionName + " because it is a spawn area!");
    			return;
    		}
    		else
    		{
    			RegionOwner.addPlayer(player.getName());

    			region.setOwners(RegionOwner);
    			setDefaultResFlags(sender,regionName,false);
    		
    			player.sendMessage("You are now the owner of " + regionName + "!");
    		}
    	}
    	else
    	{
    		Map<String, ProtectedRegion> regions = regionManager.getRegions();
    		
    		for (Entry<String, ProtectedRegion> key : regions.entrySet())
    		{
    			region = regionManager.getRegion(key.getKey());
    			boolean bSpawnArea = region.getFlag(Flags.Spawn_Area);
    			
    			if (!region.hasMembersOrOwners() && !bSpawnArea)
    			{
    				DefaultDomain RegionOwner = region.getOwners();
    				
    				RegionOwner.addPlayer(player.getName());

        			region.setOwners(RegionOwner);
        			setDefaultResFlags(sender,key.getKey(),false);
        		
        			player.sendMessage("You are now the owner of " + key.getKey() + "!");
        			return;
    			}
    		}
    	}
    }
	
	@SuppressWarnings("deprecation")
	public void reslist (CommandSender sender)
	{
		ProtectedRegion region;
    	Player p = (Player)sender;
    	World world = p.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	
    	int i;
		
		//create the book
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) book.getItemMeta();
		
		//set the title and author of this book
		bookMeta.setTitle(p.getWorld().getName().toString() + " Residence List");
		bookMeta.setAuthor("Server");
		
		//create pages
		ArrayList<String> pages = new ArrayList<String>();
		Map<String, ProtectedRegion> regions = regionManager.getRegions();
		
		for (Entry<String, ProtectedRegion> key : regions.entrySet())
		{
			region = regionManager.getRegion(key.getKey().toString());
			//boolean bSpawnArea = region.getFlag(Flags.Spawn_Area);
			
			if (region.getTypeName() != "global" || !region.getId().contains("spawn"))
			{
				pages.add(key.getKey().toString());
			}
		}
		
		/*for (String key : pages)
		{
			//add the pages to the meta
			bookMeta.setPages(key);
		}*/
		
		/*for (Object key : pages.toArray())
		{
			bookMeta.setPages(key.toString());
		}*/
		
		//add the pages to the meta
		bookMeta.setPages(pages.toString());

		//update the ItemStack with this new meta
		book.setItemMeta(bookMeta);
		
		if (p.getInventory().firstEmpty() != -1)
		{
			p.getInventory().addItem(book);
		}
		else
		{
			p.sendMessage("cant give you residence list book, your inventory is full!");
		}
	}
	
	public void removeAllRegions(CommandSender sender)
	{
		Set<String> regions;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	YamlConfiguration resYml = new YamlConfiguration();
    	
    	regions = regionManager.getRegions().keySet();
    	
    	for (Object regionName : regions.toArray())
    	{
    		regionManager.removeRegion(regionName.toString());
    		resYml.createSection("residence");
    		try
    		{
				resYml.save("plugins/CityWorld/Residences.yml");
			}
    		catch (IOException e)
    		{
				e.printStackTrace();
			}
    		player.sendMessage(regionName.toString() + " has been deleted!");
    	}
	}
	
	public void removeAllResidences(CommandSender sender)
	{
		Player player = (Player)sender;
		World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
		
		File resFile = new File("plugins/CityWorld/Residences.yml");
    	
    	YamlConfiguration resYml = YamlConfiguration.loadConfiguration(resFile);
    	YamlConfiguration newYml = new YamlConfiguration();
    	
    	for (String key : resYml.getConfigurationSection("residence").getKeys(false))
   	 	{
    		regionManager.removeRegion(key);
    		newYml.createSection("residence");
    		try
    		{
				newYml.save("plugins/CityWorld/Residences.yml");
			}
    		catch (IOException e)
    		{
				e.printStackTrace();
			}
    		player.sendMessage(key.toString() + " has been deleted!");
   	 	}
	}
    
    public void unclaim(CommandSender sender, String regionName) 
    {
    	ProtectedRegion region;
    	Player player = (Player)sender;
    	World world = player.getWorld();
    	RegionManager regionManager = wg.getRegionManager(world);
    	
    	if (regionName != null)
    	{
    		region = regionManager.getRegion(regionName);
    	
    		if (region == null)
    		{
    			player.sendMessage(regionName + " Doesnt exsist!");
    			return;
    		}
    	
    		DefaultDomain RegionOwner = region.getOwners();
    	
    		if (RegionOwner.contains(player.getName()))
    		{
    			RegionOwner.removePlayer(player.getName());
    	
    			region.setOwners(RegionOwner);
    			resetResFlags(sender,regionName,false);
    	
    			player.sendMessage("You are no longer the owner of " + regionName + "!");
    		}
    		else
    		{
    			player.sendMessage("Cant remove you as the owner of " + regionName + " because you are already not the owner!");
    		}
    	}
    	else
    	{
    		Map<String, ProtectedRegion> regions = regionManager.getRegions();
    		
    		for (Entry<String, ProtectedRegion> key : regions.entrySet())
    		{
    			region = regionManager.getRegion(key.getKey());
    			DefaultDomain RegionOwner = region.getOwners();
    			
    			if (region.hasMembersOrOwners() && RegionOwner.contains(player.getName()))
    			{
    				RegionOwner.removePlayer(player.getName());
    	        	
        			region.setOwners(RegionOwner);
        			resetResFlags(sender,key.getKey(),false);
        	
        			player.sendMessage("You are no longer the owner of " + key.getKey() + "!");
    			}
    		}
    	}
    }
}
