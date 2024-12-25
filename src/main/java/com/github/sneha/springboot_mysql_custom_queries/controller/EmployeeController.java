package com.github.sneha.springboot_mysql_custom_queries.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sneha.springboot_mysql_custom_queries.model.Employee;
import com.github.sneha.springboot_mysql_custom_queries.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

/**
 * Controller for managing employee-related operations.
 * Provides endpoints to create, retrieve, update, and filter employee information.
 *
 * @author sneharavikumartl
 */
@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Operation(summary = "Create a new employee", description = "Creates a new employee with auto-generated employeeId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/addEmployee")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @Operation(summary = "Get an employee by ID", description = "Retrieves an employee by their unique employeeId. Cached for performance.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/getEmployee/{employeeId}")
    public Employee getEmployee(@PathVariable String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    @Operation(summary = "Update an existing employee", description = "Updates an employee's details by their employeeId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully updated"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/updateEmployee/{employeeId}")
    public Employee updateEmployee(@PathVariable String employeeId, @Valid @RequestBody Employee employee) {
        log.info("Received Employee: {}", employee);
        return employeeService.updateEmployee(employeeId, employee);
    }

    @Operation(summary = "Retrieve all employee names", description = "Fetches the names of all employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee names retrieved successfully")
    })
    @GetMapping("/getAllEmployees")
    public List<String> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "Retrieve all employee details", description = "Fetches the complete details of all employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee details retrieved successfully")
    })
    @GetMapping("/getAllEmployeeDetails")
    public List<Employee> getAllEmployeeDetails() {
        return employeeService.getAllEmployeeDetails();
    }

    @Operation(summary = "Get total employee count", description = "Retrieves the total number of employees.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total employee count retrieved successfully")
    })
    @GetMapping("/getTotalEmployeeCount")
    public long getTotalEmployeeCount() {
        return employeeService.getTotalEmployeeCount();
    }

    @Operation(summary = "Get employees by department", description = "Fetches the names of employees in a specific department.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Department not found")
    })
    @GetMapping("/getEmployeesFromDept/{department}")
    public List<String> getEmployeesFromDept(@PathVariable String department) {
        return employeeService.getEmployeeFromDept(department);
    }

    @Operation(summary = "Filter employees", description = "Filters employees based on department and salary range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees filtered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid filter parameters")
    })
    @GetMapping("/filterEmployees")
    public List<Employee> filterEmployee(@RequestParam(required = false, defaultValue = "") String department,
                                         @RequestParam(required = false, defaultValue = "0") double minSalary,
                                         @RequestParam(required = false, defaultValue = "1.7976931348623157E308") double maxSalary) {
        return employeeService.filterEmployee(department, minSalary, maxSalary);
    }
}
