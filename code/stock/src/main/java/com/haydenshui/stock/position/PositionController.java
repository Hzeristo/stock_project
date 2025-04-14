package com.haydenshui.stock.position;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haydenshui.stock.lib.annotation.LogDetails;
import com.haydenshui.stock.utils.SecurityUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/position")
public class PositionController {
    
    private final PositionService positionService;

    private final PositionChangeLogService positionChangeLogService;

    public PositionController(PositionService positionService, PositionChangeLogService positionChangeLogService) {
        this.positionService = positionService;
        this.positionChangeLogService = positionChangeLogService;
    }

    @GetMapping()
    @LogDetails
    public ResponseEntity<?> getPosition() {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(positionService.getPositionBySecuritiesAccountId(userId));
    }
    
    @GetMapping()
    @LogDetails
    public ResponseEntity<?> getPosition(@RequestParam String id) {
        return ResponseEntity.ok(positionService.getPositionById(Long.parseLong(id)));
    }

    @GetMapping("/analysis")
    @LogDetails
    public ResponseEntity<?> getAnalysis() {
        // TODO: waiting for analysis format
        throw new UnsupportedOperationException("Unimplemented method 'getAnalysis'");
    }
    
    @GetMapping("/history")
    @LogDetails
    public ResponseEntity<?> getHistoryByUserId() {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(positionChangeLogService.getLogsBySecuritiesAccountId(userId));
    }

}
