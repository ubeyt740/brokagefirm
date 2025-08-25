package org.inghub.brokagefirm.config;


import org.inghub.brokagefirm.model.Asset;
import org.inghub.brokagefirm.model.Customer;
import org.inghub.brokagefirm.model.Role;
import org.inghub.brokagefirm.repository.AssetRepository;
import org.inghub.brokagefirm.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository,
                                   AssetRepository assetRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {

            // --- CUSTOMERS ---
            Customer customer1 = new Customer();
            customer1.setUserName("admin");
            customer1.setPassword(passwordEncoder.encode("admin123"));
            customer1.setFirstName("Admin");
            customer1.setLastName("User");
            customer1.setRole(Role.ADMIN);
            customerRepository.save(customer1);

            Customer customer2 = new Customer();
            customer2.setUserName("customer2");
            customer2.setPassword(passwordEncoder.encode("123456"));
            customer2.setFirstName("Ubeyt");
            customer2.setLastName("Dalkılıç");
            customer2.setRole(Role.USER);
            customerRepository.save(customer2);

            Customer customer3 = new Customer();
            customer3.setUserName("customer3");
            customer3.setPassword(passwordEncoder.encode("123456"));
            customer3.setFirstName("Şükrü");
            customer3.setLastName("Hasta");
            customer3.setRole(Role.USER);
            customerRepository.save(customer3);

            // --- ASSETS ---
            Asset asset1 = new Asset();
            asset1.setCustomerId(customer2.getId());
            asset1.setAssetName("TRY");
            asset1.setSize(10000.0);
            asset1.setUsableSize(10000.0);
            assetRepository.save(asset1);

            Asset asset2 = new Asset();
            asset2.setCustomerId(customer2.getId());
            asset2.setAssetName("ING");
            asset2.setSize(100.0);
            asset2.setUsableSize(100.0);
            assetRepository.save(asset2);

            Asset asset3 = new Asset();
            asset3.setCustomerId(customer3.getId());
            asset3.setAssetName("TRY");
            asset3.setSize(5000.0);
            asset3.setUsableSize(5000.0);
            assetRepository.save(asset3);

            Asset asset4 = new Asset();
            asset4.setCustomerId(customer3.getId());
            asset4.setAssetName("INGHUB");
            asset4.setSize(50.0);
            asset4.setUsableSize(50.0);
            assetRepository.save(asset4);

            System.out.println("Database initialized with sample customers and assets.");
        };
    }
}