package com.example.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Branch;
import com.example.demo.model.Employee;
import com.example.demo.model.EmployeeRole;
import com.example.demo.model.UserCustomer;
import com.example.demo.model.Vehicle;
import com.example.demo.model.Vehicle.VehicleType;
import com.example.demo.repository.BranchRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserCustomerRepository;
import com.example.demo.repository.VehicleRepository;

@Service
public class DBInit implements CommandLineRunner {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private BranchRepository branchRepository;
	@Autowired
	private UserCustomerRepository userCustomerRepository;
	@Autowired
	private VehicleRepository vehicleRepository;
	@Autowired
	private RolesRepository rolesRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// Delete all
		
        this.employeeRepository.deleteAll();
        this.userCustomerRepository.deleteAll();
        this.vehicleRepository.deleteAll();
        this.branchRepository.deleteAll();
        this.rolesRepository.deleteAll();

        //create and save branches
        Branch Haifa = Branch.builder().city("Haifa").address("Haazmaut 5").build();
        Branch Headquarters = Branch.builder().city("Headquarters").build();
        branchRepository.saveAll(Arrays.asList(Haifa,Headquarters));

        EmployeeRole AdminRole = new EmployeeRole("ADMIN");
        EmployeeRole ManagerRole = new EmployeeRole("MANAGER");
        EmployeeRole EmployeeRole = new EmployeeRole("EMPLOYEE");
        rolesRepository.saveAll(Arrays.asList(AdminRole,ManagerRole,EmployeeRole));

        // Create and save employees
        Employee admin = Employee.builder()
				.address("Harishonim 1, Rishon Lezion")
				.age(33)
				.branch(Headquarters)
				.email("admin@g.com")
				.id("373654633")
				.name("Elon Musk")
				.password(passwordEncoder.encode("12345678"))
				.phoneNumber("054-2234567")
				.role(new EmployeeRole("ADMIN"))
				.username("admin")
				.build();
        Employee haifaEmloyee  = Employee.builder()
				.address("Bat Galim 16, Haifa")
				.age(22)
				.branch(Haifa)
				.email("yosib@g.com")
				.id("373222633")
				.name("Yosi Biton")
				.password(passwordEncoder.encode("12345678"))
				.phoneNumber("052-2239967")
				.role(new EmployeeRole("EMPLOYEE"))
				.username("yosib")
				.build();
        Employee haifaManager = Employee.builder()
				.address("Haneviim 55, Haifa")
				.age(29)
				.branch(Haifa)
				.email("yk20@g.com")
				.id("203020202")
				.name("Yaniv Katan")
				.password(passwordEncoder.encode("12345678"))
				.phoneNumber("052-2020202")
				.role(new EmployeeRole("MANAGER"))
				.username("yk20")
				.build();
        employeeRepository.saveAll(Arrays.asList(admin,haifaEmloyee,haifaManager));

        
        // Create and save customers
        UserCustomer customer1 = UserCustomer.builder()
        		.address("Aba 2 ,Yafo")
        		.age(19)
        		.email("w@f.com")
        		.id("745833256")
        		.name("John Cena")
        		.phoneNumber("050-8876543")
        		.build();
        userCustomerRepository.saveAll(Arrays.asList(customer1));

        // Create and save vehicles
        Vehicle honda = Vehicle.builder()
        						.licensePlateId("11-111-11")
        						.location(Haifa)
        						.model("Honda Civic")
        						.passengers(5)
        						.pricePerDay(80)
        						.year(2018)
        						.vehicleType(VehicleType.Standart)
        						.build();
        vehicleRepository.saveAll(Arrays.asList(honda));

	}

}
