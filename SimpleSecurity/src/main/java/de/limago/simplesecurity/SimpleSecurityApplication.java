package de.limago.simplesecurity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SimpleSecurityApplication {

    public static void main(String[] args) {

        SpringApplication.run(SimpleSecurityApplication.class, args);
    }

}
