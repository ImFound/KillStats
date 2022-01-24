package dev.imfound.killstats;

import dev.imfound.killstats.events.DeathEvent;
import dev.imfound.killstats.extensions.Placeholders;
import dev.imfound.killstats.mysql.HikariDataSource;
import dev.imfound.killstats.mysql.tbl.KillStatsTbl;
import dev.imfound.killstats.obj.KPlayer;
import dev.imfound.killstats.obj.KStreak;
import dev.imfound.killstats.task.SaveValuesTask;
import dev.imfound.killstats.utils.Metrics;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.plaf.synth.SynthDesktopIconUI;
import java.util.ArrayList;

public final class KillStats extends JavaPlugin {

    @Getter private static HikariDataSource dataSource;
    @Getter private static KillStats instance;
    @Getter public static ArrayList<KPlayer> playerList = new ArrayList<>();
    @Getter public static ArrayList<KStreak> streakList = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        long startTime = System.currentTimeMillis();
        getLogger().info("\n" +
                "\n" +
                " _  __ _  _     _     ____  _____  ____  _____  ____ \n" +
                "/ |/ // \\/ \\   / \\   / ___\\/__ __\\/  _ \\/__ __\\/ ___\\\n" +
                "|   / | || |   | |   |    \\  / \\  | / \\|  / \\  |    \\\n" +
                "|   \\ | || |_/\\| |_/\\\\___ |  | |  | |-||  | |  \\___ |\n" +
                "\\_|\\_\\\\_/\\____/\\____/\\____/  \\_/  \\_/ \\|  \\_/  \\____/\n" +
                "                                                     \n" +
                "\n");
        getLogger().info("");
        getLogger().info("--[KillStats v" + getDescription().getVersion() + "]--");
        getLogger().info("Loading config..");
        loadConfig();
        getLogger().info("Config loaded!");
        getLogger().info("Opening MySQL Pool..");
        dataSource = new HikariDataSource();
        new KillStatsTbl().createTable();
        new KillStatsTbl().selectAll().forEach(kPlayer -> KillStats.getPlayerList().add(kPlayer));
        getLogger().info("MySQL pool opened!");
        getLogger().info("Loading tasks..");
        new SaveValuesTask().runTaskTimerAsynchronously(KillStats.getInstance(), 20L, 6000L);
        getLogger().info("Tasks loaded!");
        getLogger().info("Loading listeners..");
        loadListeners(
                new DeathEvent()
        );
        getLogger().info("Listeners loaded!");
        getLogger().info("Loading hooks..");
        new Placeholders().register();
        getLogger().info("Hooks loaded!");
        getLogger().info("Loading metrics..");
        new Metrics(this, 14054);
        getLogger().info("Metrics loaded!");
        long endTime = System.currentTimeMillis() - startTime;
        getLogger().info("Thanks for using an ImFound plugin, it took " + endTime + "ms to load it!");
        getLogger().info("--[KillStats v" + getDescription().getVersion() + "]--");
    }

    private void loadListeners(Listener... listeners) {
        for(Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    private void loadConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving data asynchronously");
        long startTime = System.currentTimeMillis();
        KillStatsTbl database = new KillStatsTbl();
        for (KPlayer kPlayer : KillStats.getPlayerList()) {
            if (database.exists(kPlayer.getPlayer())) {
                database.update(kPlayer);
            } else {
                database.insert(kPlayer);
            }
        }
        KillStats.getPlayerList().clear();
        new KillStatsTbl().selectAll().forEach(kPlayer -> KillStats.getPlayerList().add(kPlayer));
        long timeTaken = System.currentTimeMillis() - startTime;
        int dataSize = KillStats.getPlayerList().size();
        getLogger().info(String.format("Saved %d data! It tooks " + timeTaken + "ms!", dataSize));
        Bukkit.getScheduler().cancelTasks(this);
    }
}
