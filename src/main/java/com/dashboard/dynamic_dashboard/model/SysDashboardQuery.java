package com.dashboard.dynamic_dashboard.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "sys_dashboard_queries", schema = "dashboard_meta")
public class SysDashboardQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "query_id")
    private Integer queryId;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "source_id", nullable = false)
    private Integer sourceId;

    @Column(name = "report_title", nullable = false, length = 255)
    private String reportTitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
    @Column(name = "sql_text", nullable = false, columnDefinition = "TEXT")
    private String sqlText;

    @Column(name = "chart_type", nullable = false, length = 50)
    private String chartType = "TABLE";

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    // Default Constructor
    public SysDashboardQuery() {
    }

    // Getters and Setters
    public Integer getQueryId() { return queryId; }
    public void setQueryId(Integer queryId) { this.queryId = queryId; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public Integer getSourceId() { return sourceId; }
    public void setSourceId(Integer sourceId) { this.sourceId = sourceId; }

    public String getReportTitle() { return reportTitle; }
    public void setReportTitle(String reportTitle) { this.reportTitle = reportTitle; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSqlText() { return sqlText; }
    public void setSqlText(String sqlText) { this.sqlText = sqlText; }

    public String getChartType() { return chartType; }
    public void setChartType(String chartType) { this.chartType = chartType; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
