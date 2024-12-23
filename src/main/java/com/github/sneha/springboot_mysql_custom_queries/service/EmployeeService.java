package com.github.sneha.springboot_mysql_custom_queries.service;

import com.github.sneha.springboot_mysql_custom_queries.exception.EmployeeDetailsNotFoundException;
import com.github.sneha.springboot_mysql_custom_queries.model.Employee;
import com.github.sneha.springboot_mysql_custom_queries.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Employee operations such as retrieving, creating,
 * updating, and deleting Employees. It also integrates caching with Redis for better performance.
 *
 * @author sneharavikumartl
 */
@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Retrieves a Employee by their EmployeeId from the cache or the database.
     * The result is cached with the EmployeeId as the key.
     * If the Employee is not found in the cache, it fetches from the database.
     *
     * @param EmployeeId the ID of the Employee to retrieve
     * @return the Employee with the given EmployeeId
     */

    public Employee getEmployeeById(@Valid String EmployeeId) {
        // If the Employee is not found in the cache, it will fetch from DB
        log.info("Fetching Employee with ID: {} from the database", EmployeeId);
        return employeeRepository.findById(EmployeeId).orElseThrow(() -> new EmployeeDetailsNotFoundException("Employee not found for the Id " + EmployeeId));
    }

    /**
     * Creates a new Employee and saves it to the database.
     * This method does not apply caching.
     *
     * @param Employee the Employee object to create
     * @return the created Employee
     */
    public Employee createEmployee(@Valid Employee Employee) {
        Employee.setEmployeeId(generateEmployeeId());  // Automatically generate EmployeeId
        return employeeRepository.save(Employee);  // Save the new Employee to the database
    }

    /**
     * Updates an existing Employee's information and applies cache operations.
     * The cache is updated with the new Employee data if Employee data is present in cache
     *
     * @param EmployeeId      the ID of the Employee to update
     * @param updatedEmployee the updated Employee data
     * @return the updated Employee
     */
    public Employee updateEmployee(String EmployeeId, @Valid Employee updatedEmployee) {
        Employee Employee = employeeRepository.findById(EmployeeId).orElseThrow(() -> new EmployeeDetailsNotFoundException("Employee not found for the Id - " + EmployeeId));
        Employee.setEmployeeName(updatedEmployee.getEmployeeName());
        Employee.setEmail(updatedEmployee.getEmail());
        Employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        Employee.setAddress(updatedEmployee.getAddress());

        return employeeRepository.save(Employee);
    }

    /**
     * Deletes a Employee by their EmployeeId and evicts the corresponding cache.
     *
     * @param EmployeeId the ID of the Employee to delete
     */
    public void deleteEmployee(String EmployeeId) {
        Employee Employee = employeeRepository.findById(EmployeeId).orElseThrow(() -> new EmployeeDetailsNotFoundException("Employee not found for the Id - " + EmployeeId));
        employeeRepository.delete(Employee);  // Delete the Employee from the database
    }

    /**
     * Utility method to generate a unique EmployeeId (example using UUID).
     *
     * @return a unique EmployeeId
     */
    private String generateEmployeeId() {
        return java.util.UUID.randomUUID().toString(); // Generates a random UUID as the EmployeeId
    }

}
