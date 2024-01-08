package de.limago.simplesecurity.services;

import de.limago.simplesecurity.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@ContextConfiguration()
@Import({ PreAuthorizeServiceImpl.class,SecurityConfig.class})
class PreAuthorizeServiceImplTest {


    @Autowired
    private PreAuthorizeServiceImpl objectUnderTest;

    @Test
    @WithMockUser(username = "john", roles = { "VIEWER", "ADMIN" })
    public void givenRoleViewer_whenCallGetUsername_thenReturnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        var userName =  securityContext.getAuthentication().getName();
        //securityContext.getAuthentication().getAuthorities().stream().toList().forEach(System.out::println);
        assertEquals("john", userName);
    }

    @Test
    @WithMockUser(username = "john", roles = { "USER" })
    public void getHelloWorldInUpperCase_validUser_returnsString() {

        assertEquals("HELLO WORLD", objectUnderTest.getHelloWorldInUpperCase());
    }

    @Test
    @WithMockUser(username = "john", roles = { "WRONG" })
    //@WithAnonymousUser
    public void getHelloWorldInUpperCase_inValidUser_returnsString() {
          assertThrows(AccessDeniedException.class, ()->objectUnderTest.getHelloWorldInUpperCase());
    }


}