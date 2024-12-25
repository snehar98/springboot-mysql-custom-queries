package com.github.sneha.springboot_mysql_custom_queries.repository;

import com.github.sneha.springboot_mysql_custom_queries.model.Employee;
import com.github.sneha.springboot_mysql_custom_queries.model.EmployeeCriteriaContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the EmployeeCriteriaBuilderRepository interface.
 * Provides methods to filter Employee entities dynamically using JPA Criteria API.
 *
 * @author sneharavikumartl
 */
@Repository
public class EmployeeCriteriaBuilderImpl implements EmployeeCriteriaBuilderRepository {

    /**
     * EntityManager instance to interact with the persistence context and execute queries.
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Initializes and returns an EmployeeCriteriaContext object to encapsulate
     * CriteriaBuilder, CriteriaQuery, and Root for Employee entities.
     *
     * @return a new instance of EmployeeCriteriaContext
     */
    private EmployeeCriteriaContext initializeEmployeeCriteriaContext() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);
        return new EmployeeCriteriaContext(cb, cq, root);
    }

    /**
     * Filters employees based on department, minimum salary, and maximum salary using
     * dynamic query construction with JPA Criteria API.
     *
     * @param department the department to filter by (optional)
     * @param minSalary  the minimum salary to filter by (optional)
     * @param maxSalary  the maximum salary to filter by (optional)
     * @return a list of Employee entities matching the specified filter criteria
     */
    public List<Employee> filterEmployee(String department, Double minSalary, Double maxSalary) {
        // Initialize criteria context for Employee
        EmployeeCriteriaContext employeeCriteriaContext = initializeEmployeeCriteriaContext();
        CriteriaBuilder cb = employeeCriteriaContext.cb;
        CriteriaQuery<Employee> cq = employeeCriteriaContext.cq;
        Root<Employee> employee = employeeCriteriaContext.root;

        // Build predicates for dynamic filtering
        List<Predicate> predicates = new ArrayList<>();

        if (department != null && !department.isEmpty()) {
            predicates.add(cb.equal(employee.get("dept"), department));
        }
        if (minSalary != null) {
            predicates.add(cb.greaterThanOrEqualTo(employee.get("salary"), minSalary));
        }
        if (maxSalary != null) {
            predicates.add(cb.lessThanOrEqualTo(employee.get("salary"), maxSalary));
        }

        // Apply predicates to the query
        cq.where(predicates.toArray(new Predicate[0]));

        // Execute the query and return the results
        return entityManager.createQuery(cq).getResultList();
    }
}
