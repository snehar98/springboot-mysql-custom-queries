package com.github.sneha.springboot_mysql_custom_queries.controller;

import com.github.sneha.springboot_mysql_custom_queries.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles administrative operations such as Employee management
 * and system configuration. It uses Spring Security for authentication
 * based on employee name and password.
 *
 * @author sneharavikumartl
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Deletes a Employee by their EmployeeId.
     * This method interacts with the EmployeeService to delete a employee by their ID.
     *
     * @param employeeId the ID of the employee to be deleted
     */
    @DeleteMapping("/deleteEmployee/{employeeId}")
    @Operation(summary = "Delete Employee", description = "Deletes a Employee by their unique EmployeeId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public void deleteEmployee(@PathVariable String employeeId) {
        employeeService.deleteEmployee(employeeId);
    }
}
