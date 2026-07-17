package com.example.Assignment_91Social.Repository;


import com.example.Assignment_91Social.Models.PartPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartPriceRepository extends JpaRepository<PartPrice, Long> {

    Optional<PartPrice> findValidPriceOnDate(@Param("partId") Long partId,
                                             @Param("date") LocalDate date);

    List<PartPrice> findByPartIdOrderByEffectiveFromDesc(Long partId);
}