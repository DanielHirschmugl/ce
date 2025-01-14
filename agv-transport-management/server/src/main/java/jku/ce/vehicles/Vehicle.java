package jku.ce.vehicles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_vehicle")
public class Vehicle {

    @Id
    @GeneratedValue
    private Long id;
    private double longitude;
    private double latitude;
    private double batteryCharge;
}
