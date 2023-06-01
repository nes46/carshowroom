package com.komarnitskaia.carshowroom.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTestBase {
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void check() {
        getTables().stream()
                .map(this::countRecordsInTable)
                .forEach(count -> assertThat(count).isZero());
    }

    @AfterEach
    void truncateTables() {
        jdbcTemplate.execute("truncate table " + String.join(", ", getTables()));
    }

    private long countRecordsInTable(final String tableName) {
        final var queryResult = jdbcTemplate.queryForObject("select count(*) from " + tableName, Long.class);
        return Objects.requireNonNullElse(queryResult, 0L);
    }

    private Set<String> getTables() {
        return Set.of("car", "showroom", "users");
    }

    @SneakyThrows
    protected String asJsonString(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
