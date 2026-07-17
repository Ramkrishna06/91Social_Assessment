package com.example.Assignment_91Social.Services;

import com.example.Assignment_91Social.Models.ConfigurationComponent;
import com.example.Assignment_91Social.Models.PartPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PricingService {

    private final ConfigurationService configurationService;
    private final PartPriceService partPriceService;

    @Autowired
    public PricingService(ConfigurationService configurationService, PartPriceService partPriceService) {
        this.configurationService = configurationService;
        this.partPriceService = partPriceService;
    }

    // Calculate the full price
    //THIS Nedded to be fixed have some bugs
    public PriceBreakdown calculatePrice(Long configId, LocalDate date) {

        List<ConfigurationComponent> components = configurationService.getPartsForConfiguration(configId);

        List<PriceLine> lines = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (ConfigurationComponent component : components) {

            Long partId = component.getPart().getId();
            String partName = component.getPart().getName();
            Integer quantity = component.getQuantity();

            PriceLine line = new PriceLine();
            line.setPartName(partName);
            line.setQuantity(quantity);


        //
            try {
                PartPrice partPrice = partPriceService.getValidPrice(partId, date);
                BigDecimal lineTotal = partPrice.getUnitCost().multiply(BigDecimal.valueOf(quantity));

                line.setUnitCost(partPrice.getUnitCost());
                line.setLineTotal(lineTotal);
                line.setPriceMissing(false);

                grandTotal = grandTotal.add(lineTotal);

            } catch (IllegalArgumentException ex) {
                // No valid price found - this row gets flagged, matches Screen 3 requirement
                line.setUnitCost(null);
                line.setLineTotal(null);
                line.setPriceMissing(true);
            }

            lines.add(line);
        }

        PriceBreakdown breakdown = new PriceBreakdown();
        breakdown.setConfigId(configId);
        breakdown.setLines(lines);
        breakdown.setGrandTotal(grandTotal);

        return breakdown;
    }

    // Simple inner classes to hold the result
    public static class PriceLine {
        private String partName;
        private Integer quantity;
        private BigDecimal unitCost;
        private BigDecimal lineTotal;
        private boolean priceMissing;

        // getters and setters
        public String getPartName() { return partName; }
        public void setPartName(String partName) { this.partName = partName; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getUnitCost() { return unitCost; }
        public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
        public BigDecimal getLineTotal() { return lineTotal; }
        public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
        public boolean isPriceMissing() { return priceMissing; }
        public void setPriceMissing(boolean priceMissing) { this.priceMissing = priceMissing; }
    }

    public static class PriceBreakdown {
        private Long configId;
        private List<PriceLine> lines;
        private BigDecimal grandTotal;

        // getters and setters
        public Long getConfigId() { return configId; }
        public void setConfigId(Long configId) { this.configId = configId; }
        public List<PriceLine> getLines() { return lines; }
        public void setLines(List<PriceLine> lines) { this.lines = lines; }
        public BigDecimal getGrandTotal() { return grandTotal; }
        public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    }
}