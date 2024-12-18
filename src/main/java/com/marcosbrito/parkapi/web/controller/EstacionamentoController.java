package com.marcosbrito.parkapi.web.controller;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import com.marcosbrito.parkapi.service.EstacionamentoService;
import com.marcosbrito.parkapi.web.dto.EstacionamentoCreateDto;
import com.marcosbrito.parkapi.web.dto.EstacionamentoResponseDto;
import com.marcosbrito.parkapi.web.dto.mapper.ClienteVagaMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

    public final EstacionamentoService estacionamentoService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstacionamentoResponseDto> checkIn(@RequestBody @Valid EstacionamentoCreateDto dto){
        ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);
        estacionamentoService.checkin(clienteVaga);
        //Fazendo a transformação de um objeto retornado pelo service em response DTO
        EstacionamentoResponseDto estacionamentoResponseDto = ClienteVagaMapper.toDto(clienteVaga);

        //Retornando o cabeçalho como comprovante
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{recibo}")
                .buildAndExpand(clienteVaga.getRecibo())
                .toUri();

        //Desta forma, o metodo retorna tanto o cabeçalho quanto o corpo
        return ResponseEntity.created(location).body(estacionamentoResponseDto);
    }
}
