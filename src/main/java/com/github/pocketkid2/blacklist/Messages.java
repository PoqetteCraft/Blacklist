package com.github.pocketkid2.blacklist;

import net.md_5.bungee.api.ChatColor;

public interface Messages {

	String NO_PERM = ChatColor.RED + "You don't have permission for that!";
	String NOT_ENOUGH_ARGS = ChatColor.RED + "Not enough arguments!";
	String BLACKLIST_ADD_SUCCESSFUL = "Successfully blacklisted player %s";
	String BLACKLIST_REMOVE_SUCCESSFUL = "Successfully removed %s from the blacklist";
	String PLAYER_ON_BLACKLIST = "Player %s is on the blacklist";
	String PLAYER_NOT_ON_BLACKLIST = "Player %s is not on the blacklist";
	String INVALID_ARGUMENT = "Invalid argument '%s'";
	String INVALID_PAGE = "Invalid page! Range is %d to %d";
	String DISPLAYING_PAGE = "Displaying page %d of %d";

}
