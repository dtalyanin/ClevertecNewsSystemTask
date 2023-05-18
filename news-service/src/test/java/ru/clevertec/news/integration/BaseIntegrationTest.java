package ru.clevertec.news.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import generators.factories.AuthenticatedUserFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "classpath:/db/changelog/changeset/scripts/sequence-reset.sql")
public abstract class BaseIntegrationTest {

    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:15.2");
    private static final WireMockServer SERVER = new WireMockServer(8090);

    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", CONTAINER::getUsername);
        registry.add("spring.datasource.password", CONTAINER::getPassword);
    }

    @BeforeAll
    static void startContainer() {
        CONTAINER.start();
        SERVER.start();
        configureFor("localhost", 8090);
    }

    @SneakyThrows
    @BeforeEach
    void setUp() {
        stubFor(get(urlEqualTo("/users/token/admin"))
                .willReturn(aResponse().
                        withStatus(HttpStatus.OK.value())
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                        .withBody(MAPPER.writeValueAsString(AuthenticatedUserFactory.getAdmin()))));
        stubFor(get(urlEqualTo("/users/token/journalist"))
                .willReturn(aResponse().
                        withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(MAPPER.writeValueAsString(AuthenticatedUserFactory.getJournalist()))));
        stubFor(get(urlEqualTo("/users/token/subscriber"))
                .willReturn(aResponse().
                        withStatus(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(MAPPER.writeValueAsString(AuthenticatedUserFactory.getSubscriber()))));
    }

    @AfterAll
    static void tearDown() {
        SERVER.stop();
    }
}
