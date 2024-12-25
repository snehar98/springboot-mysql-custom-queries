package com.github.sneha.springboot_mysql_custom_queries.service;

import com.github.sneha.springboot_mysql_custom_queries.exception.DepartmentNotFoundException;
import com.github.sneha.springboot_mysql_custom_queries.exception.EmployeeDetailsNotFoundException;
import com.github.sneha.springboot_mysql_custom_queries.model.Employee;
import com.github.sneha.springboot_mysql_custom_queries.repository.EmployeeCriteriaBuilderRepository;
import com.github.sneha.springboot_mysql_custom_queries.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    @Autowired
    private EmployeeCriteriaBuilderRepository employeeCriteriaBuilder;

    /**
     * Retrieves an Employee by their EmployeeId from the cache or the database.
     * The result is cached with the EmployeeId as the key.
     * If the Employee is not found in the cache, it fetches from the database.
     *
     * @param EmployeeId the ID of the Employee to retrieve
     * @return the Employee with the given EmployeeId
     */
    public Employee getEmployeeById(@Valid String EmployeeId) {
        log.info("Fetching Employee with ID: {} from the database", EmployeeId);
        return employeeRepository.findById(EmployeeId).orElseThrow(() ->
                new EmployeeDetailsNotFoundException("Employee not found for the Id " + EmployeeId));
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
     * The cache is updated with the new Employee data if Employee data is present in cache.
     *
     * @param EmployeeId      the ID of the Employee to update
     * @param updatedEmployee the updated Employee data
     * @return the updated Employee
     */
    public Employee updateEmployee(String EmployeeId, @Valid Employee updatedEmployee) {
        Employee Employee = employeeRepository.findById(EmployeeId).orElseThrow(() ->
                new EmployeeDetailsNotFoundException("Employee not found for the Id - " + EmployeeId));
        Employee.setEmployeeName(updatedEmployee.getEmployeeName());
        Employee.setEmail(updatedEmployee.getEmail());
        Employee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        Employee.setAddress(updatedEmployee.getAddress());

        return employeeRepository.save(Employee);
    }

    /**
     * Deletes an Employee by their EmployeeId and evicts the corresponding cache.
     *
     * @param EmployeeId the ID of the Employee to delete
     */
    public void deleteEmployee(String EmployeeId) {
        Employee Employee = employeeRepository.findById(EmployeeId).orElseThrow(() ->
                new EmployeeDetailsNotFoundException("Employee not found for the Id - " + EmployeeId));
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

    /**
     * Retrieves a list of all employees' names.
     *
     * @return a list of employee names
     */
    public List<String> getAllEmployees(){
        return employeeRepository.findAllEmployeeNames();
    }

    /**
     * Retrieves a list of all employees' details.
     *
     * @return a list of Employee objects containing all employee details
     */
    public List<Employee> getAllEmployeeDetails(){
        return employeeRepository.findAll();
    }

    /**
     * Retrieves the total count of employees.
     *
     * @return the total number of employees
     */
    public long getTotalEmployeeCount(){
        return employeeRepository.count();
    }

    /**
     * Retrieves a list of employee names from a specific department.
     * Throws an exception if the department is not found.
     *
     * @param department the department name
     * @return a list of employee names in the given department
     * @throws DepartmentNotFoundException if the department is not found
     */
    public List<String> getEmployeeFromDept(String department){
        List<String> employees = employeeRepository.getEmployeeFromDept(department);
        if(employees.isEmpty())
            throw new DepartmentNotFoundException("Entered Department is invalid - " + department);
        return employees;
    }

    /**
     * Filters employees based on optional query parameters: department, minSalary, and maxSalary.
     *
     * @param department the department name (can be null)
     * @param minSalary  the minimum salary (can be null)
     * @param maxSalary  the maximum salary (can be null)
     * @return a list of employees matching the filter criteria
     */
    public List<Employee> filterEmployee(String department, double minSalary, double maxSalary){
        return employeeCriteriaBuilder.filterEmployee(department, minSalary, maxSalary);
    }
}
