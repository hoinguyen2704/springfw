package com.vti.service;

import java.util.List;

import com.vti.dto.DepartmentDto;

public interface IDepartmentService {

	public List<DepartmentDto> getAllDepartments();

	public DepartmentDto getDepartmentByID(int id);

	public DepartmentDto createDepartment(String name);

	public DepartmentDto updateDepartment(int id, String name);

	public void deleteDepartment(int id);

	public List<DepartmentDto> getDepartmentsByNameContains(String keyword);

	// Thêm các method mới
	public void deleteDepartmentByName(String name);

	public List<DepartmentDto> getDepartmentsByName(String name);
	
	public boolean isDepartmentExistsByName(String name);
}
