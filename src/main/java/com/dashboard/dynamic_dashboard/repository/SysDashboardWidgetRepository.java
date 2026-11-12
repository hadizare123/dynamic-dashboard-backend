package com.dashboard.dynamic_dashboard.repository;

import com.dashboard.dynamic_dashboard.model.SysDashboardWidget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SysDashboardWidgetRepository extends JpaRepository<SysDashboardWidget, Integer> {
    List<SysDashboardWidget> findByDashboardIdOrderBySortOrderAsc(Integer dashboardId);
}
