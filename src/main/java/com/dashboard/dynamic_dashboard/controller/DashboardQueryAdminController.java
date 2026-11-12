package com.dashboard.dynamic_dashboard.controller;

import com.dashboard.dynamic_dashboard.model.SysDashboardQuery;
import com.dashboard.dynamic_dashboard.model.SysDataSource;
import com.dashboard.dynamic_dashboard.repository.SysDashboardQueryRepository;
import com.dashboard.dynamic_dashboard.service.SysDataSourceService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard/queries")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardQueryAdminController {

    private final SysDashboardQueryRepository queryRepository;
    private final SysDataSourceService dataSourceService;

    public DashboardQueryAdminController(
            SysDashboardQueryRepository queryRepository,
            SysDataSourceService dataSourceService
    ) {
        this.queryRepository = queryRepository;
        this.dataSourceService = dataSourceService;
    }

    public record SourceOption(Integer sourceId, String displayName, String sourceKey) {}

    public record CreateQueryRequest(
            Integer sourceId,
            Integer categoryId,
            String reportTitle,
            String description,
            String sqlText,
            String chartType,
            Boolean isActive
    ) {}

    @GetMapping
    public List<SysDashboardQuery> listQueries() {
        return queryRepository.findAll();
    }

    // فقط اطلاعات لازم برای انتخاب دیتاسورس؛ بدون رمز و JDBC URL
    @GetMapping("/sources")
    public List<SourceOption> listSources() {
        return dataSourceService.getActiveDataSources().stream()
                .map(s -> new SourceOption(
                        s.getSourceId(), s.getDisplayName(), s.getSourceKey()
                ))
                .toList();
    }

    // برای ارسال POST در تنظیمات دارای CSRF
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SysDashboardQuery createQuery(@RequestBody CreateQueryRequest request) {
        if (request.sourceId() == null ||
                dataSourceService.getById(request.sourceId())
                        .filter(s -> Boolean.TRUE.equals(s.getIsActive()))
                        .isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "منبع دادهٔ معتبر و فعال انتخاب کن"
            );
        }

        if (request.reportTitle() == null ||
                request.reportTitle().isBlank() ||
                request.reportTitle().length() > 255 ||
                request.sqlText() == null ||
                request.sqlText().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "عنوان و متن کوئری الزامی‌اند"
            );
        }

        String chartType = request.chartType() == null ||
                request.chartType().isBlank()
                ? "TABLE"
                : request.chartType().trim();

        if (chartType.length() > 50) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "نوع نمایش نامعتبر است"
            );
        }

        SysDashboardQuery query = new SysDashboardQuery();
        query.setSourceId(request.sourceId());
        query.setCategoryId(request.categoryId());
        query.setReportTitle(request.reportTitle().trim());
        query.setDescription(request.description());
        query.setSqlText(request.sqlText().trim());
        query.setChartType(chartType);
        query.setIsActive(request.isActive() == null ? true : request.isActive());

        return queryRepository.save(query);
    }
}
