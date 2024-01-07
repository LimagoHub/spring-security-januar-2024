package de.limago.simplesecurity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication

public class SimpleSecurityApplication {

    public static void main(String[] args) {
        final String password = "password";
        final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String hashedPassword = passwordEncoder.encode(password);

        System.out.println(hashedPassword);

        System.out.println(passwordEncoder.matches("password", "$2a$10$okpMatjfDqh8TXmXMYWsrekYD2hSrOtiNXU4qXfmZmTW7nV3hM2Am"));
        SpringApplication.run(SimpleSecurityApplication.class, args);
    }

}
