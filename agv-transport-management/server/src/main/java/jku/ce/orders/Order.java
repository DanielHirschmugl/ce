package jku.ce.orders;

import jakarta.persistence.*;
import jku.ce.vehicles.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String location;
    private String material;
    private int amount;
    private int priority;
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    private State state;

}
