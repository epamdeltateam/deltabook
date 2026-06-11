package com.deltabook.security.token;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class CustomPersistentTokenRepository implements PersistentTokenRepository {

    private final JdbcTemplate jdbcTemplate;

    public CustomPersistentTokenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        jdbcTemplate.update(
                """
                INSERT INTO persistent_logins
                (username, series, token, last_used)
                VALUES (?, ?, ?, ?)
                """,
                token.getUsername(),
                token.getSeries(),
                token.getTokenValue(),
                new Timestamp(token.getDate().getTime())
        );
    }

    @Override
    public void updateToken(@NonNull String series, @NonNull String tokenValue, Date lastUsed) {
        jdbcTemplate.update(
                """
                UPDATE persistent_logins
                SET token = ?, last_used = ?
                WHERE series = ?
                """,
                tokenValue,
                new Timestamp(lastUsed.getTime()),
                series
        );
    }

    @Override
    public @Nullable PersistentRememberMeToken getTokenForSeries(@NonNull String seriesId) {
        List<PersistentRememberMeToken> result = jdbcTemplate.query(
                """
                SELECT username, series, token, last_used
                FROM persistent_logins
                WHERE series = ?
                """,
                (rs, rowNum) -> new PersistentRememberMeToken(
                        rs.getString("username"),
                        rs.getString("series"),
                        rs.getString("token"),
                        rs.getTimestamp("last_used")
                ),
                seriesId
        );

        return result.isEmpty() ? null : result.getFirst();
    }

    @Override
    public void removeUserTokens(@NonNull String username) {
        jdbcTemplate.update(
                "DELETE FROM persistent_logins WHERE username = ?",
                username
        );
    }
}