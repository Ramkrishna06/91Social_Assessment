package com.example.Assignment_91Social.Controller;

import com.example.Assignment_91Social.Models.Configuration;
import com.example.Assignment_91Social.Models.ConfigurationComponent;
import com.example.Assignment_91Social.Services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }


    @GetMapping
    public ResponseEntity<List<Configuration>> getAllConfigurations() {
        return ResponseEntity.ok(configurationService.getAllConfigurations());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Configuration> getConfigurationById(@PathVariable Long id) {
        return ResponseEntity.ok(configurationService.getConfigurationById(id));
    }


//    @PostMapping
//    public ResponseEntity<Configuration> createConfiguration(
//            @RequestParam String configName,
//            @RequestParam String modelName) {
//
//        Configuration savedConfig = configurationService.createConfiguration(configName, modelName);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedConfig);
//    }
//
//
//    @PostMapping("/{id}/parts")
//    public ResponseEntity<Configuration> addPartToConfiguration(
//            @PathVariable Long id,
//            @RequestParam Long partId,
//            @RequestParam Integer quantity) {
//
//        Configuration updatedConfig = configurationService.addPartToConfiguration(id, partId, quantity);
//        return ResponseEntity.ok(updatedConfig);
//    }


    @DeleteMapping("/{id}/parts/{partId}")
    public ResponseEntity<Configuration> removePartFromConfiguration(
            @PathVariable Long id,
            @PathVariable Long partId) {

        Configuration updatedConfig = configurationService.removePartFromConfiguration(id, partId);
        return ResponseEntity.ok(updatedConfig);
    }

    @GetMapping("/{id}/parts")
    public ResponseEntity<List<ConfigurationComponent>> getPartsForConfiguration(@PathVariable Long id) {
        return ResponseEntity.ok(configurationService.getPartsForConfiguration(id));
    }
}