package com.vti.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vti.dto.DepartmentDto;
import com.vti.entity.Department;
import com.vti.repository.IDepartmentRepository;

@Service
public class DepartmentService implements IDepartmentService {

	@Autowired
	private IDepartmentRepository repository;

	@Override
	public List<DepartmentDto> getAllDepartments() {
		return repository.findAll().stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public DepartmentDto getDepartmentByID(int id) {
		Department department = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department không tồn tại với ID: " + id));
		return convertToDto(department);
	}

	@Override
	public DepartmentDto createDepartment(String name) {
		if (repository.existsByName(name)) {
			throw new RuntimeException("Department đã tồn tại với tên: " + name);
		}

		Department department = new Department();
		department.setName(name);

		Department savedDepartment = repository.save(department);
		return convertToDto(savedDepartment);
	}

	@Override
	public DepartmentDto updateDepartment(int id, String name) {
		Department department = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Department không tồn tại với ID: " + id));

		if (!name.equals(department.getName()) && repository.existsByName(name)) {
			throw new RuntimeException("Department đã tồn tại với tên: " + name);
		}

		department.setName(name);

		Department updatedDepartment = repository.save(department);
		return convertToDto(updatedDepartment);
	}

	@Override
	public void deleteDepartment(int id) {
		if (!repository.existsById(id)) {
			throw new RuntimeException("Department không tồn tại với ID: " + id);
		}
		repository.deleteById(id);
	}

	@Override
	public List<DepartmentDto> getDepartmentsByNameContains(String keyword) {
		return repository.findByNameContains(keyword).stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	// Thêm các method mới
	@Transactional
	@Override
	public void deleteDepartmentByName(String name) {
		repository.deleteByName(name);
	}

	@Override
	public List<DepartmentDto> getDepartmentsByName(String name) {
		return repository.getDepartmentByName(name).stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public boolean isDepartmentExistsByName(String name) {
		return repository.existsByName(name);
	}

	private DepartmentDto convertToDto(Department department) {
		DepartmentDto dto = new DepartmentDto();
		dto.setId(department.getId());
		dto.setName(department.getName());
		return dto;
	}
}
