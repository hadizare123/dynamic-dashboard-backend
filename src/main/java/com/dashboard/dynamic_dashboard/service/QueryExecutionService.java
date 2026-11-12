package com.dashboard.dynamic_dashboard.service;

import com.dashboard.dynamic_dashboard.model.SysDashboardQuery;
import com.dashboard.dynamic_dashboard.repository.SysDashboardQueryRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class QueryExecutionService {

    private final DynamicDataSourceRegistry registry;
    private final SysDashboardQueryRepository queryRepository;

    public QueryExecutionService(DynamicDataSourceRegistry registry,
                                 SysDashboardQueryRepository queryRepository) {
        this.registry = registry;
        this.queryRepository = queryRepository;
    }

    public List<Map<String, Object>> executeQueryById(Integer queryId, Map<String, Object> params) {
        // ۱. خواندن متادیتای کوئری از دیتابیس
        SysDashboardQuery queryMeta = queryRepository.findById(queryId)
                .orElseThrow(() -> new IllegalArgumentException("کوئری مورد نظر یافت نشد: " + queryId));

        if (!Boolean.TRUE.equals(queryMeta.getIsActive())) {
            throw new IllegalStateException("این کوئری غیرفعال است!");
        }

        String sql = queryMeta.getSqlText().trim();

        // ۲. دریافت DataSource مربوط به این دیتابیس هدف
        DataSource ds = registry.get(queryMeta.getSourceId());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);

        // تنظیم تایم‌اوت اجرایی (۱۵ ثانیه)
        jdbcTemplate.setQueryTimeout(15);

        // ۳. اجرای کوئری در سطح کانکشن Read-Only فیزیکی دیتابیس
        return jdbcTemplate.execute(new ConnectionCallback<List<Map<String, Object>>>() {
            @Override
            public List<Map<String, Object>> doInConnection(Connection con) throws SQLException, DataAccessException {
                boolean originalReadOnly = con.isReadOnly();
                try {
                    // وادار کردن درایور به Read-Only Transaction
                    con.setReadOnly(true);

                    NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
                    return namedTemplate.queryForList(sql, params == null ? Map.of() : params);
                } finally {
                    // ریست کردن حالت کانکشن برای بازگشت به Pool
                    try {
                        con.setReadOnly(originalReadOnly);
                    } catch (SQLException ignored) {
                    }
                }
            }
        });
    }
}
