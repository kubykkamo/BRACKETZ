package domain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A team entered in a tournament, together with the players currently on its
 * roster. Roster members are player ids, so a player can leave one team's
 * roster and join another's without either team holding the entity.
 */
public class Team {

    private UUID id;
    private String name;
    private final Set<UUID> activeRoster = new LinkedHashSet<>();

    public Team() {
    }

    /** Team starting out with an empty roster. */
    public Team(UUID id, String name) {
        this(id, name, Collections.emptySet());
    }

    public Team(UUID id, String name, Set<UUID> activeRoster) {
        this.id = id;
        setName(name);
        setActiveRoster(activeRoster);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    // Final because the constructors call it: an overriding subclass would see
    // the call before its own state was initialised.
    public final void setName(String name) {
        this.name = Objects.requireNonNull(name, "name");
    }

    /** Ids of the players currently on the roster, in the order they joined. */
    public Set<UUID> getActiveRoster() {
        return Collections.unmodifiableSet(activeRoster);
    }

    public final void setActiveRoster(Set<UUID> newRoster) {
        activeRoster.clear();
        activeRoster.addAll(Objects.requireNonNull(newRoster, "activeRoster"));
    }

    public boolean addToRoster(UUID playerId) {
        return activeRoster.add(Objects.requireNonNull(playerId, "playerId"));
    }

    public boolean removeFromRoster(UUID playerId) {
        return activeRoster.remove(playerId);
    }

    public boolean isOnRoster(UUID playerId) {
        return activeRoster.contains(playerId);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Team)) {
            return false;
        }
        Team that = (Team) other;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Team{id=" + id
                + ", name='" + name + '\''
                + ", activeRoster=" + activeRoster + '}';
    }
}
