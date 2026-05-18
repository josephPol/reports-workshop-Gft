package org.example.reportsworskhopgft;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class ReportsWorskhopGftApplicationMainTest extends AbstractIntegrationTest {

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
