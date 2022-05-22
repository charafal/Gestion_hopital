package com.aallami.projet;

import com.aallami.projet.entities.Patient;
import com.aallami.projet.repositories.PatientRepository;
import com.aallami.projet.security.services.SecurityService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class ProjetApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientrepository){
        return args -> {
            patientrepository.save(new Patient(null, "Ayoub", new Date(), false, 102));
            patientrepository.save(new Patient(null, "Imane", new Date(), false, 200));

        };
    }
    //@Bean
    CommandLineRunner saveUsers(SecurityService securityService){
        return args -> {
            securityService.saveNewUser("mohamed", "1234", "1234");
            securityService.saveNewUser("yasmin", "1234", "1234");
            securityService.saveNewUser("hassan", "1234", "1234");

            securityService.saveNewRole("USER", "");
            securityService.saveNewRole("ADMIN", "");


            securityService.addRoleToUser("mohamed", "USER");
            securityService.addRoleToUser("mohamed", "ADMIN");
            securityService.addRoleToUser("yasmin", "USER");
            securityService.addRoleToUser("hassan", "USER");
        };

    }

}
