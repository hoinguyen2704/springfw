package com.vti.repository;

import com.vti.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPositionRepository extends JpaRepository<Position, Integer> {
    
    Optional<Position> findByName(String name);
    
    boolean existsByName(String name);
}
