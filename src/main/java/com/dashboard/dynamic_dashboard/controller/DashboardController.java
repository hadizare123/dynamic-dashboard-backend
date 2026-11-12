package com.dashboard.dynamic_dashboard.controller;

import com.dashboard.dynamic_dashboard.model.SysDashboard;
import com.dashboard.dynamic_dashboard.model.SysDashboardWidget;
import com.dashboard.dynamic_dashboard.model.SysDashboardQuery;
import com.dashboard.dynamic_dashboard.repository.SysDashboardRepository;
import com.dashboard.dynamic_dashboard.repository.SysDashboardWidgetRepository;
import com.dashboard.dynamic_dashboard.repository.SysDashboardQueryRepository;
import com.dashboard.dynamic_dashboard.service.QueryExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/dashboards")
@CrossOrigin(origins = "http://localhost:5173")
public class DashboardController {

    private final SysDashboardRepository dashboardRepository;
    private final SysDashboardWidgetRepository widgetRepository;
    private final SysDashboardQueryRepository queryRepository;
    private final QueryExecutionService queryExecutionService;

    public DashboardController(SysDashboardRepository dashboardRepository,
                               SysDashboardWidgetRepository widgetRepository,
                               SysDashboardQueryRepository queryRepository,
                               QueryExecutionService queryExecutionService) {
        this.dashboardRepository = dashboardRepository;
        this.widgetRepository = widgetRepository;
        this.queryRepository = queryRepository;
        this.queryExecutionService = queryExecutionService;
    }

    // GET /api/dashboards -> لیست همه داشبوردها (بدون اجرای کوئری)
    @GetMapping
    public List<SysDashboard> listDashboards() {
        return dashboardRepository.findAll();
    }



    // GET /api/dashboards/{dashboardId} -> جزئیات داشبورد + ویجت‌ها + زنده اجرا شدن کوئری هر ویجت
    @GetMapping("/{dashboardId}")
    public ResponseEntity<Map<String, Object>> getDashboard(@PathVariable Integer dashboardId) {
        Optional<SysDashboard> dashOpt = dashboardRepository.findById(dashboardId);
        if (dashOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SysDashboard dash = dashOpt.get();
        List<SysDashboardWidget> widgets =
                widgetRepository.findByDashboardIdOrderBySortOrderAsc(dashboardId);

        // ساخت لیست نتایج ویجت‌ها به همراه دیتای اجراشده
        List<Map<String, Object>> widgetsWithData = new ArrayList<>();
        for (SysDashboardWidget w : widgets) {
            Map<String, Object> wMap = new LinkedHashMap<>();
            wMap.put("widget", w);

            try {
                SysDashboardQuery q = w.getQueryId() == null
                        ? null
                        : queryRepository.findById(w.getQueryId()).orElse(null);

                if (q != null && Boolean.TRUE.equals(q.getIsActive())) {
                    List<Map<String, Object>> rows =
                            queryExecutionService.executeQueryById(w.getQueryId(), Map.of());
                    wMap.put("data", rows);
                    wMap.put("columns", rows.isEmpty() ? List.of() : new ArrayList<>(rows.get(0).keySet()));
                    wMap.put("status", "OK");
                } else {
                    wMap.put("data", List.of());
                    wMap.put("columns", List.of());
                    wMap.put("status", q == null ? "NO_QUERY" : "INACTIVE");
                }
            } catch (Exception ex) {
                wMap.put("data", List.of());
                wMap.put("columns", List.of());
                wMap.put("status", "ERROR");
                wMap.put("error", ex.getMessage());
            }

            widgetsWithData.add(wMap);
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("dashboard", dash);
        body.put("widgets", widgetsWithData);

        return ResponseEntity.ok(body);
    }
}
