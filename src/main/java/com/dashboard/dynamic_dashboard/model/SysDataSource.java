package com.dashboard.dynamic_dashboard.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sys_data_sources", schema = "dashboard_meta")
public class SysDataSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "source_id")
    private Integer sourceId;

    @Column(name = "source_key", nullable = false, unique = true, length = 50)
    private String sourceKey;
    @Column(name = "code", length = 50)
    private String code;
    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;

    @Column(name = "db_type", nullable = false, length = 50)
    private String dbType;

    @Column(name = "jdbc_url", nullable = false, columnDefinition = "TEXT")
    private String jdbcUrl;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "password_enc", length = 255)
    private String passwordEnc;

    @Column(name = "pool_min_idle")
    private Integer poolMinIdle = 2;

    @Column(name = "pool_max_size")
    private Integer poolMaxSize = 10;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    public SysDataSource() {
    }

    // Getters and Setters
    public Integer getSourceId() { return sourceId; }
    public void setSourceId(Integer sourceId) { this.sourceId = sourceId; }

    public String getSourceKey() { return sourceKey; }
    public void setSourceKey(String sourceKey) { this.sourceKey = sourceKey; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getDbType() { return dbType; }
    public void setDbType(String dbType) { this.dbType = dbType; }

    public String getJdbcUrl() { return jdbcUrl; }
    public void setJdbcUrl(String jdbcUrl) { this.jdbcUrl = jdbcUrl; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordEnc() { return passwordEnc; }
    public void setPasswordEnc(String passwordEnc) { this.passwordEnc = passwordEnc; }
    public Integer getPoolMinIdle() { return poolMinIdle; }
    public void setPoolMinIdle(Integer poolMinIdle) { this.poolMinIdle = poolMinIdle; }

    public Integer getPoolMaxSize() { return poolMaxSize; }
    public void setPoolMaxSize(Integer poolMaxSize) { this.poolMaxSize = poolMaxSize; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
