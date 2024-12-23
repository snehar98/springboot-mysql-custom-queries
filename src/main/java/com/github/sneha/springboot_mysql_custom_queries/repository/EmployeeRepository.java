package com.github.sneha.springboot_mysql_custom_queries.repository;

import com.github.sneha.springboot_mysql_custom_queries.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for interacting with the "users" table in the database.
 * It extends JpaRepository, providing CRUD operations and more.
 *
 * @author sneharavikumartl
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    // JpaRepository provides built-in methods for basic CRUD operations (e.g., save, findAll, findById, deleteById).
}
