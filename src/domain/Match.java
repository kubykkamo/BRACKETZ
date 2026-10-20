package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import events.MatchFinishedListener;


/**
 * A single match between two teams of a tournament. The opponents are held as
 * team ids rather than {@link Team} references.
 */
public class Match {

    private UUID id;
    private UUID teamAId;
    private UUID teamBId;
    private int teamAResult;
    private int teamBResult;
    /** Whether the final score has been reported; a score can only be reported once. */
    private boolean finished;
    private UUID nextMatchId; 
    private Slot nexSlot;

    public Slot getNexSlot() {
        return nexSlot;
    }

    public void setNexSlot(Slot nexSlot) {
        this.nexSlot = nexSlot;
    }

    public enum Slot
    {
        TEAM_A,
        TEAM_B
    }

    public UUID getNextMatchId() {
        return nextMatchId;
    }

    public void setNextMatchId(UUID nextMatchId) {
        this.nextMatchId = nextMatchId;
    }

    private transient List<MatchFinishedListener> listeners = new ArrayList<>();

    public void addListener(MatchFinishedListener listener) {
        if (listeners == null) listeners = new ArrayList<>(); // Pojistka po načtení z JSONu
        listeners.add(listener);
    }

    /**
     * Records the final score of this match and advances the winner into the
     * bracket by notifying the registered listeners. This is the only way to
     * score a match; it is meant to be called once, from the UI, when the
     * match really has finished.
     *
     * @throws IllegalStateException if the match is already finished, or if one
     *             of the two team slots is still empty
     * @throws IllegalArgumentException if a score is negative, or if the scores
     *             are level, which cannot happen in a knockout match
     */
    public void reportScore(int scoreA, int scoreB) {
        if (finished) {
            throw new IllegalStateException("match already finished: " + id);
        }
        if (scoreA < 0 || scoreB < 0) {
            throw new IllegalArgumentException("scores must not be negative: " + scoreA + ":" + scoreB);
        }
        if (scoreA == scoreB) {
            throw new IllegalArgumentException("a knockout match cannot be tied: " + scoreA + ":" + scoreB);
        }
        if (teamAId == null || teamBId == null) {
            throw new IllegalStateException("both teams must be assigned before scoring: " + id);
        }

        this.teamAResult = scoreA;
        this.teamBResult = scoreB;
        // Marked finished before notifying, so a listener cannot re-enter this method.
        finished = true;

        notifyFinished(getWinnerId());
    }

    public boolean isFinished() {
        return finished;
    }

    /** The winning team, or {@code null} while the match is unfinished. */
    public UUID getWinnerId() {
        if (!finished) {
            return null;
        }
        // A reported score is guaranteed not to be level, so one side always wins.
        return teamAResult > teamBResult ? teamAId : teamBId;
    }

    private void notifyFinished(UUID winnerId) {
        if (listeners == null) {
            return;
        }
        // Iterating a copy lets a listener subscribe while it is being notified.
        for (MatchFinishedListener listener : List.copyOf(listeners)) {
            listener.onMatchFinished(this, winnerId);
        }
    }

    

    private void setTeamAResult(int teamAResult) {
    
        this.teamAResult = teamAResult;
    }

    private void setTeamBResult(int teamBResult) {
        this.teamBResult = teamBResult;
    }

    public int getTeamAResult() {
        return teamAResult;
    }

    public int getTeamBResult() {
        return teamBResult;
    }

    public Match() {
    }

    public Match(UUID id, UUID teamAId, UUID teamBId) {
        this.id = id;
        this.teamAId = teamAId;
        this.teamBId = teamBId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(UUID teamAId) {
        this.teamAId = teamAId;
    }

    public UUID getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(UUID teamBId) {
        this.teamBId = teamBId;
    }

    public void assignTeam(UUID winnerId, Slot targetSlot) {
    if (targetSlot == Slot.TEAM_A) this.teamAId = winnerId;
    else if (targetSlot == Slot.TEAM_B) this.teamBId = winnerId;
    
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Match)) {
            return false;
        }
        Match that = (Match) other;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Match{id=" + id
                + ", teamAId=" + teamAId
                + ", teamBId=" + teamBId + '}';
    }
}
