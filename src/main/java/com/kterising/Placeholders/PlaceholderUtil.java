package com.kterising.Placeholders;

import com.kterising.Functions.MessagesConfig;
import com.kterising.Functions.ModVoteGUI;
import com.kterising.Functions.PlayerStats;
import com.kterising.Functions.StartGame;
import com.kterising.Team.Team;
import com.kterising.Team.TeamManager;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.Objects;

public class PlaceholderUtil {

    public static String processPlaceholder(OfflinePlayer player, String placeholder) {
        switch (placeholder.toLowerCase()) {
            case "match":
                return String.valueOf(StartGame.match);
            case "survivor":
                return Integer.toString(StartGame.survivor);
            case "player":
                return player.getName();
            case "time":
                int totalSeconds = StartGame.seconds;
                int minutes = totalSeconds / 60;
                int seconds = totalSeconds % 60;
                return minutes + ":" + String.format("%02d", seconds);
            case "mode":
                return StartGame.mode;
            case "lava":
                return Integer.toString(StartGame.lava);
            case "pvp":
                return StartGame.PvP ? ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("pvp-enabled"))) : ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("pvp-disabled")));
            case "win":
                return Integer.toString(PlayerStats.getWin(Objects.requireNonNull(player.getPlayer())));
            case "death":
                return Integer.toString(PlayerStats.getDeath(Objects.requireNonNull(player.getPlayer())));
            case "kill":
                return Integer.toString(PlayerStats.getKill(Objects.requireNonNull(player.getPlayer())));
            case "teammode":
                return getTeamStatus();
            case "team":
                return getPlayerTeamName(player);
            default:
                return null;
        }
    }

    private static String getPlayerTeamName(OfflinePlayer player) {
        Team team = TeamManager.getPlayerTeam(Objects.requireNonNull(player.getPlayer()));
        if (team != null) {
            return ChatColor.translateAlternateColorCodes('&', team.getName());
        } else {
            return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("messages.no-team-name")));
        }
    }

    private static String getTeamStatus() {
        String winningTeam = ModVoteGUI.getWinningTeam();

        if (StartGame.match) {
            if (winningTeam.equals(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("vote.no-team"))))) {
                return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("vote.no-team")));
            } else if (winningTeam.equals(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("vote.team"))))) {
                return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("vote.team")));
            }
        }
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("vote.wait")));
    }
}
