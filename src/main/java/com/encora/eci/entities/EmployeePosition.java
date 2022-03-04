package com.encora.eci.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
@Table(name = "employee_position")
public class EmployeePosition {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(unique = true)
    @Size(min = 2, max = 20, message = "Position name length must be between 2 and 100 characters.")
    private String positionName;

    @JsonIgnore
    @OneToMany(mappedBy = "employeePosition", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Employee> employees;

    public EmployeePosition(String positionName) {
        this.positionName = positionName;
    }
}
