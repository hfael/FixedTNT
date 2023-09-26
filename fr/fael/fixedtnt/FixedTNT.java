package fr.fael.fixedtnt;

import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class FixedTNT extends JavaPlugin implements Listener{

	
	@Override
	public void onEnable() {
	getServer().getPluginManager().registerEvents(this, this);
	super.onEnable();
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		Block tar = e.getClickedBlock();
		if(it != null && it.getType() == Material.FLINT_AND_STEEL) {
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK && tar != null && tar.getType() == Material.TNT) {
				e.setCancelled(true);
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.FUSE, 1, 1);
				tar.setType(Material.AIR);
				Location loc = tar.getLocation().add(0.5, 0.25, 0.5);
				TNTPrimed tnt = (TNTPrimed) tar.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(0f, 0.25f, 0f));
			}
		}
	}
	
	@EventHandler
	public void explode(EntityExplodeEvent e) {
		List<Block> block = e.blockList();
		Iterator<Block> bs = block.iterator();
		while(bs.hasNext()) {
			Block b = bs.next();
			if(b.getType() == Material.TNT) {
				b.setType(Material.AIR);
				Location loc = b.getLocation().add(0.5, 0.25, 0.5);
				TNTPrimed tnt = (TNTPrimed) b.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
				tnt.setVelocity(new Vector(0, 0.25, 0));
				tnt.teleport(loc);
			}
			if(b.getType() == Material.SANDSTONE) {
				e.setCancelled(true);
				b.setType(Material.AIR);
			}else {
				e.setCancelled(true);
			}
		}
	}
}
