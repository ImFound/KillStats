package dev.imfound.killstats.events;

import dev.imfound.killstats.KillStats;
import dev.imfound.killstats.enums.Config;
import dev.imfound.killstats.obj.KPlayer;
import dev.imfound.killstats.obj.KStreak;
import dev.imfound.killstats.utils.KListUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            if (KListUtils.existsPlayer(e.getEntity().getUniqueId())) {
                KPlayer kPlayer = KListUtils.getPlayer(e.getEntity());
                KPlayer kPlayerModified = kPlayer;
                kPlayerModified.setDeaths(kPlayer.getDeaths() + 1);
                KillStats.getPlayerList().set(KillStats.getPlayerList().indexOf(kPlayer), kPlayerModified);
                if (KListUtils.existsStreak(kPlayer.getPlayer().getUniqueId())) {
                    KStreak kStreak = KListUtils.getStreak(kPlayer);
                    KillStats.getStreakList().remove(kStreak);
                }
            } else {
                KPlayer kPlayer = new KPlayer(e.getEntity(), 0, 1);
                KillStats.getPlayerList().add(kPlayer);
            }
            if (KListUtils.existsPlayer(e.getEntity().getKiller().getUniqueId())) {
                KPlayer kPlayer = KListUtils.getPlayer(e.getEntity().getKiller());
                KPlayer kPlayerModified = kPlayer;
                kPlayerModified.setKills(kPlayer.getKills() + 1);
                KillStats.getPlayerList().set(KillStats.getPlayerList().indexOf(kPlayer), kPlayerModified);
                if (KListUtils.existsStreak(kPlayer.getPlayer().getUniqueId())) {
                    KStreak kStreak = KListUtils.getStreak(kPlayer);
                    KStreak kStreak1 = kStreak;
                    kStreak1.setStreak(kStreak.getStreak() + 1);
                    KillStats.getStreakList().set(KillStats.getStreakList().indexOf(kStreak), kStreak1);
                    if (Config.STREAK_BROADCAST_ENABLED.getBoolean()) {
                        if (kStreak1.getStreak() % Config.STREAK_BROADCAST_DELAY.getInt() == 0) {
                            String broadcast = Config.STREAK_BROADCAST_MESSAGE.getFormattedString();
                            broadcast = broadcast.replace("{PLAYER}", kPlayer.getPlayer().getName());
                            broadcast = broadcast.replace("{STREAK}", String.valueOf(kStreak1.getStreak()));
                            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(kPlayer.getPlayer(), broadcast));
                        }
                    }
                } else {
                    KStreak kStreak = new KStreak(kPlayer, 0);
                    kStreak.setStreak(1);
                    KillStats.getStreakList().add(kStreak);
                }
            } else {
                KPlayer kPlayer = new KPlayer(e.getEntity().getKiller(), 1, 0);
                KillStats.getPlayerList().add(kPlayer);
            }
        }
    }

}
