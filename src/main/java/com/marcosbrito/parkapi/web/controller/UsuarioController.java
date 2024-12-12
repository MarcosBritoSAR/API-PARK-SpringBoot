package com.marcosbrito.parkapi.web.controller;


import com.marcosbrito.parkapi.web.dto.UsuarioCreateDTO;
import com.marcosbrito.parkapi.web.dto.UsuarioResponseDTO;
import com.marcosbrito.parkapi.web.dto.UsuarioSenhaDTO;
import com.marcosbrito.parkapi.web.dto.mapper.UsuarioMapper;
import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    /*
    *A anotacao valid informa para o controlador que o UsuarioCreateDTO precisa ser validado*/
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioCreateDTO createDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

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
