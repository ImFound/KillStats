package dev.imfound.killstats.extensions;

import dev.imfound.killstats.enums.Config;
import dev.imfound.killstats.obj.KPlayer;
import dev.imfound.killstats.utils.KListUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Random;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "foundstats";
    }

    @Override
    public String getAuthor() {
        return "ImFound";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (params.equalsIgnoreCase("kd")) {
            if (KListUtils.existsPlayer(p.getUniqueId())) {
                int kills = KListUtils.getPlayer(p).getKills();
                int deaths = KListUtils.getPlayer(p).getDeaths();
                double kd = (double) kills / deaths;
                return String.valueOf(kd);
            } else {
                return "0.0";
            }
        } else if (params.equalsIgnoreCase("kills")) {
            if (KListUtils.existsPlayer(p.getUniqueId())) {
                return String.valueOf(KListUtils.getPlayer(p).getKills());
            } else {
                return "0";
            }
        } else if (params.equalsIgnoreCase("deaths")) {
            if (KListUtils.existsPlayer(p.getUniqueId())) {
                return String.valueOf(KListUtils.getPlayer(p).getDeaths());
            } else {
                return "0";
            }
        } else if (params.equalsIgnoreCase("streak")) {
            if (KListUtils.existsStreak(p.getUniqueId())) {
                return Config.STREAK_YES.getFormattedString();
            } else {
                return Config.STREAK_NO.getFormattedString();
            }
        } else if (params.equalsIgnoreCase("streakcounter")) {
            if(KListUtils.existsStreak(p.getUniqueId())) {
                return String.valueOf(KListUtils.getStreak(KListUtils.getPlayer(p)).getStreak());
            } else {
                return "0";
            }
        }
        return "N/A";
    }

}
