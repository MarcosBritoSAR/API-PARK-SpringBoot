package com.marcosbrito.parkapi.web.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDTO {

    //NotBlanck é tpo um notnull mas melhorado
    @NotBlank //O notBlank verificar se o conteudo do atributo é diferente de : null ou ""
    @Email
    @Size(min = 1, max = 20)
    private String username;

    @NotBlank
    @Size(min = 1, max = 20)
    private String password;


}
