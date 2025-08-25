package org.inghub.brokagefirm.controller;

import org.inghub.brokagefirm.model.Customer;
import org.inghub.brokagefirm.repository.CustomerRepository;
import org.inghub.brokagefirm.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(CustomerRepository customerRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestParam String userName, @RequestParam String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByUserName(userName);
        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Customer customer = optionalCustomer.get();
        if (!passwordEncoder.matches(password, customer.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return jwtUtil.generateToken(customer);
    }
}