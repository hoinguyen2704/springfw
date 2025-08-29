package com.vti.service;

import com.vti.dto.AccountDto;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdating;

import java.util.List;

public interface IAccountService {
    
    List<AccountDto> getAllAccounts();
    
    AccountDto getAccountById(int id);
    
    AccountDto getAccountByEmail(String email);
    
    AccountDto getAccountByUsername(String username);
    
    AccountDto createAccount(AccountFormForCreating form);
    
    AccountDto updateAccount(int id, AccountFormForUpdating form);
    
    void deleteAccount(int id);
    
    List<AccountDto> getAccountsByDepartment(short departmentId);
    
    List<AccountDto> getAccountsByPosition(short positionId);
    
    boolean isAccountExistsByEmail(String email);
    
    boolean isAccountExistsByUsername(String username);
}
