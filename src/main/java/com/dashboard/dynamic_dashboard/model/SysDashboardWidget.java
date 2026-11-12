package com.dashboard.dynamic_dashboard.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "sys_dashboard_widgets", schema = "dashboard_meta")
public class SysDashboardWidget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "widget_id")
    private Integer widgetId;

    @Column(name = "dashboard_id", nullable = false)
    private Integer dashboardId;

    @Column(name = "query_id", nullable = false)
    private Integer queryId;

    @Column(name = "widget_title", length = 150)
    private String widgetTitle;

    @Column(name = "widget_type", nullable = false, length = 50)
    private String widgetType; // مثل TABLE, CHART, KPI

    @Column(name = "grid_pos_x")
    private Integer gridPosX = 0;

    @Column(name = "grid_pos_y")
    private Integer gridPosY = 0;

    @Column(name = "grid_width")
    private Integer gridWidth = 6;

    @Column(name = "grid_height")
    private Integer gridHeight = 4;

    @Column(name = "widget_props", columnDefinition = "jsonb")
    private String widgetProps = "{}";

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_visible")
    private Boolean isVisible = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}
