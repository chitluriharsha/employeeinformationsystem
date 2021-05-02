package com.poc.employeeinformatiosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.employeeinformatiosystem.domain.EmployeeInformation;

@Repository
public interface EmployeeInformationRepository extends JpaRepository<EmployeeInformation, String>{

}
