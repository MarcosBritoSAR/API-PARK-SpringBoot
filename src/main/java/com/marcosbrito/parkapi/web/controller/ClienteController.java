package com.marcosbrito.parkapi.web.controller;


import com.marcosbrito.parkapi.entity.Cliente;
import com.marcosbrito.parkapi.jwt.JwtUserDetails;
import com.marcosbrito.parkapi.jwt.JwtUserDetailsService;
import com.marcosbrito.parkapi.service.ClienteService;
import com.marcosbrito.parkapi.service.UsuarioService;
import com.marcosbrito.parkapi.web.dto.ClienteCreateDto;
import com.marcosbrito.parkapi.web.dto.ClienteResponseDTO;
import com.marcosbrito.parkapi.web.dto.mapper.ClienteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDTO> create(@RequestBody @Valid ClienteCreateDto dto, @AuthenticationPrincipal JwtUserDetails userDetails) {
        Cliente cliente = ClienteMapper.toCliente(dto);

        //Como temos um relacionamento 1 para um com o ususario, precisamos vincular o cliente com o usuairo.
        //Como podemos fazer isso?
        //Vamosa usar o id do usuario -> Para isso usaremos o contexto do Spring security
        //Sempre ao fazer um login, o Spring Security guarda um objeto UserDetails que guarda os objetos do usuario e por lá pegamento o id dele usando o getId

        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.save(cliente);

        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }
}
