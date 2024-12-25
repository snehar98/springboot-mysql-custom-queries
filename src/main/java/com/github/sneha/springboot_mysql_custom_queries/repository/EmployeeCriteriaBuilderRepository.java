package com.github.sneha.springboot_mysql_custom_queries.repository;

import com.github.sneha.springboot_mysql_custom_queries.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for filtering Employee entities using custom criteria.
 * Defines the contract for filtering employees based on various parameters like
 * department and salary range.
 *
 * @author sneharavikumartl
 */
@Repository
public interface EmployeeCriteriaBuilderRepository {

    /**
     * Filters employees based on department, minimum salary, and maximum salary.
     *
     * @param department the department to filter by (optional)
     * @param minSalary the minimum salary to filter by (optional)
     * @param maxSalary the maximum salary to filter by (optional)
     * @return a list of Employee entities that match the specified criteria
     */
    List<Employee> filterEmployee(String department, Double minSalary, Double maxSalary);
}
