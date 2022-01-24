package dev.imfound.killstats.obj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KStreak {

    private KPlayer kPlayer;
    private int streak;

}
