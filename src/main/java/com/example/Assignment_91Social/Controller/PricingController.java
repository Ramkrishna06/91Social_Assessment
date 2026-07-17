package com.example.Assignment_91Social.Controller;

import com.example.Assignment_91Social.Services.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/pricing")
public class PricingController {

    private final PricingService pricingService;

    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    // GET /api/pricing/{configId}?date=2026-07-17
    // If date is not passed, defaults to today
    @GetMapping("/{configId}")
    public ResponseEntity<PricingService.PriceBreakdown> getPriceBreakdown(
            @PathVariable Long configId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate priceDate = (date != null) ? date : LocalDate.now();
        PricingService.PriceBreakdown breakdown = pricingService.calculatePrice(configId, priceDate);

        return ResponseEntity.ok(breakdown);
    }
}