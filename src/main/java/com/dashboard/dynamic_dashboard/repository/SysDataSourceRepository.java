package com.dashboard.dynamic_dashboard.repository;

import com.dashboard.dynamic_dashboard.model.SysDataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysDataSourceRepository extends JpaRepository<SysDataSource, Long> {

    // پیدا کردن دیتاسورس بر اساس نام یونیک کد سیستم
    Optional<SysDataSource> findByCode(String code);

    // پیدا کردن تمام دیتاسورس‌های فعال (مثلا اوراکل پرسنلی، پستگرس و ...)
    List<SysDataSource> findByIsActiveTrue();
}
