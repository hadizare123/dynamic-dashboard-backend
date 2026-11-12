package com.dashboard.dynamic_dashboard.service;

import com.dashboard.dynamic_dashboard.model.SysDataSource;
import com.dashboard.dynamic_dashboard.repository.SysDataSourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

@Service
public class SysDataSourceService {

    private final SysDataSourceRepository repository;

    public SysDataSourceService(SysDataSourceRepository repository) {
        this.repository = repository;
    }

    // گرفتن تمام دیتاسورس‌های تعریف شده
    public List<SysDataSource> getAllDataSources() {
        return repository.findAll();
    }

    // گرفتن دیتاسورس‌های فعال سیستم
    public List<SysDataSource> getActiveDataSources() {
        return repository.findByIsActiveTrue();
    }

    // پیدا کردن با ID
    public Optional<SysDataSource> getById(int id) {
        return repository.findById(id);
    }

    // پیدا کردن با کد یکتا (مثلا ATTENDANCE_ORACLE)
    public Optional<SysDataSource> getByCode(String code) {
        return repository.findByCode(code);
    }

    // ذخیره یا به‌روزرسانی دیتاسورس
    @Transactional
    public SysDataSource save(SysDataSource dataSource) {
        return repository.save(dataSource);
    }

    // حذف دیتاسورس
    @Transactional
    public void delete(int id) {
        repository.deleteById(id);
    }

    /**
     * متد اعتبارسنجی و تست زنده کانکشن:
     * بدون نیاز به ساخت کانکشن‌پول دائمی، یک بار تلاش می‌کنه لاگین کنه
     */
    public boolean testConnection(String jdbcUrl, String username, String password, String driverClassName) {
        try {
            if (driverClassName != null && !driverClassName.isBlank()) {
                Class.forName(driverClassName);
            }
            // تایم‌اوت ۵ ثانیه‌ای تا سیستم فریز نشه
            DriverManager.setLoginTimeout(5);
            try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
                return conn.isValid(5);
            }
        } catch (Exception e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
}
