import domain.Match;
import domain.Team;
import domain.Tournament;
import service.TournamentSummarizer;

import java.util.UUID;

/**
 * Bootstraps a small example tournament and prints its summary to the console.
 */
public class Main {

    public static void main(String[] args) {
        Team sentinels = newTeam("Sentinels", 3);
        Team fraggers = newTeam("Fraggers", 2);
        Team ninjas = newTeam("Ninjas", 3);

        Tournament event = new Tournament(50_000.0);
        event.addTeam(sentinels);
        event.addTeam(fraggers);
        event.addTeam(ninjas);

        event.addMatch(new Match(UUID.randomUUID(), sentinels.getId(), fraggers.getId()));
        event.addMatch(new Match(UUID.randomUUID(), ninjas.getId(), fraggers.getId()));

    }

    /** Team with the given name and an empty roster of {@code rosterSize} fresh players. */
    private static Team newTeam(String name, int rosterSize) {
        Team team = new Team(UUID.randomUUID(), name);
        for (int i = 0; i < rosterSize; i++) {
            team.addToRoster(UUID.randomUUID());
        }
        return team;
    }
}
