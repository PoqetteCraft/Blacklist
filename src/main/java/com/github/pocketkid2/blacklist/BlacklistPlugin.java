package com.github.pocketkid2.blacklist;

import com.github.pocketkid2.database.Database;
import com.github.pocketkid2.database.DatabasePlugin;

public class BlacklistPlugin extends DatabasePlugin {

	@Override
	public void onEnable() {
		Database.register(this);
		getLogger().info("Done!");
	}

	@Override
	public void onDisable() {
		getLogger().info("Done!");
	}
}
