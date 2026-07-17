package com.example.Assignment_91Social.Services;

import com.example.Assignment_91Social.Models.Configuration;
import com.example.Assignment_91Social.Models.ConfigurationComponent;
import com.example.Assignment_91Social.Models.Part;
import com.example.Assignment_91Social.Repository.ConfigurationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;
    private final PartService partService;

    @Autowired
    public ConfigurationService(ConfigurationRepository configurationRepository, PartService partService) {
        this.configurationRepository = configurationRepository;
        this.partService = partService;
    }

    // Create a new, empty configuration (e.g. "Hero Sprint Pro - Red")
    public Configuration createConfiguration(String configName, String modelName) {
        Configuration configuration = new Configuration();
        configuration.setConfigName(configName);
        configuration.setModelName(modelName);
        return configurationRepository.save(configuration);
    }

    // Add a part (with quantity) to a configuration
    public Configuration addPartToConfiguration(Long configId, Long partId, Integer quantity) {
        Configuration configuration = getConfigurationById(configId);
        Part part = partService.getPartById(partId);

        ConfigurationComponent component = new ConfigurationComponent();
        component.setConfiguration(configuration);
        component.setPart(part);
        component.setQuantity(quantity);

        configuration.getComponents().add(component);
        return configurationRepository.save(configuration);
    }

    // Remove a part from a configuration
    public Configuration removePartFromConfiguration(Long configId, Long partId) {
        Configuration configuration = getConfigurationById(configId);

        configuration.getComponents()
                .removeIf(component -> component.getPart().getId().equals(partId));

        return configurationRepository.save(configuration);
    }

    // Get all parts + quantities needed for one bicycle (the core "extra feature")
    public List<ConfigurationComponent> getPartsForConfiguration(Long configId) {
        Configuration configuration = getConfigurationById(configId);
        return configuration.getComponents();
    }

    // Used internally by other services (e.g. Pricing Service)
    public Configuration getConfigurationById(Long configId) {
        return configurationRepository.findById(configId)
                .orElseThrow(() -> new IllegalArgumentException("Configuration not found: " + configId));
    }

    public List<Configuration> getAllConfigurations() {
        return configurationRepository.findAll();
    }
}