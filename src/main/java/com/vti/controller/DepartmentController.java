package com.vti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.dto.DepartmentDto;
import com.vti.service.IDepartmentService;

@RestController
@RequestMapping(value = "api/v1/departments")
public class DepartmentController {

	@Autowired
	private IDepartmentService service;

	@GetMapping()
	public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
		List<DepartmentDto> departments = service.getAllDepartments();
		return ResponseEntity.ok(departments);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DepartmentDto> getDepartmentByID(@PathVariable(name = "id") int id) {
		DepartmentDto department = service.getDepartmentByID(id);
		return ResponseEntity.ok(department);
	}

	@PostMapping()
	public ResponseEntity<DepartmentDto> createDepartment(@RequestParam String name) {
		DepartmentDto createdDepartment = service.createDepartment(name);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable(name = "id") int id,
			@RequestParam String name) {
		DepartmentDto updatedDepartment = service.updateDepartment(id, name);
		return ResponseEntity.ok(updatedDepartment);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable(name = "id") int id) {
		service.deleteDepartment(id);
		return ResponseEntity.ok("Xóa department thành công!");
	}

	@GetMapping(value = "/search")
	public ResponseEntity<List<DepartmentDto>> getDepartmentsByNameContains(@RequestParam String keyword) {
		List<DepartmentDto> departments = service.getDepartmentsByNameContains(keyword);
		return ResponseEntity.ok(departments);
	}

	// Thêm các endpoint mới
	@DeleteMapping(value = "/name/{name}")
	public ResponseEntity<String> deleteDepartmentByName(@PathVariable(name = "name") String name) {
		service.deleteDepartmentByName(name);
		return ResponseEntity.ok("Xóa department theo tên thành công!");
	}

	@GetMapping(value = "/name/{name}")
	public ResponseEntity<List<DepartmentDto>> getDepartmentsByExactName(@PathVariable(name = "name") String name) {
		List<DepartmentDto> departments = service.getDepartmentsByName(name);
		return ResponseEntity.ok(departments);
	}
}
