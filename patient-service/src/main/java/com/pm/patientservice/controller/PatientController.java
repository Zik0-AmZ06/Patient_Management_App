package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient", description = "an API for patient management")
public class PatientController {
    //Dependency injection
    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }

    @GetMapping // Maps HTTP GET requests to this method
    @Operation(summary = "Get patients", description = "Retrieves a list of all patients")
    public ResponseEntity<List<PatientResponseDTO>> getPatients() { // ResponseEntity a spring entity that creates a HTTP response
        List<PatientResponseDTO> patients = patientService.getPatients();
        return ResponseEntity.ok().body(patients);
    }

    @PostMapping // Maps HTTP POST requests to this method
    @Operation(summary = "Create patient", description = "Creates a new patient")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        // @Valid ensures that the request body is validated against the constraints defined in PatientRequestDTO and @RequestBody binds the request body to the method parameter (JSON req to patient DTO req conversion)
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO); // Returns a 200 OK response with the created patient data
    }

    @PutMapping("/{id}") // Maps HTTP PUT requests to this method with a path variable
    @Operation(summary = "Update patient", description = "Updates an existing patient by ID")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable UUID id, // @PathVariable binds the path variable to the method parameter
            @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        // @Validated ensures that the request body is validated against the constraints defined in PatientRequestDTO
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO); // Returns a 200 OK response with the updated patient data
    }

    @DeleteMapping("/{id}") // Maps HTTP DELETE requests to this method with a path variable
    @Operation(summary = "Delete patient", description = "Deletes a patient by ID")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id); // Calls the service to delete the patient
        return ResponseEntity.noContent().build(); // Returns a 204 No Content response indicating successful deletion
    }
}
