package domain;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * A tournament: the prize pool on offer, the teams entered and the matches
 * played between them. Acts as the aggregate root of the domain model.
 */
public class Tournament {

    private static final double MINIMUM_PRIZE_POOL = 0.0;

    private double prizePool;
    private final Set<Team> teams = new LinkedHashSet<>();
    private final Set<Match> matches = new LinkedHashSet<>();




    public Tournament() {
    }

    /** Tournament with no teams and no matches yet. */
    public Tournament(double prizePool) {
        this(prizePool, Collections.emptySet(), Collections.emptySet());
    }

    public Tournament(double prizePool, Set<Team> teams, Set<Match> matches) {
        setPrizePool(prizePool);
        setTeams(teams);
        setMatches(matches);
    }

    public double getPrizePool() {
        return prizePool;
    }

    // Final because the constructors call it: an overriding subclass would see
    // the call before its own state was initialised.
    public final void setPrizePool(double prizePool) {
        if (prizePool < MINIMUM_PRIZE_POOL) {
            throw new IllegalArgumentException("prizePool must not be negative: " + prizePool);
        }
        this.prizePool = prizePool;
    }

    /** Teams entered in this tournament, in the order they were added. */
    public Set<Team> getTeams() {
        return Collections.unmodifiableSet(teams);
    }

    public final void setTeams(Set<Team> newTeams) {
        teams.clear();
        teams.addAll(Objects.requireNonNull(newTeams, "teams"));
    }

    public boolean addTeam(Team team) {
        return teams.add(Objects.requireNonNull(team, "team"));
    }

    public boolean removeTeam(Team team) {
        return teams.remove(team);
    }

    /** Matches played in this tournament, in the order they were added. */
    public Set<Match> getMatches() {
        return Collections.unmodifiableSet(matches);
    }

    public final void setMatches(Set<Match> newMatches) {
        matches.clear();
        matches.addAll(Objects.requireNonNull(newMatches, "matches"));
    }

    public boolean addMatch(Match match) {
        return matches.add(Objects.requireNonNull(match, "match"));
    }

    public boolean removeMatch(Match match) {
        return matches.remove(match);
    }

    public Match getMatchById(UUID matchId){

        return matches.stream()
        .filter(m -> m.getId().equals(matchId))
        .findFirst()
        .orElse(null);
    }

    @Override
    public String toString() {
        return "Tournament{prizePool=" + prizePool
                + ", teams=" + teams.size()
                + ", matches=" + matches.size() + '}';
    }
}
