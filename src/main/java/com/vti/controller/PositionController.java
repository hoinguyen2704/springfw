package com.vti.controller;

import com.vti.dto.PositionDto;
import com.vti.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @GetMapping
    public ResponseEntity<List<PositionDto>> getAllPositions() {
        List<PositionDto> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PositionDto> getPositionById(@PathVariable int id) {
        PositionDto position = positionService.getPositionById(id);
        return ResponseEntity.ok(position);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PositionDto> getPositionByName(@PathVariable String name) {
        PositionDto position = positionService.getPositionByName(name);
        return ResponseEntity.ok(position);
    }

    @PostMapping
    public ResponseEntity<PositionDto> createPosition(@RequestParam String name) {
        PositionDto createdPosition = positionService.createPosition(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPosition);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PositionDto> updatePosition(@PathVariable int id, @RequestParam String name) {
        PositionDto updatedPosition = positionService.updatePosition(id, name);
        return ResponseEntity.ok(updatedPosition);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePosition(@PathVariable int id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok("Xóa position thành công!");
    }

    @GetMapping("/check-name/{name}")
    public ResponseEntity<Boolean> checkNameExists(@PathVariable String name) {
        boolean exists = positionService.isPositionExistsByName(name);
        return ResponseEntity.ok(exists);
    }
}
