package com.encora.eci.repositories;

import com.encora.eci.entities.Employee;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Deprecated
public class EmployeeRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    public Employee findById(long employeeNumber) {
        TypedQuery<Employee> query = em.createQuery("select e from Employee e where e.employeeNumber = :employeeNumber", Employee.class);

        query.setParameter("employeeNumber", employeeNumber);

        return query.getSingleResult();
    }

    public List<Employee> searchEmployees(String firstName, String lastName, String position) {
        return em.createQuery(
                "select e from Employee e join e.EmployeePosition ep where e.firstName like :firstName and e.lastName like :lastName and ep.positionName like :position",
                Employee.class)
                .setParameter("firstName", firstName != null ? firstName : "")
                .setParameter("lastName", lastName != null ? lastName : "")
                .setParameter("position", position != null ? position : "")
                .getResultList();
    }
}
