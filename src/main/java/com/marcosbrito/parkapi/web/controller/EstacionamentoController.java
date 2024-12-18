package com.marcosbrito.parkapi.web.controller;

import com.marcosbrito.parkapi.entity.ClienteVaga;
import com.marcosbrito.parkapi.repository.projection.ClienteVagaProjection;
import com.marcosbrito.parkapi.service.ClienteVagaService;
import com.marcosbrito.parkapi.service.EstacionamentoService;
import com.marcosbrito.parkapi.web.dto.EstacionamentoCreateDto;
import com.marcosbrito.parkapi.web.dto.EstacionamentoResponseDto;
import com.marcosbrito.parkapi.web.dto.PageableDto;
import com.marcosbrito.parkapi.web.dto.mapper.ClienteVagaMapper;
import com.marcosbrito.parkapi.web.dto.mapper.PageableMapper;
import com.marcosbrito.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
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
    public final ClienteVagaService clienteVagaService;

    @Operation(summary = "Operação de check-in", description = "Recurso para dar entrada de um veículo no estacionamento. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "URL de acesso ao recurso criado"),
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Causas possiveis: <br/>" +
                            "- CPF do cliente não cadastrado no sistema; <br/>" +
                            "- Nenhuma vaga livre foi localizada;",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
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


    @GetMapping("Check-in/{recibo}")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENTE')")
    public ResponseEntity<EstacionamentoResponseDto> getByRecibo(@PathVariable("recibo") String recibo){
       ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
        EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("Check-out/{recibo}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<EstacionamentoResponseDto> checkout(@PathVariable("recibo") String recibo){
       ClienteVaga clienteVaga = estacionamentoService.checkout(recibo);
        EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllEstacionamentosPorCpf(@PathVariable String cpf, @PageableDefault(size = 5, sort = "dataEntrada", direction = Sort.Direction.ASC) Pageable pageable){

        //Criando uma Interface De Projeção
        Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorClienteCpf(cpf,pageable);

        PageableDto dto = PageableMapper.toDto(projection);

        return ResponseEntity.ok(dto);

    }
}
