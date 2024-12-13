package com.marcosbrito.parkapi.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class JwtToken {
    /*
    Sempre que recebermos um pedido de autenticacao, e a autenticacao ocorrer com sucesso, devemos devolver um token no corpo da resposta.
    Devemos devolver um token do tipo JwtToken
     */
    private String token;
}
