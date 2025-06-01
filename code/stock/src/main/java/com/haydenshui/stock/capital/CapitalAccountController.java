package com.haydenshui.stock.capital;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.haydenshui.stock.lib.annotation.LogDetails;
import com.haydenshui.stock.lib.dto.capital.CapitalAccountDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/capital")
public class CapitalAccountController{

    private final CapitalAccountService capitalAccountService;

    public CapitalAccountController(CapitalAccountService capitalAccountService) {
        this.capitalAccountService = capitalAccountService;
    }

    @LogDetails
    @PutMapping("/new")
    public ResponseEntity<?> createAccount(@RequestBody CapitalAccountDTO dto) {
        return ResponseEntity.ok(capitalAccountService.createAccount(dto));
    }

    @LogDetails
    @GetMapping("/profile")
    public ResponseEntity<?> getAccountById(@RequestParam Long id, @RequestParam String type) {
        return ResponseEntity.ok(capitalAccountService.getAccountById(id, type));
    }

    @LogDetails
    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody CapitalAccountDTO dto) {
        return ResponseEntity.ok(capitalAccountService.updateAccount(dto));
    }
    
}