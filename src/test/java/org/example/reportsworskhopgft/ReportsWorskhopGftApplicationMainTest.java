package org.example.reportsworskhopgft;

import org.example.reportsworskhopgft.blockedorder.infrastructure.BlockedOrderRepositoryJPA;
import org.example.reportsworskhopgft.eventlog.infrastructure.EventLogRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
            "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration",
            "spring.main.web-application-type=none",
            "spring.jmx.enabled=false",
            "management.endpoints.enabled-by-default=false"
        })
class ReportsWorskhopGftApplicationMainTest {

    @MockitoBean private EventLogRepositoryJPA eventLogRepositoryJPA;
    @MockitoBean private BlockedOrderRepositoryJPA blockedOrderRepositoryJPA;
    @MockitoBean private ConnectionFactory connectionFactory;

    @Test
    void main_should_run() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            ReportsWorskhopGftApplication.main(new String[] {});
            mocked.verify(
                    () ->
                            SpringApplication.run(
                                    ReportsWorskhopGftApplication.class, new String[] {}));
        }
    }
}
