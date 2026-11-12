package com.dashboard.dynamic_dashboard.controller;

import com.dashboard.dynamic_dashboard.model.SysDataSource;
import com.dashboard.dynamic_dashboard.service.SysDataSourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/datasources")
@CrossOrigin(origins = "http://localhost:5173") // اجازه دسترسی از Vue
public class SysDataSourceController {

    private final SysDataSourceService service;

    public SysDataSourceController(SysDataSourceService service) {
        this.service = service;
    }

    // لیست تمام دیتاسورس‌ها
    @GetMapping
    public List<SysDataSource> getAll() {
        return service.getAllDataSources();
    }

    // ذخیره دیتاسورس جدید
    @PostMapping
    public SysDataSource create(@RequestBody SysDataSource dataSource) {
        return service.save(dataSource);
    }

    // تست اتصال (برای اینکه کاربر قبل ذخیره ببینه وصل می‌شه یا نه)
    @PostMapping("/test")
    public ResponseEntity<Boolean> testConnection(@RequestBody Map<String, String> payload) {
        String url = payload.get("jdbcUrl");
        String username = payload.get("username");
        String password = payload.get("password");
        String driver = payload.get("driverClassName");

        boolean isConnected = service.testConnection(url, username, password, driver);
        return ResponseEntity.ok(isConnected);
    }

    // حذف
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
