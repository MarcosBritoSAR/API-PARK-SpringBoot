package com.marcosbrito.parkapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RespostaPersonalizada {

    private String STATUS = "Deu foi certo";
    private String DESCRIPTION = "É VERDADE, DEU CERTO MESMO";
    private String NOTA = "CONCORDO COM A AFIRMAÇÃO A CIMA, DEU CERTO MESMO";
}
