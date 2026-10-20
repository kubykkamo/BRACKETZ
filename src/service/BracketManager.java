package service;

import java.util.UUID;

import domain.Match;
import domain.Tournament;
import events.MatchFinishedListener;

public class BracketManager implements MatchFinishedListener{
    private Tournament tournament;

    public BracketManager(Tournament tournament) {
        this.tournament = tournament;
    }


    @Override
    public void onMatchFinished(Match match, UUID winnerId) {
        
        var nextMatchId = match.getNextMatchId();
        
        if (nextMatchId != null)
        {
            Match nextMatch = tournament.getMatchById(nextMatchId);

            nextMatch.assignTeam(winnerId, nextMatch.getNexSlot());
        }
        
    }
    
}
