package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Employee {
    @Id
    @NotBlank
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    @ElementCollection
    private List<String> phoneNumbers;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate DOJ;
    private double salary;

    public void setDOJ(String date) {
        this.DOJ = LocalDate.parse(date);
    }
}
