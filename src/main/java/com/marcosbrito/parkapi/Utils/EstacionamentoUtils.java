package com.marcosbrito.parkapi.Utils;

import java.time.LocalDate;

public class EstacionamentoUtils {

    public static String gerarRecibo(){
        LocalDate date = LocalDate.now();
        //Personalizando a data

        String recibo = date.toString().substring(0, 19);
        return recibo.replace("-","").replace(":","").replace("T","-");
    }
}
