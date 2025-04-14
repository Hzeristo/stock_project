package com.haydenshui.stock.securities;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.haydenshui.stock.lib.annotation.LogDetails;
import com.haydenshui.stock.lib.dto.risk.RiskAssessmentDTO;
import com.haydenshui.stock.lib.dto.securities.CorporateSecuritiesAccountDTO;
import com.haydenshui.stock.lib.dto.securities.IndividualSecuritiesAccountDTO;
import com.haydenshui.stock.lib.dto.securities.SecuritiesAccountDTO;
import com.haydenshui.stock.utils.SecurityUtils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/account")
public class SecuritiesAccountController{

    private final IndividualSecuritiesAccountService individualSecuritiesAccountService;

    private final CorporateSecuritiesAccountService corporateSecuritiesAccountService;

    public SecuritiesAccountController(IndividualSecuritiesAccountService individualSecuritiesAccountService, CorporateSecuritiesAccountService corporateSecuritiesAccountService) {
        this.individualSecuritiesAccountService = individualSecuritiesAccountService;
        this.corporateSecuritiesAccountService = corporateSecuritiesAccountService;
    }

    @PreAuthorize("#userId == authentication.principal")
    @LogDetails
    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(@RequestParam String type) {
        Long userId = SecurityUtils.getCurrentUserId();
        
        if ("individual".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(individualSecuritiesAccountService.getAccountById(userId));
        } else if ("corporate".equalsIgnoreCase(type)) {
            return ResponseEntity.ok(corporateSecuritiesAccountService.getAccountById(userId));
        } else {
            throw new IllegalArgumentException("Unknown account type: " + type);
        }
    }

    @PreAuthorize("#userId == authentication.principal")
    @LogDetails
    @PutMapping("/profile")
    public ResponseEntity<?> updateUserProfile(@RequestBody SecuritiesAccountDTO updateDTO) {
        if (updateDTO instanceof IndividualSecuritiesAccountDTO){
            return ResponseEntity.ok(individualSecuritiesAccountService.updateAccount((IndividualSecuritiesAccountDTO)updateDTO));
        } else if (updateDTO instanceof CorporateSecuritiesAccountDTO){
            return ResponseEntity.ok(corporateSecuritiesAccountService.updateAccount((CorporateSecuritiesAccountDTO)updateDTO));
        } else {
            throw new IllegalArgumentException("Unknown account type: " + updateDTO.getClass().getName());
        }
    }

    @PreAuthorize("#userId == authentication.principal")
    @LogDetails
    @PutMapping("/security")
    public ResponseEntity<?> updateSecuritySettings(@RequestBody SecuritiesAccountDTO securityDTO) {
        if (securityDTO instanceof IndividualSecuritiesAccountDTO){
            return ResponseEntity.ok(individualSecuritiesAccountService.updateAccount((IndividualSecuritiesAccountDTO)securityDTO));
        } else if (securityDTO instanceof CorporateSecuritiesAccountDTO){
            return ResponseEntity.ok(corporateSecuritiesAccountService.updateAccount((CorporateSecuritiesAccountDTO)securityDTO));
        } else {
            throw new IllegalArgumentException("Unknown account type: " + securityDTO.getClass().getName());
        }
    }

    @PreAuthorize("#userId == authentication.principal")
    @LogDetails
    @GetMapping("/risk-accessment")
    public ResponseEntity<?> getRiskAssessment(HttpServletRequest request) {
        // TODO: waiting for definition
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("#userId == authentication.principal")
    @LogDetails
    @PostMapping("/risk-accessment")
    public ResponseEntity<?> submitRiskAssessment(@RequestBody RiskAssessmentDTO riskDTO,
                                                  HttpServletRequest request) {
        // TODO: waiting for definition
        return ResponseEntity.ok().build();
    }

}