package de.limago.simplesecurity.aspects;

import de.limago.simplesecurity.aspects.annotations.RunAsRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Aspect
@Component
public class RunAsRoleInterceptor {

    @Before("Pointcuts.runAsRoleMethods()")
    public void before() {
        System.out.println("######  BEFORE  ######");
    }

    //@Around("execution(public * de.limago.simplesecurity.services.LowSecureService.*(..)) ")
    @Around("Pointcuts.runAsRoleMethods()")
    public Object runAsRoleDecorator(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("###  Hallo ###");
        Authentication originalAuth = SecurityContextHolder.getContext().getAuthentication();

        try {

            var value = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(RunAsRole.class).value();
            // Erstelle eine temporäre Authentifizierung basierend auf der Rolle
            Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(value));
            Authentication auth = new CustomAuthenticationToken(originalAuth.getPrincipal(), originalAuth.getCredentials(), authorities);

            SecurityContextHolder.getContext().setAuthentication(auth); // Setze die temporäre Identität

            return proceedingJoinPoint.proceed();
        } finally {
            SecurityContextHolder.getContext().setAuthentication(originalAuth); // Setze die ursprüngliche Identität wieder her
        }




    }

    private class CustomAuthenticationToken extends AbstractAuthenticationToken {

        private final Object principal;

        public CustomAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.principal = principal;
            super.setAuthenticated(true);
        }

        public CustomAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
            super(authorities);
            this.principal = principal;
            super.setAuthenticated(true);
        }

        @Override
        public Object getCredentials() {
            return null; // In diesem Beispiel gibt es keine expliziten Anmeldeinformationen für die Rolle
        }

        @Override
        public Object getPrincipal() {
            return this.principal;
        }
    }
}
