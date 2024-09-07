package com.kterising.Functions;

import com.kterising.Team.Team;
import com.kterising.Team.TeamManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class StartGame implements Listener {
    public static int seconds;

    public static boolean time;

    public static boolean win = false;

    public static boolean lavarising;

    public static int survivor;

    public static int lava;

    public static String winner;

    public static boolean PvP;

    public static String mode;

    public static Boolean match;

    public static Sound getSoundFromConfig(Plugin plugin, String path) {
        return Sound.valueOf(plugin.getConfig().getString(path));
    }

    public static void skipTime(Plugin plugin) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (lavarising) {
                System.out.println(ChatColor.translateAlternateColorCodes('&', "[KteRising] Cancel Event! Command: Skip"));
                continue;
            }
            player.getWorld().playSound(player.getLocation(), getSoundFromConfig(plugin, "sound.skip-sound"), 1.0F, 1.0F);
            seconds = 3;
        }
    }

    public static void start(final Plugin plugin) {
        if (match)
            return;
        lavarising = false;
        time = false;
        match = Boolean.TRUE;
        lava = plugin.getConfig().getInt("lava-start-block");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            player.setHealth(20.0D);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.SURVIVAL);
            String title = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.start-game.title")));
            String subtitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.start-game.sub")));
            player.sendTitle(title, subtitle);
            player.getWorld().playSound(player.getLocation(), getSoundFromConfig(plugin, "sound.start-sound"), 1.0F, 1.0F);
            PvP = false;
            ItemStack netheritePickaxe = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.pickaxe.material")));
            ItemMeta pickaxeMeta = netheritePickaxe.getItemMeta();
            assert pickaxeMeta != null;
            pickaxeMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.pickaxe.name"))));
            netheritePickaxe.setItemMeta(pickaxeMeta);
            netheritePickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
            ItemStack cookedBeef = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.food.material")), ItemsConfig.getConfig().getInt("items.food.amount"));
            ItemMeta foodMeta = cookedBeef.getItemMeta();
            assert foodMeta != null;
            foodMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.food.name"))));
            cookedBeef.setItemMeta(foodMeta);
            ItemStack log = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.oak.material")), ItemsConfig.getConfig().getInt("items.oak.amount"));
            if ("Classic".equals(mode)) {
                seconds = plugin.getConfig().getInt("classic-start-time");
                player.getInventory().addItem(netheritePickaxe, cookedBeef, log);
                continue;
            }
            if ("OP".equals(mode)) {
                seconds = plugin.getConfig().getInt("op-start-time");
                ItemStack diamond = new ItemStack(Material.DIAMOND, 64);
                ItemStack iron = new ItemStack(Material.IRON_INGOT, 64);
                ItemStack stone = new ItemStack(Material.COBBLESTONE, 576);
                player.getInventory().addItem(netheritePickaxe, cookedBeef, diamond, iron, stone, log);
                continue;
            }
            if ("Elytra".equals(mode)) {
                seconds = plugin.getConfig().getInt("classic-start-time");
                ItemStack elytra = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.elytra.material")), ItemsConfig.getConfig().getInt("items.elytra.amount"));
                ItemMeta elytraMeta = elytra.getItemMeta();
                assert elytraMeta != null;
                elytraMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.elytra.name"))));
                elytra.setItemMeta(elytraMeta);
                ItemStack firework = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.firework.material")), ItemsConfig.getConfig().getInt("items.firework.amount"));
                player.getInventory().addItem(netheritePickaxe, cookedBeef, log, elytra, firework);
                continue;
            }
            if ("ElytraOP".equals(mode)) {
                seconds = plugin.getConfig().getInt("op-start-time");
                ItemStack diamond = new ItemStack(Material.DIAMOND, 64);
                ItemStack iron = new ItemStack(Material.IRON_INGOT, 64);
                ItemStack stone = new ItemStack(Material.COBBLESTONE, 576);
                ItemStack elytra = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.elytra.material")), ItemsConfig.getConfig().getInt("items.elytra.amount"));
                ItemMeta elytraMeta = elytra.getItemMeta();
                assert elytraMeta != null;
                elytraMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.elytra.name"))));
                elytra.setItemMeta(elytraMeta);
                ItemStack firework = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.firework.material")), ItemsConfig.getConfig().getInt("items.firework.op-amount"));
                player.getInventory().addItem(netheritePickaxe, cookedBeef, diamond, iron, stone, log, firework, elytra);
                continue;
            }
            if ("Trident".equals(mode)) {
                seconds = plugin.getConfig().getInt("classic-start-time");
                ItemStack tridentRiptide = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.trident.material")));
                ItemMeta tridentRiptideMeta = tridentRiptide.getItemMeta();
                assert tridentRiptideMeta != null;
                tridentRiptideMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.trident.riptide-name"))));
                tridentRiptide.setItemMeta(tridentRiptideMeta);
                tridentRiptide.addUnsafeEnchantment(Enchantment.RIPTIDE, 3);
                ItemStack tridentLoyalty = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.trident.material")));
                ItemMeta tridentLoyaltyMeta = tridentLoyalty.getItemMeta();
                assert tridentLoyaltyMeta != null;
                tridentLoyaltyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.trident.loyalty-name"))));
                tridentLoyalty.setItemMeta(tridentLoyaltyMeta);
                tridentLoyalty.addUnsafeEnchantment(Enchantment.LOYALTY, 3);
                player.getInventory().addItem(netheritePickaxe, cookedBeef, log, tridentRiptide, tridentLoyalty);
                continue;
            }
            if ("TridentOP".equals(mode)) {
                seconds = plugin.getConfig().getInt("op-start-time");
                ItemStack diamond = new ItemStack(Material.DIAMOND, 64);
                ItemStack iron = new ItemStack(Material.IRON_INGOT, 64);
                ItemStack stone = new ItemStack(Material.COBBLESTONE, 576);
                ItemStack tridentRiptide = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.trident.material")));
                ItemMeta tridentRiptideMeta = tridentRiptide.getItemMeta();
                assert tridentRiptideMeta != null;
                tridentRiptideMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.trident.riptide-name"))));
                tridentRiptide.setItemMeta(tridentRiptideMeta);
                tridentRiptide.addUnsafeEnchantment(Enchantment.RIPTIDE, 3);
                ItemStack tridentLoyalty = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.trident.material")));
                ItemMeta tridentLoyaltyMeta = tridentLoyalty.getItemMeta();
                assert tridentLoyaltyMeta != null;
                tridentLoyaltyMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.trident.loyalty-name"))));
                tridentLoyalty.setItemMeta(tridentLoyaltyMeta);
                tridentLoyalty.addUnsafeEnchantment(Enchantment.LOYALTY, 3);
                player.getInventory().addItem(netheritePickaxe, cookedBeef, diamond, iron, stone, log, tridentRiptide, tridentLoyalty);
                continue;
            }
            if ("UltraOP".equals(mode)) {
                seconds = plugin.getConfig().getInt("op-start-time");
                ItemStack diamond = new ItemStack(Material.DIAMOND, 64);
                ItemStack iron = new ItemStack(Material.IRON_INGOT, 64);
                ItemStack stone = new ItemStack(Material.COBBLESTONE, 576);
                ItemStack goldenApple = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.ultra-food.apple.material")), ItemsConfig.getConfig().getInt("items.ultra-food.apple.amount"));
                ItemStack bow = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.bow.material")));
                ItemMeta bowMeta = bow.getItemMeta();
                assert bowMeta != null;
                bowMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(ItemsConfig.getConfig().getString("items.bow.bow-name"))));
                bow.setItemMeta(bowMeta);
                ItemStack arrow = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.arrow.material")), ItemsConfig.getConfig().getInt("items.arrow.amount"));
                ItemStack goldencarrot = new ItemStack(Material.valueOf(ItemsConfig.getConfig().getString("items.ultra-food.carrot.material")), ItemsConfig.getConfig().getInt("items.ultra-food.carrot.amount"));
                player.getInventory().addItem(netheritePickaxe, cookedBeef, diamond, iron, stone, log, goldenApple, bow, arrow, goldencarrot);
            }
        }
        (new BukkitRunnable() {
            public void run() {
                StartGame.live(plugin);
                if (StartGame.time) {
                    StartGame.seconds++;
                } else {
                    StartGame.seconds--;
                }
                if (StartGame.seconds == 0) {
                    StartGame.time = true;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String title = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.lava-starting.title")));
                        String subtitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.lava-starting.sub")));
                        player.sendTitle(title, subtitle);
                        player.getWorld().playSound(player.getLocation(), StartGame.getSoundFromConfig(plugin, "sound.lava-rise-sound"), 1.0F, 1.0F);
                        StartGame.lavarising = true;
                    }
                    StartGame.upLava(plugin);
                }
            }
        }).runTaskTimer(plugin, 20L, 20L);
    }

    public static void live(Plugin plugin) {
        Bukkit.getServer().getConsoleSender();
        survivor = 0;
        Player winnerPlayer = null;
        Team winnerTeam = null;
        List<Player> survivingPlayers = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                survivor++;
                survivingPlayers.add(player);
                if (winnerPlayer == null) {
                    winnerPlayer = player;
                    winnerTeam = TeamManager.getPlayerTeam(player);
                    continue;
                }
                if (winnerTeam != null && !winnerTeam.getPlayers().contains(player))
                    winnerTeam = null;
            }
        }
        if (survivor == 1 || (survivor == 2 && winnerTeam != null)) {
            String winner;
            if (survivor == 1) {
                assert winnerPlayer != null;
                winner = winnerPlayer.getName();
                if (!win) {
                    PlayerStats.addWin(winnerPlayer);
                    win = true;
                }
            } else {
                winner = survivingPlayers.get(0).getName() + ", " + survivingPlayers.get(1).getName();
                if (!win) {
                    for (Player player : winnerTeam.getPlayers())
                        PlayerStats.addWin(player);
                    win = true;
                }
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                String title = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.finish-game.title")));
                title = title.replace("%winner%", winner);
                String subtitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.finish-game.sub")).replace("%winner%", winner));
                player.sendTitle(title, subtitle);
                player.getWorld().playSound(player.getLocation(), getSoundFromConfig(plugin, "sound.winner-sound"), 1.0F, 1.0F);
            }
            if (plugin.getConfig().getBoolean("server-stop")) {
                BukkitScheduler scheduler = Bukkit.getScheduler();
                scheduler.runTaskLater(plugin, () -> plugin.getServer().shutdown(), 200L);
            }
        }
        if (survivor == 0 && plugin.getConfig().getBoolean("server-stop")) {
            BukkitScheduler scheduler = Bukkit.getScheduler();
            scheduler.runTaskLater(plugin, () -> plugin.getServer().shutdown(), 100L);
        }
    }

    public static void upLava(final Plugin plugin) {
        final World world = Bukkit.getWorld("world");
        assert world != null;
        final WorldBorder worldBorder = world.getWorldBorder();
        final int minX = (int)(worldBorder.getCenter().getX() - worldBorder.getSize() / 2.0D);
        final int minZ = (int)(worldBorder.getCenter().getZ() - worldBorder.getSize() / 2.0D);
        final int maxX = minX + (int)worldBorder.getSize();
        final int maxZ = minZ + (int)worldBorder.getSize();
        (new BukkitRunnable() {
            public void run() {
                if (!StartGame.lavarising)
                    return;
                int currentLavaLevel = StartGame.lava;
                List<Location> lavaLocations = new ArrayList<>();
                for (int x = minX; x <= maxX; x++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Location location = new Location(world, x, currentLavaLevel, z);
                        if (StartGame.lava == plugin.getConfig().getInt("lava-border") - 1) {
                            if (location.getBlock().getType() == Material.AIR)
                                lavaLocations.add(location);
                        } else {
                            lavaLocations.add(location);
                        }
                    }
                }
                for (Location location : lavaLocations)
                    location.getBlock().setType(Material.LAVA, false);
                if (StartGame.lava == plugin.getConfig().getInt("pvp-allow") && !StartGame.PvP) {
                    StartGame.PvP = true;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String title = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.pvp-allow.title")));
                        String subtitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.pvp-allow.sub")));
                        player.getWorld().playSound(player.getLocation(), StartGame.getSoundFromConfig(plugin, "sound.pvp-sound"), 1.0F, 1.0F);
                        player.sendTitle(title, subtitle);
                    }
                }
                if (StartGame.lava == 255) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getLocation().getY() <= 253.0D)
                            player.setHealth(0.0D);
                    }
                    worldBorder.setSize(3.0D, 180L);
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        String title = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.worldborder-shrink.title")));
                        String subtitle = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.get().getString("title.worldborder-shrink.sub")));
                        player.getWorld().playSound(player.getLocation(), StartGame.getSoundFromConfig(plugin, "sound.shrink-sound"), 1.0F, 1.0F);
                        player.sendTitle(title, subtitle);
                    }
                }
                if (StartGame.lava < plugin.getConfig().getInt("lava-border"))
                    StartGame.lava++;
            }
        }).runTaskTimer(plugin, 0L, 20L * plugin.getConfig().getInt("lava-delay"));
    }
}
