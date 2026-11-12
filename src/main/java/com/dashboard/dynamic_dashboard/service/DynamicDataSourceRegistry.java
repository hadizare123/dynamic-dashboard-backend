package com.dashboard.dynamic_dashboard.service;

import com.dashboard.dynamic_dashboard.model.SysDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicDataSourceRegistry {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceRegistry.class);

    private final Map<Integer, DataSource> dataSources = new ConcurrentHashMap<>();

    public void register(SysDataSource src) {
        if (!Boolean.TRUE.equals(src.getIsActive())) return;

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(src.getJdbcUrl());
        cfg.setUsername(src.getUsername());
        cfg.setPassword(src.getPasswordEnc());
        cfg.setPoolName("ds-" + src.getSourceKey());
        cfg.setMinimumIdle(src.getPoolMinIdle() != null ? src.getPoolMinIdle() : 2);
        cfg.setMaximumPoolSize(src.getPoolMaxSize() != null ? src.getPoolMaxSize() : 10);
        cfg.setConnectionTimeout(10_000);

        HikariDataSource ds = new HikariDataSource(cfg);
        DataSource old = dataSources.put(src.getSourceId(), ds);
        if (old instanceof HikariDataSource h) h.close();
        log.info("DataSource registered: id={} key={}", src.getSourceId(), src.getSourceKey());
    }

    public DataSource get(Integer sourceId) {
        DataSource ds = dataSources.get(sourceId);
        if (ds == null) {
            throw new IllegalArgumentException("DataSource not registered: " + sourceId);
        }
        return ds;
    }

    public boolean has(Integer sourceId) {
        return dataSources.containsKey(sourceId);
    }
}
