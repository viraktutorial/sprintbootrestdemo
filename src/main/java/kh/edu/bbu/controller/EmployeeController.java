package kh.edu.bbu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kh.edu.bbu.repository.EmployeeRepository;
import kh.edu.bbu.exception.ResourceNotFoundException;
import kh.edu.bbu.model.Employee;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//read all employees
	@GetMapping
	public List<Employee> getEmployees(){
		return employeeRepository.findAll();
	}
	
	//read specific employee 
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee id is not exist: " + id));
		return ResponseEntity.ok(employee);
	}
	
	//create an employee(POST)
	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
	}
	
	//update an employee(PUT)
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee id is not exist: " + id));
		
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailID(employeeDetails.getEmailID());
		Employee updatedEmployee=employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee); 
		
	}
	
	//delete an employee(DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Employee employee=employeeRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee id is not exist: " + id));
		employeeRepository.delete(employee);
		Map<String, Boolean> response=new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
		
	}
	
}
