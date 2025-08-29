package com.vti.service;

import com.vti.dto.AccountDto;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;
import com.vti.form.AccountFormForCreating;
import com.vti.form.AccountFormForUpdating;
import com.vti.repository.IAccountRepository;
import com.vti.repository.IDepartmentRepository;
import com.vti.repository.IPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private IDepartmentRepository departmentRepository;

    @Autowired
    private IPositionRepository positionRepository;

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getAccountById(int id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account không tồn tại với ID: " + id));
        return convertToDto(account);
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account không tồn tại với email: " + email));
        return convertToDto(account);
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account không tồn tại với username: " + username));
        return convertToDto(account);
    }

    @Override
    public AccountDto createAccount(AccountFormForCreating form) {
        // Kiểm tra email và username đã tồn tại chưa
        if (accountRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("Email đã tồn tại: " + form.getEmail());
        }
        if (accountRepository.existsByUsername(form.getUsername())) {
            throw new RuntimeException("Username đã tồn tại: " + form.getUsername());
        }

        // Kiểm tra department và position có tồn tại không
        if (!departmentRepository.existsById(form.getDepartmentId().intValue())) {
            throw new RuntimeException("Department không tồn tại với ID: " + form.getDepartmentId());
        }
        if (!positionRepository.existsById(form.getPositionId().intValue())) {
            throw new RuntimeException("Position không tồn tại với ID: " + form.getPositionId());
        }

        Account account = new Account();
        account.setEmail(form.getEmail());
        account.setUsername(form.getUsername());
        account.setFullName(form.getFullName());
        account.setDepartmentId(form.getDepartmentId());
        account.setPositionId(form.getPositionId());

        Account savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount);
    }

    @Override
    public AccountDto updateAccount(int id, AccountFormForUpdating form) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account không tồn tại với ID: " + id));

        // Kiểm tra email và username đã tồn tại chưa (trừ account hiện tại)
        if (!form.getEmail().equals(account.getEmail()) &&
                accountRepository.existsByEmail(form.getEmail())) {
            throw new RuntimeException("Email đã tồn tại: " + form.getEmail());
        }
        if (!form.getUsername().equals(account.getUsername()) &&
                accountRepository.existsByUsername(form.getUsername())) {
            throw new RuntimeException("Username đã tồn tại: " + form.getUsername());
        }

        // Kiểm tra department và position có tồn tại không
        if (!departmentRepository.existsById(form.getDepartmentId().intValue())) {
            throw new RuntimeException("Department không tồn tại với ID: " + form.getDepartmentId());
        }
        if (!positionRepository.existsById(form.getPositionId().intValue())) {
            throw new RuntimeException("Position không tồn tại với ID: " + form.getPositionId());
        }

        account.setEmail(form.getEmail());
        account.setUsername(form.getUsername());
        account.setFullName(form.getFullName());
        account.setDepartmentId(form.getDepartmentId());
        account.setPositionId(form.getPositionId());

        Account updatedAccount = accountRepository.save(account);
        return convertToDto(updatedAccount);
    }

    @Override
    public void deleteAccount(int id) {
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account không tồn tại với ID: " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    public List<AccountDto> getAccountsByDepartment(short departmentId) {
        return accountRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> getAccountsByPosition(short positionId) {
        return accountRepository.findByPositionId(positionId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountExistsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public boolean isAccountExistsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    private AccountDto convertToDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setAccountId(account.getAccountId());
        dto.setEmail(account.getEmail());
        dto.setUsername(account.getUsername());
        dto.setFullName(account.getFullName());
        dto.setDepartmentId(account.getDepartmentId());
        dto.setPositionId(account.getPositionId());
        dto.setCreateDate(account.getCreateDate());

        // Lấy tên department và position
        try {
            Department department = departmentRepository.findById((int) account.getDepartmentId()).orElse(null);
            if (department != null) {
                dto.setDepartmentName(department.getName());
            }

            Position position = positionRepository.findById((int) account.getPositionId()).orElse(null);
            if (position != null) {
                dto.setPositionName(position.getName());
            }
        } catch (Exception e) {
            // Log error nếu cần
        }

        return dto;
    }
}
