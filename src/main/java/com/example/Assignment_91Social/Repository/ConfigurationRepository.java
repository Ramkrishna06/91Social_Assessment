package com.example.Assignment_91Social.Repository;

import com.example.Assignment_91Social.Models.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {

    Optional<Configuration> findByConfigName(String configName);

    List<Configuration> findByModelName(String modelName);
}