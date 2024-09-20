package com.mydata.ems.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydata.ems.dto.EmployeeDto;
import com.mydata.ems.exception.ResourceNotFoundException;
import com.mydata.ems.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private EmployeeService es;

	public EmployeeController(EmployeeService es) {
		super();
		this.es = es;
	}
	
	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto)
	{
		EmployeeDto savedEmp=es.createEmployee(employeeDto);
		return new ResponseEntity<>(savedEmp,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<EmployeeDto>> getAllEmployees()
	{
		List<EmployeeDto> allEmp=es.getAllEmployees();
		return ResponseEntity.ok(allEmp);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<EmployeeDto> getEmployeeByID(@PathVariable Long id) throws ResourceNotFoundException
	{
		EmployeeDto empdt=es.getEmployeeById(id);
		return ResponseEntity.ok(empdt);
	}
	
	//build delete employee rest api
	@DeleteMapping("{id}")
	public ResponseEntity<String>deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException{
		es.deleteEmployee(id);
		return ResponseEntity.ok("Employee delete successfully");
	}
	
	@PutMapping("{id}")
	public ResponseEntity<EmployeeDto>updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto ed) throws ResourceNotFoundException{
		EmployeeDto empDto = es.updateEmployee(id, ed);
		return ResponseEntity.ok(empDto);
	}
	
}

