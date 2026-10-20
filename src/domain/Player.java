package domain;

import java.util.Objects;
import java.util.UUID;

/**
 * A player, identified by their Steam account, and the team they currently
 * belong to.
 */
public class Player {

    private UUID id;
    private String nickname;
    private int steamId;
    private UUID currentTeamId;

    public Player() {
    }

    /** Free agent, not currently attached to a team. */
    public Player(UUID id, String nickname, int steamId) {
        this(id, nickname, steamId, null);
    }

    public Player(UUID id, String nickname, int steamId, UUID currentTeamId) {
        this.id = id;
        setNickname(nickname);
        this.steamId = steamId;
        this.currentTeamId = currentTeamId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    // Final because the constructors call it: an overriding subclass would see
    // the call before its own state was initialised.
    public final void setNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname, "nickname");
    }

    public int getSteamId() {
        return steamId;
    }

    public void setSteamId(int steamId) {
        this.steamId = steamId;
    }

    /** Id of the team the player currently plays for, or {@code null} for a free agent. */
    public UUID getCurrentTeamId() {
        return currentTeamId;
    }

    /** Pass {@code null} to make the player a free agent. */
    public void setCurrentTeamId(UUID currentTeamId) {
        this.currentTeamId = currentTeamId;
    }

    public boolean isFreeAgent() {
        return currentTeamId == null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Player)) {
            return false;
        }
        Player that = (Player) other;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Player{id=" + id
                + ", nickname='" + nickname + '\''
                + ", steamId=" + steamId
                + ", currentTeamId=" + currentTeamId + '}';
    }
}
