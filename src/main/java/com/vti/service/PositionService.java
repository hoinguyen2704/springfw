package com.vti.service;

import com.vti.dto.PositionDto;
import com.vti.entity.Position;
import com.vti.repository.IPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService implements IPositionService {

    @Autowired
    private IPositionRepository positionRepository;

    @Override
    public List<PositionDto> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PositionDto getPositionById(int id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Position không tồn tại với ID: " + id));
        return convertToDto(position);
    }

    @Override
    public PositionDto getPositionByName(String name) {
        Position position = positionRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Position không tồn tại với tên: " + name));
        return convertToDto(position);
    }

    @Override
    public PositionDto createPosition(String name) {
        if (positionRepository.existsByName(name)) {
            throw new RuntimeException("Position đã tồn tại với tên: " + name);
        }

        Position position = new Position();
        position.setName(name);

        Position savedPosition = positionRepository.save(position);
        return convertToDto(savedPosition);
    }

    @Override
    public PositionDto updatePosition(int id, String name) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Position không tồn tại với ID: " + id));

        if (!name.equals(position.getName()) && positionRepository.existsByName(name)) {
            throw new RuntimeException("Position đã tồn tại với tên: " + name);
        }

        position.setName(name);

        Position updatedPosition = positionRepository.save(position);
        return convertToDto(updatedPosition);
    }

    @Override
    public void deletePosition(int id) {
        if (!positionRepository.existsById(id)) {
            throw new RuntimeException("Position không tồn tại với ID: " + id);
        }
        positionRepository.deleteById(id);
    }

    @Override
    public boolean isPositionExistsByName(String name) {
        return positionRepository.existsByName(name);
    }

    private PositionDto convertToDto(Position position) {
        PositionDto dto = new PositionDto();
        dto.setId(position.getId());
        dto.setName(position.getName());
        return dto;
    }
}
