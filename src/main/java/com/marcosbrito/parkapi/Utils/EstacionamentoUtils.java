package com.marcosbrito.parkapi.Utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EstacionamentoUtils {

    private static final double PRIMEIROS_15_MINUTES = 5.00;
    private static final double PRIMEIROS_60_MINUTES = 9.25;
    private static final double ADICIONAL_15_MINUTES = 1.75;
    private static final double DESCONTO_PERCENTUAL = 0.30;


    public static String gerarRecibo() {
        LocalDate date = LocalDate.now();
        //Personalizando a data

        String recibo = date.toString().substring(0, 19);
        return recibo.replace("-", "").replace(":", "").replace("T", "-");
    }

    public static BigDecimal calcularCusto(LocalDateTime entrada, LocalDateTime saida) {
        // Calcula a diferença em minutos entre os horários de entrada e saída
        long minutes = entrada.until(saida, ChronoUnit.MINUTES);

// Inicializa o total a ser cobrado
        double total = 0.0;

// Regra para os primeiros 15 minutos
        if (minutes <= 15) {
            // Se o total de minutos for 15 ou menos, aplica o valor fixo correspondente
            total = PRIMEIROS_15_MINUTES;

// Regra para os primeiros 60 minutos
        } else if (minutes <= 60) {
            // Se o total de minutos for maior que 15 e até 60, aplica o valor fixo correspondente
            total = PRIMEIROS_60_MINUTES;

// Regra para mais de 60 minutos
        } else {
            // Calcula o tempo adicional que excede os primeiros 60 minutos
            long addicionalMinutes = minutes - 60;

            // Divide o tempo adicional em blocos de 15 minutos
            Double totalParts = ((double) addicionalMinutes / 15);

            // Verifica se o número de blocos é uma fração ou um número inteiro
            if (totalParts > totalParts.intValue()) {
                // Se for uma fração (ex.: 4.66), arredonda para cima adicionando mais um bloco
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * (totalParts.intValue() + 1));
            } else {
                // Se for um número inteiro (ex.: 4), calcula o custo exato com base no número de blocos
                total += PRIMEIROS_60_MINUTES + (ADICIONAL_15_MINUTES * totalParts.intValue());
            }
        }

// O valor total calculado está armazenado na variável `total`

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal calcularDesconto(BigDecimal custo, long numeroDeVezes) {
        BigDecimal desconto = ((numeroDeVezes > 0) && (numeroDeVezes % 10 == 0)) ? custo.multiply(new BigDecimal(DESCONTO_PERCENTUAL)) : new BigDecimal(0);
        return desconto.setScale(2, RoundingMode.HALF_EVEN);
    }
}
