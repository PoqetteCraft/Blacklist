package com.github.pocketkid2.blacklist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.github.pocketkid2.database.Database;

public class BlacklistManager {

	private BlacklistPlugin plugin;

	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement selectId;
	private PreparedStatement selectAll;

	public BlacklistManager(BlacklistPlugin p) {
		plugin = p;
		try {
			Database.create().executeUpdate("CREATE TABLE IF NOT EXISTS blacklist (uuid VARCHAR(36) PRIMARY KEY);");
			insert = Database.prepare("INSERT INTO blacklist VALUES (?);");
			delete = Database.prepare("DELETE FROM blacklist WHERE uuid = ?;");
			selectId = Database.prepare("SELECT * FROM blacklist WHERE uuid = ?;");
			selectAll = Database.prepare("SELECT * FROM blacklist;");
		} catch (SQLException e) {
			plugin.getLogger().severe("Error creating table and statements");
			e.printStackTrace();
		}
	}

	/**
	 * Adds the given player UUID to the blacklist
	 *
	 * @param id
	 */
	public void add(UUID id) {
		try {
			insert.setString(1, id.toString());
			insert.executeUpdate();
		} catch (SQLException e) {
			plugin.getLogger().severe("Error inserting UUID " + id.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Removes the given player UUID from the blacklist if it was there
	 *
	 * @param id
	 */
	public void remove(UUID id) {
		try {
			delete.setString(1, id.toString());
			delete.executeUpdate();
		} catch (SQLException e) {
			plugin.getLogger().severe("Error removing UUID " + id.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Checks if the given player UUID is in the blacklist
	 *
	 * @param id
	 * @return
	 */
	public boolean contains(UUID id) {
		try {
			selectId.setString(1, id.toString());
			try (ResultSet rs = selectId.executeQuery()) {
				return rs.next();
			} catch (SQLException e) {
				plugin.getLogger().severe("Error reading UUID " + id.toString());
				e.printStackTrace();
			}
		} catch (SQLException e) {
			plugin.getLogger().severe("Error preparing select (by id) statement");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Grabs all player UUIDs from the blacklist and returns them as a list
	 *
	 * @return
	 */
	public List<UUID> all() {
		List<UUID> list = new ArrayList<UUID>();
		try (ResultSet rs = selectAll.executeQuery()) {
			while (rs.next()) {
				list.add(UUID.fromString(rs.getString("uuid")));
			}
		} catch (SQLException e) {
			plugin.getLogger().severe("Error reading blacklist");
			e.printStackTrace();
		}
		return list;
	}

}
