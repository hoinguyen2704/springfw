package com.vti.controller;

import com.vti.dto.AccountDto;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdating;
import com.vti.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable int id) {
        AccountDto account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AccountDto> getAccountByEmail(@PathVariable String email) {
        AccountDto account = accountService.getAccountByEmail(email);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AccountDto> getAccountByUsername(@PathVariable String username) {
        AccountDto account = accountService.getAccountByUsername(username);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<AccountDto>> getAccountsByDepartment(@PathVariable short departmentId) {
        List<AccountDto> accounts = accountService.getAccountsByDepartment(departmentId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<AccountDto>> getAccountsByPosition(@PathVariable short positionId) {
        List<AccountDto> accounts = accountService.getAccountsByPosition(positionId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody AccountFormForCreating form) {
        AccountDto createdAccount = accountService.createAccount(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable int id,
            @Valid @RequestBody AccountFormForUpdating form) {
        AccountDto updatedAccount = accountService.updateAccount(id, form);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable int id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Xóa account thành công!");
    }

    @GetMapping("/check-email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = accountService.isAccountExistsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-username/{username}")
    public ResponseEntity<Boolean> checkUsernameExists(@PathVariable String username) {
        boolean exists = accountService.isAccountExistsByUsername(username);
        return ResponseEntity.ok(exists);
    }
}
