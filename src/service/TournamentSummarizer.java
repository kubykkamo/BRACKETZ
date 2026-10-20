package service;

import java.util.Locale;
import java.util.Objects;

import domain.Team;
import domain.Tournament;

/**
 * Renders a {@link Tournament} as a human readable overview.
 *
 * <p>Stateless domain service: it has no fields, so one instance can serve any
 * number of tournaments.
 */
public class TournamentSummarizer {

    private static final String NEW_LINE = System.lineSeparator();

    public TournamentSummarizer() {
    }

}
