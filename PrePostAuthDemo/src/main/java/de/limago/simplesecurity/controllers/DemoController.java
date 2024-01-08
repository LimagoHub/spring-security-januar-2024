package de.limago.simplesecurity.controllers;

import de.limago.simplesecurity.services.PreAuthorizeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    private final PreAuthorizeServiceImpl service;

    @GetMapping("hello")
    public String getValue() {
        return service.getHelloWorldInUpperCase();
    }

}
