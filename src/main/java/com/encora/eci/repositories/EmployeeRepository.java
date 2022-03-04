package com.encora.eci.repositories;

import com.encora.eci.entities.Employee;
import com.encora.eci.entities.EmployeePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByIdAndInactiveIs(long id, boolean inactive);

    @Query(value = "SELECT count(e) FROM Employee e where e.companyEmail like concat('%', :companyEmailUserName, '%')")
    long getCompanyEmailCount(String companyEmailUserName);

    @Query(value =
            "SELECT " +
                    "   e " +
                    "FROM " +
                    "   Employee e " +
                    "WHERE e.inactive IS false " +
                    "AND MAKEDATE('2020', DAYOFYEAR(e.birthday)) " +
                    "   BETWEEN " +
                    "       MAKEDATE('2020', DAYOFYEAR(:date)) " +
                    "   AND" +
                    "       MAKEDATE('2020', DAYOFYEAR(:date) + 7)")
    List<Employee> findActiveByBirthdayNextWeek(LocalDate date);

    List<Employee> findByFirstNameLikeAndLastNameLikeAndInactiveIs(String firstName, String lastName, boolean inactive);

    List<Employee> findByFirstNameLikeAndLastNameLikeAndEmployeePositionIsAndInactiveIs(String firstName, String lastName, EmployeePosition employeePosition, boolean inactive);

    List<Employee> findByFirstNameLikeAndLastNameLike(String firstName, String lastName);

    List<Employee> findByFirstNameLikeAndLastNameLikeAndEmployeePositionIs(String firstName, String lastName, EmployeePosition employeePosition);

}
