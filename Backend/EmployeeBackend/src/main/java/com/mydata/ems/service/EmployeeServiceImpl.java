package com.mydata.ems.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mydata.ems.dto.EmployeeDto;
import com.mydata.ems.entity.Employee;
import com.mydata.ems.exception.ResourceNotFoundException;
import com.mydata.ems.mapper.EmployeeMapper;
import com.mydata.ems.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{


	private EmployeeRepository employeeRepository;	
	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}
	
	@Override
	public EmployeeDto createEmployee(EmployeeDto employeeDto) {
		
		Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
		
		Employee savedEmployee = employeeRepository.save(employee);
		
		EmployeeDto edto = EmployeeMapper.mapToEmployeeDto(savedEmployee);
		
		return edto; 
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {
		
		
		List<Employee> allEmp = employeeRepository.findAll();
		return allEmp.stream()
				.map((Employees)->EmployeeMapper.mapToEmployeeDto(Employees))
				.collect(Collectors.toList());
		
	}

	@Override
	public EmployeeDto getEmployeeById(Long empId) throws ResourceNotFoundException {
		Employee emp = employeeRepository.findById(empId).orElseThrow(()->new ResourceNotFoundException("employee not exist eith given id : "+empId));
		return EmployeeMapper.mapToEmployeeDto(emp);
	}
	
	@Override
	public void deleteEmployee(Long id) throws ResourceNotFoundException{
		@SuppressWarnings("unused")
		Employee e = employeeRepository.findById(id)
				.orElseThrow(
				()->new ResourceNotFoundException("employee is not exist with given id:"+id));
		employeeRepository.deleteById(id);
	}

	@Override
	public EmployeeDto updateEmployee(Long id, EmployeeDto ed) throws ResourceNotFoundException {
		Employee e = employeeRepository.findById(id)
				.orElseThrow(
				()->new ResourceNotFoundException("employee is not exist with given id:"+id));
		
		e.setFirstName(ed.getFirstName());
		e.setLastName(ed.getLastName());
		e.setEmail(ed.getEmail());
		
		Employee updatedEmp = employeeRepository.save(e);
		return EmployeeMapper.mapToEmployeeDto(updatedEmp);
	}
	
}

