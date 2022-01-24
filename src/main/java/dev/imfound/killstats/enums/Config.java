package dev.imfound.killstats.enums;

import dev.imfound.killstats.KillStats;
import net.md_5.bungee.api.ChatColor;

import java.util.List;

public enum Config {

    HOST("mysql.host"),
    PORT("mysql.port"),
    DATABASE("mysql.database"),
    USERNAME("mysql.username"),
    PASSWORD("mysql.password"),
    STREAK_YES("streaks.yes"),
    STREAK_NO("streaks.no"),
    STREAK_BROADCAST_ENABLED("streaks.broadcast.enabled"),
    STREAK_BROADCAST_DELAY("streaks.broadcast.delay"),
    STREAK_BROADCAST_MESSAGE("streaks.broadcast.message");

    private final String path;

    Config(String path) {
        this.path = path;
    }

    public boolean getBoolean() {
        return KillStats.getInstance().getConfig().getBoolean(path);
    }

    public String getFormattedString() {
        return ChatColor.translateAlternateColorCodes('&', KillStats.getInstance().getConfig().getString(path));
    }

    public String getString() {
        return KillStats.getInstance().getConfig().getString(path);
    }

    public int getInt() {
        return KillStats.getInstance().getConfig().getInt(path);
    }

    public List<String> getStringList() {
        return KillStats.getInstance().getConfig().getStringList(path);
    }

    public static String getFormattedString(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
