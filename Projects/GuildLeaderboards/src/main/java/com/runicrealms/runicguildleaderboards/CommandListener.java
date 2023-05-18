package com.runicrealms.runicguildleaderboards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.runicrealms.runicguilds.guilds.Guild;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class CommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			sendHelpMessage(sender);
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				if (Plugin.getInstance().config.getStringList("leaderboards").size() != 0) {
					List<String> leaderboards = Plugin.getInstance().config.getStringList("leaderboards");
					sendMessage(sender, "&6Runic Guild Leaderboards Holograms:");
					for (String leaderboardString : leaderboards) {
						List<String> messyLeaderboard = Arrays.asList(leaderboardString.split(","));
						List<String> leaderboard = new ArrayList<String>();
						for (String str : messyLeaderboard) {
							leaderboard.add(str.replaceAll(",", ""));
						}
						sendMessage(sender, "&a" + leaderboard.get(0) + " &e<" + leaderboard.get(1) + "> <" + leaderboard.get(2) + "> <" + leaderboard.get(3) + ">");
					}
				} else {
					sendMessage(sender, "&cThere are currently no leaderboards to display");
				}
			} else {
				sendHelpMessage(sender);
			}
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("create")) {
				if (sender instanceof Player) {
					if (!args[1].contains(",")) {
						Player player = (Player) sender;
						List<String> leaderboards = Plugin.getInstance().config.getStringList("leaderboards");
						for (String leaderboard : leaderboards) {
							double x = Double.parseDouble(leaderboard.split(",")[1].replaceAll(",", ""));
							double y = Double.parseDouble(leaderboard.split(",")[2].replaceAll(",", ""));
							double z = Double.parseDouble(leaderboard.split(",")[3].replaceAll(",", ""));
							String name = leaderboard.split(",")[0].replaceAll(",", "");
							if (player.getLocation().getX() == x && player.getLocation().getY() == y && player.getLocation().getZ() == z && player.getLocation().getWorld().getName().equalsIgnoreCase(leaderboard.split(",")[4].replaceAll(",", ""))) {
								sendMessage(sender, "&cYou cannot place 2 leaderboards in the same location!");
								return true;
							}
							if (name.equals(args[1])) {
								sendMessage(sender, "&cThere is already a leaderboard with that name!");
								return true;
							}
						}
						leaderboards.add(args[1] + "," + player.getLocation().getX() + "," + player.getLocation().getY() + "," + player.getLocation().getZ() + "," + player.getLocation().getWorld().getName());
						Plugin.getInstance().config.set("leaderboards", leaderboards);
						Plugin.getInstance().saveConfig();
						Hologram hologram = HologramsAPI.createHologram(Plugin.getInstance(), player.getLocation());
						hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&6TOP GUILDS"));
						List<Guild> topGuilds = Plugin.getTopGuilds();
						for (int i = 0; i < (topGuilds.size() >= 10 ? 10 : topGuilds.size()); i++) {
							Guild guild = topGuilds.get(i);
							hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&7" + (i + 1) + ". &e" + guild.getGuildName() + " &7- &r" + guild.getScore()));
						}
						Plugin.getInstance().holograms.add(hologram);
						sendMessage(sender, "&aSpawned leaderboard!");
					} else {
						sendMessage(sender, "&cLeaderboard name cannot contain a comma!");
					}
				} else {
					sender.sendMessage("You cannot execute this command from the console!");
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				List<String> leaderboards = Plugin.getInstance().config.getStringList("leaderboards");
				boolean exists = false;
				for (String leaderboard : leaderboards) {
					if (leaderboard.contains(args[1])) {
						exists = true;
					}
				}
				if (exists == false) {
					sendMessage(sender, "&cThat leaderboard does not exist. Use /lb list to view all leaderboards.");
				} else {
					for (String leaderboard : leaderboards) {
						if (leaderboard.contains(args[1])) {
							leaderboards.remove(leaderboards.indexOf(leaderboard));
							break;
						}
					}
					Plugin.getInstance().config.set("leaderboards", leaderboards);
					Plugin.getInstance().saveConfig();
					Hologram hologram = Plugin.getHologramFromName(args[1]);
					Plugin.getInstance().holograms.remove(hologram);
					hologram.delete();
					sendMessage(sender, "&aRemoved leaderboard!");
				}
			} else {
				sendHelpMessage(sender);
			}
		} else if (args.length == 6) {
			if (args[0].equalsIgnoreCase("spawn") || args[0].equalsIgnoreCase("create")) {
				if (!args[1].contains(",")) {
					if (isDouble(args[2]) && isDouble(args[3]) && isDouble(args[4])) {
						List<String> leaderboards = Plugin.getInstance().config.getStringList("leaderboards");
						for (String leaderboard : leaderboards) {
							double x = Double.parseDouble(leaderboard.split(",")[1].replaceAll(",", ""));
							double y = Double.parseDouble(leaderboard.split(",")[2].replaceAll(",", ""));
							double z = Double.parseDouble(leaderboard.split(",")[3].replaceAll(",", ""));
							String name = leaderboard.split(",")[0].replaceAll(",", "");
							if (Double.parseDouble(args[2]) == x && Double.parseDouble(args[3]) == y && Double.parseDouble(args[4]) == z && args[5].equalsIgnoreCase(args[5])) {
								sendMessage(sender, "&cYou cannot place 2 leaderboards in the same location!");
								return true;
							}
							if (name.equals(args[1])) {
								sendMessage(sender, "&cThere is already a leaderboard with that name!");
								return true;
							}
						}
						if (Bukkit.getWorld(args[5]) == null) {
							sendMessage(sender, "&cThere isn't a world with that name!");
							return true;
						}
						leaderboards.add(args[1] + "," + Double.parseDouble(args[2]) + "," + Double.parseDouble(args[3]) + "," + Double.parseDouble(args[4]));
						Plugin.getInstance().config.set("leaderboards", leaderboards);
						Plugin.getInstance().saveConfig();
						Location location = new Location(Bukkit.getWorld(args[5]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
						Hologram hologram = HologramsAPI.createHologram(Plugin.getInstance(), location);
						Plugin.getInstance().holograms.add(hologram);
						hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&6TOP GUILDS"));
						List<Guild> topGuilds = Plugin.getTopGuilds();
						for (int i = 0; i < (topGuilds.size() >= 10 ? 10 : topGuilds.size()); i++) {
							Guild guild = topGuilds.get(i);
							hologram.appendTextLine(ChatColor.translateAlternateColorCodes('&', "&7" + (i + 1) + ". &e" + guild.getGuildName() + " &7- &r" + guild.getScore()));
						}
						sendMessage(sender, "&aSpawned leaderboard!");
					} else {
						sendMessage(sender, "&cLeaderboard name cannot contain a comma!");
					}
				} else {
					sendMessage(sender, "&cPlease use numbers for the coordinates!");
				}
			} else {
				sendHelpMessage(sender);
			}
		} else {
			sendHelpMessage(sender);
		}
		return true;
	}

	private boolean isDouble(String num) {
		try {
			Double.parseDouble(num);
		} catch (Exception exception) {
			return false;
		}
		return true;
	}

	private void sendHelpMessage(CommandSender sender) {
		sendMessage(sender, "&6Runic Guild Leaderboards Commands:");
		sendMessage(sender, "&a/leaderboards list &r- &9Lists all leaderboards");
		sendMessage(sender, "&a/lb spawn &e<name> &r- &9Spawns a leaderboard at your location");
		sendMessage(sender, "&a/lb spawn &e<name> <x> <y> <z> <world> &r- &9Spawns a leaderboard at the specified coordinates");
		sendMessage(sender, "&a/lb remove &e<name> &r- &9Removes a leaderboard");
		sendMessage(sender, "&a/lb list &r- &9Lists all the current leaderboards");
	}

	private void sendMessage(CommandSender sender, String msg) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}

}
