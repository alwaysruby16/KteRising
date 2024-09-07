package com.kterising.Team;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamManager {
    private static final TeamManager instance = new TeamManager();
    private static List<Team> teams;

    private TeamManager() {
        teams = new ArrayList<>();
        initializeTeams();
    }

    public static TeamManager getInstance() {
        return instance;
    }

    private void initializeTeams() {
        for (char i = 'A'; i <= 'Z'; i++) {
            teams.add(new Team(i + " Team", 2));
        }
    }

    public static List<Team> getTeams() {
        return teams;
    }

    public static void assignPlayerToTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().size() < team.getMaxSize()) {
                team.addPlayer(player);
                return;
            }
        }
    }

    public static void removePlayerFromTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().remove(player)) {
                break;
            }
        }
    }

    public static Team getPlayerTeam(Player player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    public static void removeAllPlayersFromTeams() {
        for (Team team : teams) {
            team.getPlayers().clear();
        }
    }
}