package com.marcosbrito.parkapi.web.controller;

import com.marcosbrito.parkapi.entity.Usuario;
import com.marcosbrito.parkapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;

@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

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
}
