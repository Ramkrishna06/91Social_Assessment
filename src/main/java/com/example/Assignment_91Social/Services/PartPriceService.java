package com.example.Assignment_91Social.Services;

import com.example.Assignment_91Social.Models.Part;
import com.example.Assignment_91Social.Models.PartPrice;
import com.example.Assignment_91Social.Repository.PartPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PartPriceService {

    private final PartPriceRepository partPriceRepository;
    private final PartService partService;

    @Autowired
    public PartPriceService(PartPriceRepository partPriceRepository, PartService partService) {
        this.partPriceRepository = partPriceRepository;
        this.partService = partService;
    }

    // Add a new price for a part, starting from a given date
    public PartPrice addNewPrice(Long partId, BigDecimal unitCost, LocalDate effectiveFrom) {
        Part part = partService.getPartById(partId);

        // Close the old price row (if one exists) so history stays clean
        partPriceRepository.findValidPriceOnDate(partId, effectiveFrom).ifPresent(oldPrice -> {
            oldPrice.setEffectiveTo(effectiveFrom.minusDays(1));
            partPriceRepository.save(oldPrice);
        });

        PartPrice newPrice = new PartPrice();
        newPrice.setPart(part);
        newPrice.setUnitCost(unitCost);
        newPrice.setEffectiveFrom(effectiveFrom);
        newPrice.setEffectiveTo(null); // open-ended, this is now the current price

        return partPriceRepository.save(newPrice);
    }

    // Get the valid price of a part for a specific date
    public PartPrice getValidPrice(Long partId, LocalDate date) {
        return partPriceRepository.findValidPriceOnDate(partId, date)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No valid price found for part " + partId + " on " + date));
    }

    // Get today's price - simple helper so callers don't need to pass LocalDate.now() everywhere
    public PartPrice getCurrentPrice(Long partId) {
        return getValidPrice(partId, LocalDate.now());
    }

    // Full price history for one part (admin screen)
    public List<PartPrice> getPriceHistory(Long partId) {
        return partPriceRepository.findByPartIdOrderByEffectiveFromDesc(partId);
    }
}