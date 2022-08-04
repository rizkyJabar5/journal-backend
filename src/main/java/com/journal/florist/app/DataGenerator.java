package com.journal.florist.app;

import com.journal.florist.backend.feature.product.repositories.ProductRepository;
import com.journal.florist.backend.feature.user.repositories.UserRepository;
import com.journal.florist.backend.feature.user.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(AppUserService userServiceImpl,
                                      UserRepository userRepository,
                                      ProductRepository productRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (userRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");

            logger.info("... generating 2 User entities...");
//            userService.saveRole(new Role(ROLE_SUPERADMIN, "Role Control with super access"));
//            userService.saveRole(new Role(ROLE_OWNER,"Access application of Owner"));
//            userService.saveRole(new Role(ROLE_CASHIER,"Cashier manage to "));
//            userService.saveRole(new Role(ERole.ROLE_USER,"Global User"));
////
//            userService.saveUser(new AppUser("kiki", "Gravigik Verse", "kiki@email.com", "mugiwara", new HashSet<>()));
//            userService.saveUser(new AppUser("maskiki", "Mas Kiki", "maskiki98@email.com", "mugiwara", new HashSet<>()));
//            userService.saveUser(new AppUser("crikik", "Crikik", "crikik76@email.com", "mugiwara", new HashSet<>()));
//            userService.saveUser(new AppUser("gikgiw", "Gikgik", "gurugagik48@email.com", "mugiwara", new HashSet<>()));
//            userService.saveUser(new AppUser("rac", "Rizky Abdul Crikik", "rskhuguhugu12@email.com", "mugiwara", new HashSet<>()));

//            userService.addRoleToUser("rac", ROLE_USER);
//            userService.addRoleToUser("rac", ROLE_OWNER);
//            userService.addRoleToUser("maskiki", ROLE_CASHIER);
//            userService.addRoleToUser("crikik", ROLE_USER);
//            userService.addRoleToUser("gikgiw", ROLE_USER);
//            userService.addRoleToUser("kiki", ROLE_CASHIER);
//            userService.addRoleToUser("kiki", ROLE_SUPERADMIN);
//            userService.addRoleToUser("kiki", ROLE_USER);
//
//
//            Role roleAdmin = roleRepository.findByRoleName(ROLE_SUPERADMIN);
//            Set<Role> roleSuperadmin = Stream.of(roleAdmin).collect(Collectors.toCollection(HashSet::new));
//            User admin = new User();
//            admin.setUsername("khamet.admin");
//            admin.setEmail("rkhamet12@gmail.com");
//            admin.setHashedPassword(passwordEncoder.encode("mugiwara"));
//            admin.setRoles(roleSuperadmin);
//            userRepository.saveIdentityUser(admin);

            logger.info("... generating 100 Produk entities...");
//            ExampleDataGenerator<Product> produkRepositoryGenerator = new ExampleDataGenerator<>(Product.class,
//                    LocalDateTime.of(2022, 1, 23, 0, 0, 0));
//            produkRepositoryGenerator.setData(Product::setProductId, DataType.ID);
//            produkRepositoryGenerator.setData(Product::setGambar, DataType.FOOD_PRODUCT_IMAGE);
//            produkRepositoryGenerator.setData(Product::setNamaProduk, DataType.FOOD_PRODUCT_NAME);
//            produkRepositoryGenerator.setData(Product::setKategori, DataType.TWO_WORDS);
//            produkRepositoryGenerator.setData(Product::setHarga, DataType.FOOD_PRODUCT_EAN);
//            produkRepositoryGenerator.setData(Product::setStok, DataType.NUMBER_UP_TO_1000);
//            produkRepository.saveAll(produkRepositoryGenerator.create(100, seed));

            logger.info("Generated demo data");
        };
    }

}