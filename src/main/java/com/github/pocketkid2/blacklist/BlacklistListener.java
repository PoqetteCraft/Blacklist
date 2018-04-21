package com.github.pocketkid2.blacklist;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class BlacklistListener implements Listener {

	private BlacklistPlugin plugin;

	public BlacklistListener(BlacklistPlugin p) {
		plugin = p;
	}

	public void onPlayerLogin(AsyncPlayerPreLoginEvent e) {
		UUID id = e.getUniqueId();
		if (plugin.getBlacklistManager().contains(id) && !Bukkit.getOfflinePlayer(id).isOp()) {
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, plugin.getMessage());
		} else {
			e.allow();
		}
	}

}
