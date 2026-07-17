package com.example.Assignment_91Social.Repository;


import com.example.Assignment_91Social.Models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {

    Optional<Part> findByPartCode(String partCode);

    List<Part> findByCategory(String category);

    List<Part> findByStatus(Part.PartStatus status);
}