package org.ferrum.portalsControl.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.ferrum.portalsControl.PortalsControl;

public class PortalListener implements Listener {

    public static boolean blockWorld;
    public static boolean blockNether;
    public static boolean blockEnd;

    @EventHandler
    public void onEntityPortalEnter(EntityPortalEvent event) {
        String name = event.getTo().getWorld().getName();

        if (name.equals("world") && blockWorld) {
            event.setCancelled(true);
            return;
        }
        if (name.equals("world_nether") && blockNether) {
            event.setCancelled(true);
            return;
        }
        if (name.equals("world_the_end") && blockEnd) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPortalEnter(PlayerPortalEvent event) {
        String name = event.getTo().getWorld().getName();
        Player player = event.getPlayer();

        if (name.equals("world") && ( blockWorld || player.hasPermission("portalscontrol.block.world")) && !player.hasPermission("portalscontrol.bypass.world")) {
            event.setCancelled(true);
            return;
        }
        if (name.equals("world_nether") && ( blockNether || player.hasPermission("portalscontrol.block.nether")) && !player.hasPermission("portalscontrol.bypass.nether")) {
            event.setCancelled(true);
            return;
        }
        if (name.equals("world_the_end") && ( blockEnd || player.hasPermission("portalscontrol.block.end")) && !player.hasPermission("portalscontrol.bypass.end")) {
            event.setCancelled(true);
        }
    }
}
