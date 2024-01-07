package de.limago.simplesecurity.controllers;


import de.limago.simplesecurity.services.HighSecureService;
import de.limago.simplesecurity.services.LowSecureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure")
public class SecuredController {
	
	
	private final LowSecureService lowSecureService;
	private final HighSecureService highSecureService;

	public SecuredController(final LowSecureService lowSecureService, final HighSecureService highSecureService) {
		this.highSecureService = highSecureService;
		this.lowSecureService = lowSecureService;
	}

	@GetMapping(path = "/low")
	public void foo() {
		lowSecureService.lowSecureSeviceFoo();
	}

	@GetMapping(path = "/high")
	public void bar() {
		highSecureService.secureServicesMethodFoo();
	}
	
	
	
	@GetMapping("/safe")
	public String notSafe() {
		return "i am save";
	}



	}


