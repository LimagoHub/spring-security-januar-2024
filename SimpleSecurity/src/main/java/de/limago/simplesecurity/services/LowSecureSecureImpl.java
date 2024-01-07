package de.limago.simplesecurity.services;



import de.limago.simplesecurity.aspects.annotations.RunAsRole;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class LowSecureSecureImpl implements LowSecureService {
	
	
	private final HighSecureService highSecureService;

	public LowSecureSecureImpl(final HighSecureService highSecureService) {
		
		this.highSecureService = highSecureService;
	}
	
	@Override

	@Secured("ROLE_GUEST")
	@RunAsRole("ADMIN")
	public void lowSecureSeviceFoo() {
		System.out.println("OK");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    System.out.print( "Current User Authorities inside this RunAS method only " + 
	          auth.getAuthorities().toString());
		highSecureService.secureServicesMethodFoo();
	}

	@Override
	public void lowSecureServiceBar() {
		highSecureService.secureServicesMethodBar();
	}

}
