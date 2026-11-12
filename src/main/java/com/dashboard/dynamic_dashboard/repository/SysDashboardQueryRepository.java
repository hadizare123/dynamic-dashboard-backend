package com.dashboard.dynamic_dashboard.repository;

import com.dashboard.dynamic_dashboard.model.SysDashboardQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SysDashboardQueryRepository
        extends JpaRepository<SysDashboardQuery, Integer> {

    List<SysDashboardQuery> findByIsActiveTrue();

    List<SysDashboardQuery> findByCategoryIdAndIsActiveTrue(Integer categoryId);
}
