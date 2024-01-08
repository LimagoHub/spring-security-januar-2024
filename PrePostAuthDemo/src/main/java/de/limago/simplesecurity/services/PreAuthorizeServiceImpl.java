package de.limago.simplesecurity.services;


import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PreAuthorizeServiceImpl {
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getHelloWorldInUpperCase() {
        return "Hello World".toUpperCase();
    }

}
