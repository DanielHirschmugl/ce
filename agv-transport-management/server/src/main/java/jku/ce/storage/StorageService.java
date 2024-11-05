package jku.ce.storage;

import jku.ce.config.JwtService;
import jku.ce.exceptions.NoAccessException;
import jku.ce.user.UserRepository;
import jku.ce.vehicles.Vehicle;
import jku.ce.vehicles.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final JwtService jwtService;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<Vehicle>> getAllVehicles(String jwt) throws NoAccessException {
        // Extrahiere den Benutzernamen aus dem JWT
        String username = jwtService.extractUsername(jwt);

        // Überprüfe, ob der Benutzer existiert
        if (userRepository.findByUsername(username).isPresent()) {
            // Alle Fahrzeuge abrufen und zurückgeben
            List<Vehicle> vehicles = vehicleRepository.findAll();
            return ResponseEntity.ok(vehicles);
        } else {
            // Zugriff verweigert, wenn der Benutzer nicht existiert
            throw new NoAccessException("Kein Zugriff");
        }
    }
}
