package com.vti.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vti.entity.Department;

@Repository
public interface IDepartmentRepository extends JpaRepository<Department, Integer> {

    @Modifying
    @Query("DELETE FROM Department d WHERE d.name = :nameParameter")
    void deleteByName(@Param("nameParameter") String name);

    @Query("SELECT d FROM Department d WHERE d.name = :nameParameter")
    List<Department> getDepartmentByName(@Param("nameParameter") String name);

    List<Department> findByNameContains(String keyword);
    
    Optional<Department> findByName(String name);
    
    boolean existsByName(String name);
}
