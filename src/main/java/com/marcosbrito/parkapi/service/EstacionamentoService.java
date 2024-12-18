package com.marcosbrito.parkapi.service;

import com.marcosbrito.parkapi.Utils.EstacionamentoUtils;
import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.entity.ClienteVaga;
import com.marcosbrito.parkapi.entity.Vaga;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EstacionamentoService {

    private final ClienteVagaService clienteVagaService;
    private final ClienteService clienteService;
    private final VagaService clienteVaga;
    private final VagaService vagaService;

    @Transactional
    public ClienteVaga checkin(ClienteVaga clienteVaga) {
            //Preciso recuperar o cpf e fazer a busca do cliente. Preciso do cliente no metodo por que ele faz parte do mapeamento do ClienteVaga

        Cliente cliente;
        cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());

        //Quando eu logo, o sistema me retorna apenas o Cliente Com o CPF dele. Ao buscar no banco de dados, eu tenho o Cliente Completo

        clienteVaga.setCliente(cliente);

        Vaga vaga = vagaService.buscarVagaLivre();

        //Ocupa a vaga
        vaga.setStatus(Vaga.StatusVaga.OCUPADA);

        /*
        Quando o cliente vaga for salvo no banco de dado, acontecerá o relacionamento
        da vaga ocupada com o cliente
         */
        clienteVaga.setVaga(vaga);
        clienteVaga.setDataEntrada(LocalDateTime.now());

        clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());

        return clienteVagaService.save(clienteVaga);

    }

    @Transactional
    public ClienteVaga checkout(String recibo) {
        ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);

        LocalDateTime dataSaida = LocalDateTime.now();

        BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
        clienteVaga.setValor(valor);

        //Buscando service

        long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());

        BigDecimal desconto= EstacionamentoUtils.calcularDesconto(valor,totalDeVezes);

        clienteVaga.setDesconto(desconto);
        clienteVaga.setDataSaida(dataSaida);
        clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE);

        return clienteVagaService.save(clienteVaga);
    }
}
