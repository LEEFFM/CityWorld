package net.fallenones.menu;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
 
public class IconMenu implements Listener {
 
    private String name;
    private int size;
    private OptionClickEventHandler handler;
    private Plugin plugin;
   
    private String[] optionNames;
    private String[] optionCmds;
    private ItemStack[] optionIcons;
   
    public IconMenu(String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.plugin = plugin;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        this.optionCmds = new String[size];
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
   
    public IconMenu setOption(int position, ItemStack icon, String name,String Cmd, String... info) {
        optionNames[position] = name;
        optionCmds[position] = Cmd;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }
   
    public void open(Player player) {
        Inventory inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }
   
    public void destroy() {
        HandlerList.unregisterAll(this);
        handler = null;
        plugin = null;
        optionNames = null;
        optionCmds = null;
        optionIcons = null;
    }
   
    @EventHandler(priority=EventPriority.MONITOR)
    void onInventoryClick(InventoryClickEvent event) 
    {
        if (event.getInventory().getTitle().equals(name))
        {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null)
            {
                Plugin plugin = this.plugin;
                OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, optionNames[slot], optionCmds[slot]);
                handler.onOptionClick(e);
                if (optionCmds[slot] != "")
                {
                	Player player = (Player)event.getWhoClicked();
                	player.performCommand(optionCmds[slot]);
                }
                if (e.willClose())
                {
                    final Player p = (Player)event.getWhoClicked();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                    {
                        public void run()
                        {
                            p.closeInventory();
                        }
                    }, 1);
                }
                if (e.willDestroy())
                {
                    destroy();
                }
            }
        }
    }
    
    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);       
    }
    
    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private String Cmd;
        private boolean close;
        private boolean destroy;
       
        public OptionClickEvent(Player player, int position, String name, String Cmd) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.Cmd = Cmd;
            this.close = true;
            this.destroy = false;
        }
       
        public Player getPlayer() {
            return player;
        }
       
        public int getPosition() {
            return position;
        }
       
        public String getName() {
            return name;
        }
        
        public String getCmd()
        {
        	return Cmd;
        }
       
        public boolean willClose() {
            return close;
        }
       
        public boolean willDestroy() {
            return destroy;
        }
       
        public void setWillClose(boolean close) {
            this.close = close;
        }
       
        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
   
    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
            im.setDisplayName(name);
            im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }
   
}
