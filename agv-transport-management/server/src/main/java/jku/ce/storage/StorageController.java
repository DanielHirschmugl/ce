package jku.ce.storage;

import jku.ce.exceptions.NoAccessException;
import jku.ce.vehicles.Vehicle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/getAllVehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicles(@RequestHeader("Authorization") String token) throws NoAccessException {
        // Entferne das "Bearer " Präfix, falls vorhanden
        String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;

        // Rufe den Service auf, um alle Fahrzeuge zu erhalten
        return storageService.getAllVehicles(jwt);
    }
}
