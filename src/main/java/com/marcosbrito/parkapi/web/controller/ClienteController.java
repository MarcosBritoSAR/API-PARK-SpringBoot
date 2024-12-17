package com.marcosbrito.parkapi.web.controller;


import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.jwt.JwtUserDetails;
import com.marcosbrito.parkapi.repository.projection.ClienteProjection;
import com.marcosbrito.parkapi.service.ClienteService;
import com.marcosbrito.parkapi.service.UsuarioService;
import com.marcosbrito.parkapi.web.dto.ClienteCreateDto;
import com.marcosbrito.parkapi.web.dto.ClienteResponseDto;
import com.marcosbrito.parkapi.web.dto.PageableDto;
import com.marcosbrito.parkapi.web.dto.mapper.ClienteMapper;
import com.marcosbrito.parkapi.web.dto.mapper.PageableMapper;
import com.marcosbrito.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);

        //Como temos um relacionamento 1 para um com o ususario, precisamos vincular o cliente com o usuairo.
        //Como podemos fazer isso?
        //Vamosa usar o id do usuario -> Para isso usaremos o contexto do Spring security
        //Sempre ao fazer um login, o Spring Security guarda um objeto UserDetails que guarda os objetos do usuario e por lá pegamento o id dele usando o getId

        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.save(cliente);

        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }


    //preciso de um recurso que retornar um cliente pelo id
    @Operation(summary = "Localizar um cliente", description = "Recurso para localizar um cliente pelo ID. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable @Valid Long id) {
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok().body(ClienteMapper.toDto(cliente));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(Pageable pageable) {
        Page<ClienteProjection> clientes = clienteService.buscarTodos(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(clientes));
    }


}
