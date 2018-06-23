package net.fallenones.cityworld;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class WorldEditHandler
{
	private WorldEditPlugin we;

	public WorldEditHandler(WorldEditPlugin we)
	{
        this.we = we;
    }
	
	public WorldEditPlugin getWE()
    {
        return we;
    }
	
	public Selection getSelecion(Player p) 
	{
	    Selection sel = we.getSelection(p);
	 
	    if (sel == null) 
	    {
	        p.sendMessage("You don't have a selection."); return null;
	    }
	 
	    if (!(sel instanceof CuboidSelection)) 
	    {
	        p.sendMessage("Your selection isn't a cuboid."); return null;
	    }
	 
	    //Location min = sel.getMinimumPoint();
	    //Location max = sel.getMaximumPoint();
	    return sel;
	}
}
