package com.runicrealms.runicguildleaderboards;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.runicrealms.runicguilds.api.RunicGuildsAPI;
import com.runicrealms.runicguilds.guilds.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class Plugin extends JavaPlugin {

	private static Plugin plugin;
	public FileConfiguration config;
	public List<Hologram> holograms = new ArrayList<Hologram>();

	@Override
	public void onEnable() {
		plugin = this;
		config = this.getConfig();
		config.addDefault("leaderboards", new ArrayList<String>());
		config.options().copyDefaults(true);
		this.saveDefaultConfig();
		this.getCommand("leaderboards").setExecutor(new CommandListener());
		for (String leaderboard : config.getStringList("leaderboards")) {
			Location location = new Location(
					Bukkit.getWorld(leaderboard.split(",")[4].replaceAll(",", "")), 
					Double.parseDouble(leaderboard.split(",")[1].replaceAll(",", "")),
					Double.parseDouble(leaderboard.split(",")[2].replaceAll(",", "")),
					Double.parseDouble(leaderboard.split(",")[3].replaceAll(",", "")));
			Hologram hologram = HologramsAPI.createHologram(this, location);
			hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&6TOP GUILDS"));
			List<Guild> topGuilds = Plugin.getTopGuilds();
			for (int i = 0; i < (topGuilds.size() >= 10 ? 10 : topGuilds.size()); i++) {
				Guild guild = topGuilds.get(i);
				hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&7" + (i + 1) + ". &e" + guild.getGuildName() + " &7- &r" + guild.getScore()));
			}
			holograms.add(hologram);
		}
		this.saveConfig();
		setupRepeatingTask();
	}
	
	public static void setupRepeatingTask() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(getInstance(), new Runnable() {
			@Override
			public void run() {
				StringBuilder output = new StringBuilder();
				output.append("[");
				for (Guild guild : getTopGuilds()) {
					output.append("{\"name\":\"");
					output.append(guild.getGuildName());
					output.append("\",\"score\":");
					output.append(guild.getScore());
					output.append("},");
				}
				output.deleteCharAt(output.length() - 1);
				output.append("]");
				saveToDataFile(output.toString());
				for (Hologram hologram : Plugin.getInstance().holograms) {
					hologram.clearLines();
					hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&6TOP GUILDS"));
					List<Guild> topGuilds = Plugin.getTopGuilds();
					for (int i = 0; i < (topGuilds.size() >= 10 ? 10 : topGuilds.size()); i++) {
						Guild guild = topGuilds.get(i);
						hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&7" + (i + 1) + ". &e" + guild.getGuildName() + " &7- &r" + guild.getScore()));
					}
				}
			}
		}, 0L, 10L * 60L * 20L);
	}

	public static List<Guild> getTopGuilds() {
		List<Guild> guilds = RunicGuildsAPI.getAllGuilds();
		Collections.sort(guilds, new Comparator<Guild>() {
			@Override
			public int compare(Guild guild1, Guild guild2) {
				return new Integer(guild2.getScore()).compareTo(new Integer(guild1.getScore()));
			}
		});
		return guilds;
	}

	public static Hologram getHologramFromName(String name) {
		for (String leaderboard : getInstance().config.getStringList("leaderboards")) {
			if (leaderboard.split(",")[0].replaceAll(",", "").equals(name)) {
				double x = Double.parseDouble(leaderboard.split(",")[1].replaceAll(",", ""));
				double y = Double.parseDouble(leaderboard.split(",")[2].replaceAll(",", ""));
				double z = Double.parseDouble(leaderboard.split(",")[3].replaceAll(",", ""));
				for (Hologram hologram : getInstance().holograms) {
					if (Math.round(hologram.getX()) == Math.round(x) && Math.round(hologram.getY()) == Math.round(y) && Math.round(hologram.getZ()) == Math.round(z)) {
						return hologram;
					}
				}
				return null;
			}
		}
		return null;
	}
	
	private static void saveToDataFile(String str) {
		File file = new File(Plugin.getInstance().getDataFolder(), "guilddata.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			PrintWriter writer = new PrintWriter(file);
			writer.print(str);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Plugin getInstance() {
		return plugin;
	}

}
