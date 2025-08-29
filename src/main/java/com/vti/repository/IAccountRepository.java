package com.vti.repository;

import com.vti.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Integer> {
    
    Optional<Account> findByEmail(String email);
    
    Optional<Account> findByUsername(String username);
    
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
    
    @Query("SELECT a FROM Account a WHERE a.departmentId = :departmentId")
    List<Account> findByDepartmentId(@Param("departmentId") short departmentId);
    
    @Query("SELECT a FROM Account a WHERE a.positionId = :positionId")
    List<Account> findByPositionId(@Param("positionId") short positionId);
}
