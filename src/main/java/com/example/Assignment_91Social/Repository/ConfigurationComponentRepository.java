package com.example.Assignment_91Social.Repository;

import com.example.Assignment_91Social.Models.ConfigurationComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationComponentRepository extends JpaRepository<ConfigurationComponent, Long> {

    List<ConfigurationComponent> findByConfigurationId(Long configId);

    void deleteByConfigurationIdAndPartId(Long configId, Long partId);
}