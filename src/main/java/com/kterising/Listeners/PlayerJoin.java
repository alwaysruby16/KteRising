package com.kterising.Listeners;

import com.kterising.Functions.MessagesConfig;
import com.kterising.Functions.ScoreBoard;
import com.kterising.Functions.SpecialItems;
import com.kterising.Functions.StartGame;
import com.kterising.KteRising;
import com.kterising.Team.TeamManager;
import java.util.Objects;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoin implements Listener {
    private final KteRising plugin;

    public PlayerJoin(KteRising plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerjoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (StartGame.match) {
            player.teleport(new Location(player.getWorld(), 0.0D, 160.0D, 0.0D));
            player.setGameMode(GameMode.SPECTATOR);
            String title = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.eliminated.title")));
            String subtitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.eliminated.sub")));
            player.sendTitle(title, subtitle);
        } else {
            player.teleport(new Location(player.getWorld(), 0.0D, 160.0D, 0.0D));
            player.setGameMode(GameMode.ADVENTURE);
            TeamManager.assignPlayerToTeam(event.getPlayer());
            player.getInventory().clear();
            SpecialItems.giveSpecialItems(event.getPlayer());
        }
        BukkitRunnable scoreboardTask = new BukkitRunnable() {
            public void run() {
                ScoreBoard.scoreboard(PlayerJoin.this.plugin, event.getPlayer());
            }
        };
        scoreboardTask.runTaskTimer(this.plugin, 0L, 20L);
    }
}
