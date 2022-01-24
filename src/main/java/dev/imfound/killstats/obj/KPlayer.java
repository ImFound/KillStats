package dev.imfound.killstats.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

@Getter
@Setter
@AllArgsConstructor
public class KPlayer {

    private OfflinePlayer player;
    private int kills;
    private int deaths;

}
