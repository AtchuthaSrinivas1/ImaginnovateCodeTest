package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.entity.EmployeeTaxDetails;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addEmployee(Employee employee) throws Exception{
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            throw new Exception("Error occurred while saving employee");
        }
    }

    public List<EmployeeTaxDetails> getEmployeesDetWithTaxDeductions() {
        List<EmployeeTaxDetails> taxDetailsList = new ArrayList<>();
        List<Employee> employeeList = employeeRepository.findAll();
        for (Employee employee : employeeList) {
            double yearlySalary = calculateYearlySalary(employee);
            double taxAmount = calculateTaxAmount(yearlySalary);
            double cessAmount = calculateCessAmount(yearlySalary);
            EmployeeTaxDetails taxDetails = new EmployeeTaxDetails(employee.getEmployeeId(), employee.getFirstName(),
                    employee.getLastName(), yearlySalary, taxAmount, cessAmount);
            taxDetailsList.add(taxDetails);
        }
        return taxDetailsList;
    }

    private double calculateYearlySalary(Employee employee) {
        long totalDaysWorked = ChronoUnit.DAYS.between(employee.getDOJ(), LocalDate.now()) + 1;
        return employee.getSalary() * totalDaysWorked / 30.0;
    }

    private double calculateTaxAmount(double yearlySalary) {
        if (yearlySalary <= 250000) {
            return 0;
        } else if (yearlySalary <= 500000) {
            return (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            return 12500 + (yearlySalary - 500000) * 0.1;
        } else {
            return 62500 + (yearlySalary - 1000000) * 0.2;
        }
    }

    private double calculateCessAmount(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }
}
