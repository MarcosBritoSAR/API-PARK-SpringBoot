package com.marcosbrito.parkapi.web.controller;

import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @GetMapping()
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> users = usuarioService.getAll();
        return ResponseEntity.ok().body(users);
    }


    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario usuario1 = usuarioService.salvar(usuario);
        // Retornando uma resposta para o cliente
        return ResponseEntity.ok().body(usuario1);
    }

    //Ao par de chave denuncia que o objetivo é receber um valor da URL e usar como parametro
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsusarioById(@PathVariable Long id) {
            Usuario user = usuarioService.getUsuario(id);
            return ResponseEntity.ok().body(user);
    }

    /*
    * Para devolver uma consulta, pecisamos de um ResponseEntity. Ele encapsula o objeto a ser devolvido e formato JSON
    * Junto com o objeto, ele devolve tambem cabeçalhos e informações adicionais
    * */


    @PatchMapping("/{id}") //Por que usar  o Patch? São boas praticas! o Put é usado pra atualizar tudo e o patch para atualizacao parcial
    public ResponseEntity<Usuario> updateSenha(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario user = usuarioService.updateSenha(id, usuario.getPassword());
        return ResponseEntity.ok().body(user);
    }



}
