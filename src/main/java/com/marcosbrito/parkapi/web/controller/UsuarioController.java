package com.marcosbrito.parkapi.web.controller;


import com.marcosbrito.parkapi.web.dto.UsuarioCreateDTO;
import com.marcosbrito.parkapi.web.dto.UsuarioResponseDTO;
import com.marcosbrito.parkapi.web.dto.UsuarioSenhaDTO;
import com.marcosbrito.parkapi.web.dto.mapper.UsuarioMapper;
import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.service.UsuarioService;
import com.marcosbrito.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contem todas as informacoes necessarias para registro de usuario. ") //Descreve um atributo
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {


    private final UsuarioService usuarioService;

    @Operation(summary = "Consulta um usuário", description = "Recurso para consultar um usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })

    /*
    *A anotacao valid informa para o controlador que o UsuarioCreateDTO precisa ser validado*/
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDTO createDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }



    @Operation(summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@Valid @PathVariable Long id, @RequestBody UsuarioSenhaDTO dto) {
        //Cfria-se um endPoint que permite a alteracao da senha
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        //Nao retorna nada
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
