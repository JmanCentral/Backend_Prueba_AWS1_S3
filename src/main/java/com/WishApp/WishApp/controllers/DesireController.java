package com.WishApp.WishApp.controllers;
import com.WishApp.WishApp.http.request.DesireRequestDTO;
import com.WishApp.WishApp.http.response.DesireResponseDTO;
import com.WishApp.WishApp.services.Desire.IDesireService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/desire")
public class DesireController {

    private final IDesireService desireService;

    @PostMapping("/up")
    public ResponseEntity<DesireResponseDTO> up(@RequestBody DesireRequestDTO desireRequestDTO) {
        DesireResponseDTO createdDesire = desireService.createDesire(desireRequestDTO);
        return ResponseEntity.status(201).body(createdDesire);
    }

    @GetMapping("/search/user/{id}")
    public ResponseEntity<Page<DesireResponseDTO>> findByUserId(@PathVariable("id") UUID userId ,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "id") String sortBy) {
        Page<DesireResponseDTO> desires = desireService.findByUserId(userId, page, size, sortBy);
        return ResponseEntity.ok(desires);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<DesireResponseDTO> updateDesire(@PathVariable("id") UUID id, @RequestBody DesireRequestDTO desireRequestDTO) {
        DesireResponseDTO updatedDesire = desireService.updateDesire(id, desireRequestDTO);
        return ResponseEntity.ok(updatedDesire);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDesire(@PathVariable("id") UUID id) {
        desireService.deleteDesire(id);
        return ResponseEntity.noContent().build();
    }


}
