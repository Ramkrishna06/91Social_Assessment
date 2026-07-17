package com.example.Assignment_91Social.Services;


import com.example.Assignment_91Social.Models.Part;
import com.example.Assignment_91Social.Repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartService{

    private final PartRepository partRepository;

    @Autowired
    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public Part addPart(Part part) {
        if (partRepository.findByPartCode(part.getPartCode()).isPresent()) {
            throw new IllegalArgumentException("Part code already exists: " + part.getPartCode());
        }
        part.setStatus(Part.PartStatus.ACTIVE);
        return partRepository.save(part);
    }

    public Part updatePart(Long partId, Part updatedPart) {
        Part existingPart = partRepository.findById(partId)
                .orElseThrow(() -> new IllegalArgumentException("Part not found: " + partId));

        existingPart.setName(updatedPart.getName());
        existingPart.setCategory(updatedPart.getCategory());
        existingPart.setStatus(updatedPart.getStatus());

        return partRepository.save(existingPart);
    }

    public List<Part> searchByCategory(String category) {
        return partRepository.findByCategory(category);
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part getPartById(Long partId) {
        return partRepository.findById(partId)
                .orElseThrow(() -> new IllegalArgumentException("Part not found: " + partId));
    }
}