package com.example.Assignment_91Social.Controller;

import com.example.Assignment_91Social.Models.PartPrice;
import com.example.Assignment_91Social.Services.PartPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/part-prices")
public class PartPriceController {

    private final PartPriceService partPriceService;

    @Autowired
    public PartPriceController(PartPriceService partPriceService) {
        this.partPriceService = partPriceService;
    }

    // POST /api/part-prices - admin adds a new price for a part
    @PostMapping
    public ResponseEntity<PartPrice> addNewPrice(
            @RequestParam Long partId,
            @RequestParam BigDecimal unitCost,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate effectiveFrom) {

        PartPrice savedPrice = partPriceService.addNewPrice(partId, unitCost, effectiveFrom);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrice);
    }

    // GET /api/part-prices/current?partId=1 - today's price
    @GetMapping("/current")
    public ResponseEntity<PartPrice> getCurrentPrice(@RequestParam Long partId) {
        return ResponseEntity.ok(partPriceService.getCurrentPrice(partId));
    }

    // GET /api/part-prices/valid?partId=1&date=2026-01-01 - price on a specific date
    @GetMapping("/valid")
    public ResponseEntity<PartPrice> getValidPriceOnDate(
            @RequestParam Long partId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(partPriceService.getValidPrice(partId, date));
    }

    // GET /api/part-prices/history?partId=1 - full price history (admin screen)
    @GetMapping("/history")
    public ResponseEntity<List<PartPrice>> getPriceHistory(@RequestParam Long partId) {
        return ResponseEntity.ok(partPriceService.getPriceHistory(partId));
    }
}