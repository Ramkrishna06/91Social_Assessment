package com.example.Assignment_91Social.Controller;


import com.example.Assignment_91Social.Models.Part;
import com.example.Assignment_91Social.Services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }


    @GetMapping
    public ResponseEntity<List<Part>> getAllParts() {
        return ResponseEntity.ok(partService.getAllParts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Part>> searchByCategory(@RequestParam String category) {
        return ResponseEntity.ok(partService.searchByCategory(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Part> getPartById(@PathVariable Long id) {
        return ResponseEntity.ok(partService.getPartById(id));
    }

    @PostMapping
    public ResponseEntity<Part> addPart(@RequestBody Part part) {
        Part savedPart = partService.addPart(part);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPart);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Part> updatePart(@PathVariable Long id, @RequestBody Part part) {
        Part updatedPart = partService.updatePart(id, part);
        return ResponseEntity.ok(updatedPart);
    }
}