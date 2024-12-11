package com.marcosbrito.parkapi.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioSenhaDTO {

    //Create

    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;
}