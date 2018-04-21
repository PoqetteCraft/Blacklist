package com.github.pocketkid2.blacklist;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BlacklistCommand implements CommandExecutor {

	private BlacklistPlugin plugin;

	public BlacklistCommand(BlacklistPlugin p) {
		plugin = p;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			// Display stuff
		} else if (args.length > 0) {
			if (args[0].equalsIgnoreCase("add")) {
				if (sender.hasPermission("blacklist.add")) {
					if (args.length > 1) {
						String name = args[1];
						OfflinePlayer player = Bukkit.getOfflinePlayer(name);
						plugin.getBlacklistManager().add(player.getUniqueId());
						sender.sendMessage(String.format(Messages.BLACKLIST_ADD_SUCCESSFUL, player.getName()));
					} else {
						sender.sendMessage(Messages.NOT_ENOUGH_ARGS);
						return false;
					}
				} else {
					sender.sendMessage(Messages.NO_PERM);
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (sender.hasPermission("blacklist.remove")) {
					if (args.length > 1) {
						String name = args[1];
						OfflinePlayer player = Bukkit.getOfflinePlayer(name);
						plugin.getBlacklistManager().remove(player.getUniqueId());
						sender.sendMessage(String.format(Messages.BLACKLIST_REMOVE_SUCCESSFUL, player.getName()));
					} else {
						sender.sendMessage(Messages.NOT_ENOUGH_ARGS);
						return false;
					}
				} else {
					sender.sendMessage(Messages.NO_PERM);
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				if (sender.hasPermission("blacklist.info")) {
					if (args.length > 1) {
						String name = args[1];
						OfflinePlayer player = Bukkit.getOfflinePlayer(name);
						boolean status = plugin.getBlacklistManager().contains(player.getUniqueId());
						if (status) {
							sender.sendMessage(String.format(Messages.PLAYER_ON_BLACKLIST, player.getName()));
						} else {
							sender.sendMessage(String.format(Messages.PLAYER_NOT_ON_BLACKLIST, player.getName()));
						}
					} else {
						sender.sendMessage(Messages.NOT_ENOUGH_ARGS);
						return false;
					}
				} else {
					sender.sendMessage(Messages.NO_PERM);
				}
			} else if (args[0].equalsIgnoreCase("list")) {
				if (sender.hasPermission("blacklist.list")) {
					List<UUID> list = plugin.getBlacklistManager().all();
					int listSize = list.size();
					int pageSize = plugin.getSize();
					if (listSize == 0) {
						sender.sendMessage(Messages.BLACKLIST_EMPTY);
					} else if (listSize > pageSize) {
						int totalPages = listSize / pageSize;
						if ((listSize % pageSize) != 0) {
							totalPages++;
						}
						int displayPage = 0;
						if (args.length == 1) {
							displayPage = 1;
						} else {
							try {
								int tempPage = Integer.parseInt(args[1]);
								if ((tempPage >= 1) && (tempPage <= totalPages)) {
									displayPage = tempPage;
								} else {
									sender.sendMessage(String.format(Messages.INVALID_PAGE, 1, totalPages));
									return true;
								}
							} catch (NumberFormatException e) {
								sender.sendMessage(String.format(Messages.INVALID_ARGUMENT, args[1]));
								return false;
							}
						}
						int start = (displayPage - 1) * pageSize;
						int end = (start + pageSize) > (listSize - 1) ? (listSize - 1) : (start + pageSize);
						sender.sendMessage(String.format(Messages.DISPLAYING_PAGE, displayPage, totalPages));
						for (UUID id : list.subList(start, end)) {
							sender.sendMessage(Bukkit.getOfflinePlayer(id).getName());
						}
					} else {
						sender.sendMessage(String.format(Messages.DISPLAYING_PAGE, 1, 1));
						for (UUID id : list) {
							sender.sendMessage(Bukkit.getOfflinePlayer(id).getName());
						}
					}
				} else {
					sender.sendMessage(Messages.NO_PERM);
				}
			} else {
				// Blacklist player argument
				OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
				plugin.getBlacklistManager().add(player.getUniqueId());
				sender.sendMessage(String.format(Messages.BLACKLIST_ADD_SUCCESSFUL, player.getName()));
			}
		}
		return true;
	}

}
