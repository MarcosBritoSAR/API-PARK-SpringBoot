package com.marcosbrito.parkapi.dtos.mapper;


import com.marcosbrito.parkapi.dtos.UsuarioCreateDTO;
import com.marcosbrito.parkapi.dtos.UsuarioResponseDTO;
import com.marcosbrito.parkapi.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

//Essa classe é usada para gerenciar os Mapper.
public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDTO createDto) {
        return new ModelMapper().map(createDto, Usuario.class);
    }


    //PRECISO EXAMINAR ISSO AQUI POR QUE EU NAO ENTENDI FOI NADA
    public static UsuarioResponseDTO toDto(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDTO> props = new PropertyMap<Usuario, UsuarioResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDTO.class);
    }

    public static List<UsuarioResponseDTO> toListDto(List<Usuario> usuarios) {
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}