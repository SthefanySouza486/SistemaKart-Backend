package br.com.kartclub.kartapi.controller;

import br.com.kartclub.kartapi.dto.auth.LoginRequestDTO;
import br.com.kartclub.kartapi.dto.auth.LoginResponseDTO;
import br.com.kartclub.kartapi.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){
        LoginResponseDTO response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }
}