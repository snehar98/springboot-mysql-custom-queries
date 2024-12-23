package com.github.sneha.springboot_mysql_custom_queries.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a employee entity with relevant details such as employee name, email, phone number, and address.
 * This class is mapped to the "employees" table in the database.
 * It includes validation constraints to ensure data integrity.
 *
 * @author sneharavikumartl
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

    /**
     * The unique identifier for the employee.
     */
    @Id
    @Column(name = "employee_id")
    private String employeeId;

    /**
     * The name of the employee.
     * Cannot be blank and must be between 3 and 50 characters.
     */
    @Column(name = "employee_name", nullable = false)
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String employeeName;

    /**
     * The email address of the employee.
     * Cannot be blank and must follow a valid email format.
     */
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    /**
     * The phone number of the employee.
     * Must be exactly 10 digits.
     */
    @Column(name = "phone_number")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    /**
     * The address of the employee.
     */
    @Column(name = "address")
    private String address;

    /**
     * The salary of the employee.
     */
    @Column(name = "salary", nullable = false)
    private Double salary;
}
