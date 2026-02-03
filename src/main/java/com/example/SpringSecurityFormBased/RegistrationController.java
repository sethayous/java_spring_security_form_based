package com.example.SpringSecurityFormBased;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationService registrationService;
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping()
    public String addRegister(@RequestBody RegistrationRequest registrationRequest){
        return registrationService.EmailRegister(registrationRequest);
    }

    @GetMapping("/find/{useremail}")
    public String findUserByEmail(@PathVariable("useremail") String email){
        return "found";
    }
}