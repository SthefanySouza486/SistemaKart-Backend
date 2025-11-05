package br.com.kartclub.kartapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "O email é obrigatório!")
    @Email
    private String email;

    @NotBlank(message = "A senha é obrigatória!")
    private String senha;
}
