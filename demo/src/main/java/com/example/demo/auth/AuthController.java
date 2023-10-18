//package com.example.demo.auth;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AuthController {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @GetMapping("/home")
//    public String getHome() {
//        return "Main Page";
//    }
//
//    @GetMapping("/user")
//    public String getUser() {
//        return "User content";
//    }
//
//    @GetMapping("/admin")
//    public String getAdmin() {
//        return "Admin content";
//    }
//
//    @GetMapping("/createUser")
//    public Customer createUser() {
//        final Customer customer = new Customer();
//        customer.setUsername("abc");
//        customer.setPassword(passwordEncoder.encode("123"));
//        customer.setFullName("Pierwszy User");
//        customer.setCity("Warszawa");
//        customer.setRole("ROLE_ADMIN");
//        return customerRepository.save(customer);
//    }
//
//}