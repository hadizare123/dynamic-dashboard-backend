package com.dashboard.dynamic_dashboard.dto;


public class SysDataSourceDTO {
    private Long sourceId;
    private String sourceKey;
    private String displayName;
    private String dbType;
    private String jdbcUrl;
    private String username;
    private Boolean isActive;

    // Constructors, Getters, Setters
    public SysDataSourceDTO() {}

    public SysDataSourceDTO(Long sourceId, String sourceKey, String displayName, String dbType, String jdbcUrl, String username, Boolean isActive) {
        this.sourceId = sourceId;
        this.sourceKey = sourceKey;
        this.displayName = displayName;
        this.dbType = dbType;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.isActive = isActive;
    }

    // Getters and Setters...
    public Long getSourceId() { return sourceId; }
    public void setSourceId(Long sourceId) { this.sourceId = sourceId; }
    public String getSourceKey() { return sourceKey; }
    public void setSourceKey(String sourceKey) { this.sourceKey = sourceKey; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public String getDbType() { return dbType; }
    public void setDbType(String dbType) { this.dbType = dbType; }
    public String getJdbcUrl() { return jdbcUrl; }
    public void setJdbcUrl(String jdbcUrl) { this.jdbcUrl = jdbcUrl; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
