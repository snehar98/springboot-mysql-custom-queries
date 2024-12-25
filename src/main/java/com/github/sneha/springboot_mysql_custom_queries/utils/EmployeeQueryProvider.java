package com.github.sneha.springboot_mysql_custom_queries.utils;

public class EmployeeQueryProvider {

    // JPQL Query to find all employees
    public static final String FIND_ALL_EMPLOYEES = "SELECT e.employeeName from Employee e";

    // JPQL Query to find employees by department
    public static final String FIND_EMPLOYEES_BY_DEPARTMENT =
            "SELECT e.employeeName FROM Employee e WHERE e.dept = :department";

    // JPQL Query to find employees with a salary filter within a department
    // This query selects all employees from the Employee entity who belong to a specific department
    // and have a salary within the specified range.
    public static final String FIND_EMPLOYEES_WITH_FILTER =
            "SELECT e FROM Employee e WHERE e.dept=:department and e.salary >= :minSalary and e.salary <= :maxSalary";
}