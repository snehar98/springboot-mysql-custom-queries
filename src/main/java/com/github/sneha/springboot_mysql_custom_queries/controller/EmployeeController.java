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


/**
 * Controller for managing employee-related operations.
 * Provides endpoints to create, retrieve, and update employee information.
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

    /**
     * Create a new employee.
     *
     * @param employee the employee object to be created
     * @return the created employee
     */
    @Operation(summary = "Create a new employee", description = "Creates a new employee with auto-generated employeeId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/addEmployee")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    /**
     * Retrieve a employee by employeeId.
     *
     * @param employeeId the ID of the employee to retrieve
     * @return the employee if found
     */
    @Operation(summary = "Get a employee by ID", description = "Retrieves a employee by their unique employeeId. Cached for performance.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/getEmployee/{employeeId}")
    public Employee getEmployee(@PathVariable String employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    /**
     * Update an existing employee.
     *
     * @param employeeId the ID of the employee to update
     * @param employee   the updated employee object
     * @return the updated employee
     */
    @Operation(summary = "Update an existing employee", description = "Updates a employee's details by their employeeId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully updated"),
            @ApiResponse(responseCode = "404", description = "Employee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })

    @PostMapping("/updateEmployee/{employeeId}")
    public Employee updateEmployee(@PathVariable String employeeId, @Valid @RequestBody Employee employee) {
        //log.info("Received raw JSON: {}", rawJson);
        log.info("Received Employee: {}", employee);
        return employeeService.updateEmployee(employeeId, employee);
    }

}
