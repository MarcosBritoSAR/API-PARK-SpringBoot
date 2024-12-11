package com.marcosbrito.parkapi.dtos;

import com.marcosbrito.parkapi.entity.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter @Setter @NoArgsConstructor
public class UsuarioDTO {
    private String username;
    private String password;
}
