package org.example.reportsworskhopgft;

import org.example.reportsworskhopgft.blockedorder.infrastructure.BlockedOrderRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        useMainMethod = SpringBootTest.UseMainMethod.ALWAYS,
        args = {
                "--spring.main.web-application-type=none",
                "--spring.main.banner-mode=off",
                "--spring.jmx.enabled=false",
                "--management.endpoints.enabled-by-default=false",
                "--spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
                        + "org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration"
        }
)
class ReportsWorskhopGftApplicationMainTest {

    @MockitoBean
    private EventLogRepositoryJPA eventLogRepositoryJPA;

    @MockitoBean
    private BlockedOrderRepositoryJPA blockedOrderRepositoryJPA;

    @MockitoBean
    private ConnectionFactory connectionFactory;

    @Test
    void main_should_run() {
        // Intentionally empty: context bootstrap runs through the app's main method.
    }
}
