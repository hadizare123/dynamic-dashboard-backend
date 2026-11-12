package com.dashboard.dynamic_dashboard.controller;

import com.dashboard.dynamic_dashboard.service.QueryExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/queries")
@CrossOrigin(origins = {"http://localhost:5173", "http://127.0.0.1:5173"}) // برای ارتباط امن با Vue.js
public class QueryExecutionController {

    private static final Logger log = LoggerFactory.getLogger(QueryExecutionController.class);

    private final QueryExecutionService queryExecutionService;

    public QueryExecutionController(QueryExecutionService queryExecutionService) {
        this.queryExecutionService = queryExecutionService;
    }

    /**
     * اجرای کوئری با شناسه مشخص و پارامترهای اختیاری
     * مثال فراخوانی:
     * GET /api/queries/1/run
     * GET /api/queries/1/run?status=ACTIVE&limit=100
     */
    @GetMapping("/{queryId}/run")
    public ResponseEntity<?> runQuery(
            @PathVariable Integer queryId,
            @RequestParam(required = false) Map<String, Object> queryParams) {

        log.info("درخواست اجرای کوئری با شناسه: {} | پارامترها: {}", queryId, queryParams);

        try {
            Map<String, Object> params = (queryParams != null) ? queryParams : new HashMap<>();
            List<Map<String, Object>> result = queryExecutionService.executeQueryById(queryId, params);

            return ResponseEntity.ok(result);

        } catch (IllegalArgumentException e) {
            log.warn("کوئری پیدا نشد یا منبع نامعتبر است: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));

        } catch (SecurityException e) {
            log.error("خطای امنیتی در اجرای کوئری: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));

        } catch (IllegalStateException e) {
            log.warn("وضعیت نامعتبر کوئری: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));

        } catch (Exception e) {
            log.error("خطای سیستمی یا دیتابیسی حین اجرای کوئری {}: {}", queryId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "خطا در اجرای کوئری در دیتابیس مقصد: " + e.getMessage()));
        }
    }
}
