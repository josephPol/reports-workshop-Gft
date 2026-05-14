package org.example.reportsworskhopgft.eventlog.infrastructure.messaging.truck;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class TruckRegisteredEventTest {

    @Test
    void should_exercise_all_record_methods_for_coverage() {
        // 1. Datos de prueba
        UUID truckId = UUID.randomUUID();
        var position = new TruckRegisteredEvent.Position(10, 20);
        var event1 = new TruckRegisteredEvent(truckId, "Truck 1", position, 100, 12345);
        var event2 = new TruckRegisteredEvent(truckId, "Truck 1", position, 100, 12345);
        var event3 = new TruckRegisteredEvent(UUID.randomUUID(), "Truck 2", position, 50, 67890);

        // 2. Cobertura de Getters (Accessors)
        assertThat(event1.truckId()).isEqualTo(truckId);
        assertThat(event1.name()).isEqualTo("Truck 1");
        assertThat(event1.position()).isEqualTo(position);
        assertThat(event1.capacity()).isEqualTo(100);
        assertThat(event1.timestamp()).isEqualTo(12345);

        // Cobertura de getters del record interno Position
        assertThat(position.x()).isEqualTo(10);
        assertThat(position.y()).isEqualTo(20);

        // 3. Cobertura de toString()
        assertThat(event1.toString()).contains("truckId=" + truckId);
        assertThat(position.toString()).contains("x=10");

        // 4. Cobertura de hashCode()
        assertThat(event1.hashCode()).isEqualTo(event2.hashCode());
        assertThat(event1.hashCode()).isNotEqualTo(event3.hashCode());

        // 5. Cobertura de equals()
        // Caso: mismo objeto
        assertThat(event1).isEqualTo(event1);
        // Caso: objetos con mismos valores
        assertThat(event1).isEqualTo(event2);
        // Caso: objeto diferente
        assertThat(event1).isNotEqualTo(event3);
        // Caso: nulo o clase diferente
        assertThat(event1.equals(null)).isFalse();
        assertThat(event1.equals("no soy un evento")).isFalse();

        // Cobertura de equals para el record interno Position
        assertThat(position).isEqualTo(new TruckRegisteredEvent.Position(10, 20));
        assertThat(position).isNotEqualTo(new TruckRegisteredEvent.Position(0, 0));
    }
}
