package org.example.reportsworkshopgft.load;

import static org.assertj.core.api.Assertions.assertThat;

import io.gatling.javaapi.core.PopulationBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import org.junit.jupiter.api.Test;

class EventLogLoadSimulationTest {

    @Test
    void simulation_should_build_a_non_null_scenario() {
        EventLogLoadSimulation simulation = new EventLogLoadSimulation();

        ScenarioBuilder scenario = simulation.buildEventLogScenario();

        assertThat(scenario).isNotNull();
    }

    @Test
    void simulation_should_build_a_non_null_population() {
        EventLogLoadSimulation simulation = new EventLogLoadSimulation();

        PopulationBuilder population = simulation.buildPopulation();

        assertThat(population).isNotNull();
    }
}
