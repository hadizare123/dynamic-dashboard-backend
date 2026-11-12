package com.dashboard.dynamic_dashboard.repository;

import com.dashboard.dynamic_dashboard.model.SysDashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SysDashboardRepository extends JpaRepository<SysDashboard, Integer> {
    Optional<SysDashboard> findByDashboardCode(String dashboardCode);
}
