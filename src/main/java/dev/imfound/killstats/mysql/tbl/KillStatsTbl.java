package dev.imfound.killstats.mysql.tbl;

import dev.imfound.killstats.KillStats;
import dev.imfound.killstats.mysql.abst.KillStatsTable;
import dev.imfound.killstats.obj.KPlayer;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class KillStatsTbl extends KillStatsTable {
    @Override
    @SneakyThrows
    public void createTable() {
        try(Connection con = KillStats.getDataSource().getConnection(); PreparedStatement stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS killstats (id INT NOT NULL AUTO_INCREMENT , `player_uuid` VARCHAR(255) NOT NULL , `kills` INT(11) NOT NULL , `deaths` INT(11) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB; ")) {
            stm.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public void insert(KPlayer player) {
        try(Connection con = KillStats.getDataSource().getConnection(); PreparedStatement stm = con.prepareStatement("INSERT INTO killstats(player_uuid, kills, deaths) VALUES (?, ?, ?)")) {
            stm.setString(1, player.getPlayer().getUniqueId().toString());
            stm.setInt(2, player.getKills());
            stm.setInt(3, player.getDeaths());
            stm.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public void update(KPlayer player) {
        try(Connection con = KillStats.getDataSource().getConnection(); PreparedStatement stm = con.prepareStatement("UPDATE killstats SET kills = ?, deaths = ? WHERE player_uuid = ?")) {
            stm.setInt(1, player.getKills());
            stm.setInt(2, player.getDeaths());
            stm.setString(3, player.getPlayer().getUniqueId().toString());
            stm.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public boolean exists(OfflinePlayer player) {
        try(Connection con = KillStats.getDataSource().getConnection(); PreparedStatement stm = con.prepareStatement("SELECT * FROM killstats WHERE player_uuid = ?")) {
            stm.setString(1, player.getUniqueId().toString());
            ResultSet rs = stm.executeQuery();
            return rs.next();
        }
    }

    @Override
    @SneakyThrows
    public List<KPlayer> selectAll() {
        try(Connection con = KillStats.getDataSource().getConnection(); PreparedStatement stm = con.prepareStatement("SELECT * FROM killstats")) {
            ResultSet rs = stm.executeQuery();
            List<KPlayer> kPlayers = new ArrayList<>();
            while(rs.next()) {
                kPlayers.add(
                        new KPlayer(
                                Bukkit.getOfflinePlayer(UUID.fromString(rs.getString("player_uuid"))),
                                rs.getInt("kills"),
                                rs.getInt("deaths")
                        )
                );
            }
            return kPlayers;
        }
    }

}
