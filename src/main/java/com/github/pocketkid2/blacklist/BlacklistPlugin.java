package com.github.pocketkid2.blacklist;

import org.bukkit.Bukkit;

import com.github.pocketkid2.database.Database;
import com.github.pocketkid2.database.DatabasePlugin;

public class BlacklistPlugin extends DatabasePlugin {

	private BlacklistManager manager;
	private String message;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		message = getConfig().getString("disallow-message");
		Database.register(this);
		manager = new BlacklistManager(this);
		Bukkit.getPluginManager().registerEvents(new BlacklistListener(this), this);
		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Done!");
	}

	public BlacklistManager getBlacklistManager() {
		return manager;
	}

	public String getMessage() {
		return message;
	}

}
