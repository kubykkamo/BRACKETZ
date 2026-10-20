package events;

import java.util.UUID;

import domain.Match;

public interface MatchFinishedListener {

    void onMatchFinished(Match match, UUID winnerId);
}
