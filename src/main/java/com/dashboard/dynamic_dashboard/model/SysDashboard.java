package com.dashboard.dynamic_dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "sys_dashboards", schema = "dashboard_meta")
public class SysDashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dashboard_id")
    private Integer dashboardId;

    @Column(name = "dashboard_code", nullable = false, length = 50, unique = true)
    private String dashboardCode;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "icon_name", length = 50)
    private String iconName = "ph-squares-four";

    @Column(name = "layout_type", length = 50)
    private String layoutType = "GRID";

    @Column(name = "refresh_rate_sec")
    private Integer refreshRateSec = 0;

    // ذخیره تنظیمات تم به صورت JSON (استفاده از String یا JSONB در صورت مپ کردن)
    @Column(name = "theme_config", columnDefinition = "jsonb")
    private String themeConfig = "{\"bg\": \"#17151C\", \"accent\": \"#AAA2B3\", \"card_bg\": \"#292531\"}";

    @Column(name = "is_public")
    private Boolean isPublic = true;

    @Column(name = "created_by", length = 100)
    private String createdBy = "admin";

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private OffsetDateTime updatedAt;
}
