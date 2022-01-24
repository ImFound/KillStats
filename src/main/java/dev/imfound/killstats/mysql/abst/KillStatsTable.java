package dev.imfound.killstats.mysql.abst;

import dev.imfound.killstats.obj.KPlayer;
import org.bukkit.OfflinePlayer;

import java.util.List;

public abstract class KillStatsTable {

    public abstract void createTable();
    public abstract void insert(KPlayer player);
    public abstract void update(KPlayer player);
    public abstract boolean exists(OfflinePlayer player);
    public abstract List<KPlayer> selectAll();

}
