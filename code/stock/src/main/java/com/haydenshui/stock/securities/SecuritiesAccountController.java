package com.haydenshui.stock.securities;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.haydenshui.stock.lib.annotation.LogDetails;
import com.haydenshui.stock.lib.dto.risk.RiskAssessmentDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesAccountDTO;
import com.haydenshui.stock.utils.SecurityUtils;

import jakarta.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("/api/account")
public class SecuritiesAccountController {

    private final SecuritiesAccountService securitiesAccountService;

    public SecuritiesAccountController(SecuritiesAccountService securitiesAccountService) {
        this.securitiesAccountService = securitiesAccountService;
    }

    @LogDetails 
    @PutMapping("/new")
    public ResponseEntity<?> createAccount(@RequestBody SecuritiesAccountDTO dto) {
        return ResponseEntity.ok(securitiesAccountService.createAccount(dto));
    } 

    @LogDetails
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String type) {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(securitiesAccountService.getAccountById(userId, type));
    }

    @LogDetails
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody SecuritiesAccountDTO updateDTO) {
        return ResponseEntity.ok(securitiesAccountService.updateAccount(updateDTO));
    }

    @LogDetails
    @PutMapping("/security")
    public ResponseEntity<?> updateSecuritySettings(@RequestBody SecuritiesAccountDTO securityDTO) {
        return ResponseEntity.ok(securitiesAccountService.updateAccount(securityDTO));
    }

    @LogDetails
    @GetMapping("/risk-accessment")
    public ResponseEntity<?> getRiskAssessment(HttpServletRequest request) {
        // TODO: waiting for definition
        return ResponseEntity.ok().build();
    }

    @LogDetails
    @PostMapping("/risk-accessment")
    public ResponseEntity<?> submitRiskAssessment(@RequestBody RiskAssessmentDTO riskDTO,
                                                  HttpServletRequest request) {
        // TODO: waiting for definition
        return ResponseEntity.ok().build();
    }
}
