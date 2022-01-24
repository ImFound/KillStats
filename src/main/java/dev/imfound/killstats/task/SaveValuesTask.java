package dev.imfound.killstats.task;

import dev.imfound.killstats.KillStats;
import dev.imfound.killstats.mysql.tbl.KillStatsTbl;
import dev.imfound.killstats.obj.KPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveValuesTask extends BukkitRunnable {
    @Override
    public void run() {
        KillStats.getInstance().getLogger().info("Saving data asynchronously");
        long startTime = System.currentTimeMillis();
        KillStatsTbl database = new KillStatsTbl();
        for (KPlayer kPlayer : KillStats.getPlayerList()) {
            if (database.exists(kPlayer.getPlayer())) {
                database.update(kPlayer);
            } else {
                database.insert(kPlayer);
            }
        }
        long timeTaken = System.currentTimeMillis() - startTime;
        int dataSize = KillStats.getPlayerList().size();
        KillStats.getInstance().getLogger().info(String.format("Saved %d data! It tooks " + timeTaken + "ms!", dataSize));
    }
}
