package org.example.reportsworskhopgft;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mockStatic;

class ReportsWorskhopGftApplicationTests {

    @Test
    void shouldCreateApplicationInstance() {
        assertNotNull(new ReportsWorskhopGftApplication());
    }

    @Test
    void shouldDelegateBootstrappingToSpringApplication() {
        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            ReportsWorskhopGftApplication.main(new String[]{"--spring.main.web-application-type=none"});

            springApplication.verify(() ->
                    SpringApplication.run(ReportsWorskhopGftApplication.class,
                            new String[]{"--spring.main.web-application-type=none"}));
        }
    }
}
