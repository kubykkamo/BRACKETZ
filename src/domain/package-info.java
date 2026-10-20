/**
 * Domain model for BRACKETZ: tournaments, the teams that enter them, the
 * matches they play and the per-player statistics recorded from those matches.
 *
 * <p>{@link domain.Tournament} is the aggregate root. Cross-aggregate
 * references are held as ids ({@code teamAId}, {@code currentTeamId},
 * {@code activeRoster}) rather than object references, so each entity can be
 * loaded and validated on its own.
 */
package domain;
