package com.vti.service;

import com.vti.dto.PositionDto;

import java.util.List;

public interface IPositionService {
    
    List<PositionDto> getAllPositions();
    
    PositionDto getPositionById(int id);
    
    PositionDto getPositionByName(String name);
    
    PositionDto createPosition(String name);
    
    PositionDto updatePosition(int id, String name);
    
    void deletePosition(int id);
    
    boolean isPositionExistsByName(String name);
}
