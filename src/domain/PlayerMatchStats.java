package domain;

import java.util.Objects;
import java.util.UUID;

/**
 * One player's performance line in a single match.
 */
public class PlayerMatchStats {

    private UUID id;
    private int kills;
    private int deaths;
    private int assists;
    /** Average damage per round. */
    private int adr;

    public PlayerMatchStats() {
    }

    public PlayerMatchStats(UUID id, int kills, int deaths, int assists, int adr) {
        this.id = id;
        this.kills = kills;
        this.deaths = deaths;
        this.assists = assists;
        this.adr = adr;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getAdr() {
        return adr;
    }

    public void setAdr(int adr) {
        this.adr = adr;
    }

    /** Kill/death ratio; {@code deaths} of zero yields {@code kills} as the ratio. */
    public double getKillDeathRatio() {
        return deaths == 0 ? kills : (double) kills / deaths;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PlayerMatchStats)) {
            return false;
        }
        PlayerMatchStats that = (PlayerMatchStats) other;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PlayerMatchStats{id=" + id
                + ", kills=" + kills
                + ", deaths=" + deaths
                + ", assists=" + assists
                + ", adr=" + adr + '}';
    }
}
