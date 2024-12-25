package com.github.sneha.springboot_mysql_custom_queries.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Context class for managing JPA Criteria API objects related to Employee queries.
 * Encapsulates the CriteriaBuilder, CriteriaQuery, and Root objects required
 * for constructing and executing dynamic queries.
 *
 * @author sneharavikumartl
 */
public class EmployeeCriteriaContext {

    /**
     * CriteriaBuilder instance used for constructing criteria queries, expressions, and predicates.
     */
    public final CriteriaBuilder cb;

    /**
     * CriteriaQuery instance representing the query definition for Employee entities.
     */
    public final CriteriaQuery<Employee> cq;

    /**
     * Root instance representing the Employee entity in the criteria query.
     */
    public final Root<Employee> root;

    /**
     * Constructor to initialize the Criteria API context for Employee queries.
     *
     * @param cb   the CriteriaBuilder for building query components
     * @param cq   the CriteriaQuery representing the query for Employee entities
     * @param root the Root representing the Employee entity in the query
     */
    public EmployeeCriteriaContext(CriteriaBuilder cb, CriteriaQuery<Employee> cq, Root<Employee> root) {
        this.cb = cb;
        this.cq = cq;
        this.root = root;
    }
}
