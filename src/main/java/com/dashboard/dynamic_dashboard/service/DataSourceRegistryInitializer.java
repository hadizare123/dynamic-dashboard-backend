package com.dashboard.dynamic_dashboard.service;

import com.dashboard.dynamic_dashboard.model.SysDataSource;
import com.dashboard.dynamic_dashboard.repository.SysDataSourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSourceRegistryInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSourceRegistryInitializer.class);

    private final SysDataSourceRepository repository;
    private final DynamicDataSourceRegistry registry;

    public DataSourceRegistryInitializer(SysDataSourceRepository repository,
                                         DynamicDataSourceRegistry registry) {
        this.repository = repository;
        this.registry = registry;
    }

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) {
        for (SysDataSource src : repository.findByIsActiveTrue()) {
            try {
                registry.register(src);
            } catch (Exception e) {
                // منبع خراب نباید کل برنامه را پایین بیاورد
                log.error("Failed to register DataSource {}: {}", src.getSourceKey(), e.getMessage());
            }
        }
    }
}
