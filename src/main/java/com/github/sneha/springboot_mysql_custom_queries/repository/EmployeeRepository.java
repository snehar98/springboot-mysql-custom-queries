package com.github.sneha.springboot_mysql_custom_queries.repository;

import com.github.sneha.springboot_mysql_custom_queries.model.Employee;
import com.github.sneha.springboot_mysql_custom_queries.utils.EmployeeQueryProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for interacting with the "users" table in the database.
 * It extends JpaRepository, providing basic CRUD operations and custom query methods.
 *
 * @author sneharavikumartl
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    /**
     * Retrieves the names of all employees.
     * Uses a custom query defined in the EmployeeQueryProvider class.
     *
     * @return a list of employee names
     */
    @Query(EmployeeQueryProvider.FIND_ALL_EMPLOYEES)
    List<String> findAllEmployeeNames();

    /**
     * Retrieves employee names from a specific department.
     * Uses a custom query defined in the EmployeeQueryProvider class.
     *
     * @param department the department to filter by
     * @return a list of employee names in the given department
     */
    @Query(EmployeeQueryProvider.FIND_EMPLOYEES_BY_DEPARTMENT)
    List<String> getEmployeeFromDept(@Param("department") String department);
}
