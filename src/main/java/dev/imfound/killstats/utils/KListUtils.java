package dev.imfound.killstats.utils;

import dev.imfound.killstats.KillStats;
import dev.imfound.killstats.obj.KPlayer;
import dev.imfound.killstats.obj.KStreak;
import org.bukkit.entity.Player;

import java.util.UUID;

public class KListUtils {

    public static boolean existsPlayer(UUID playerUuid) {
        for(KPlayer kPlayer : KillStats.getPlayerList()) {
            if(kPlayer.getPlayer().getUniqueId().equals(playerUuid)) return true;
        }
        return false;
    }

    public static KPlayer getPlayer(Player player) {
        for(KPlayer kPlayer : KillStats.getPlayerList()) {
            if(kPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) return kPlayer;
        }
        return null;
    }

    public static boolean existsStreak(UUID playerUuid) {
        for(KStreak kStreak : KillStats.getStreakList()) {
            if(kStreak.getKPlayer().getPlayer().getUniqueId().equals(playerUuid)) return true;
        }
        return false;
    }

    public static KStreak getStreak(KPlayer player) {
        for(KStreak kStreak : KillStats.getStreakList()) {
            if(kStreak.getKPlayer().getPlayer().getUniqueId().equals(player.getPlayer().getUniqueId())) return kStreak;
        }
        return null;
    }

}
