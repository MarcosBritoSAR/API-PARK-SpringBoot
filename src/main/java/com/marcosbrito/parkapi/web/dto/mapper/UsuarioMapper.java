package com.marcosbrito.parkapi.web.dto.mapper;


import com.marcosbrito.parkapi.web.dto.UsuarioCreateDto;
import com.marcosbrito.parkapi.web.dto.UsuarioResponseDto;
import com.marcosbrito.parkapi.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;

//Essa classe é usada para gerenciar os Mapper.
public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto createDto) {
        return new ModelMapper().map(createDto, Usuario.class);
    }


    //TODO: PRECISO EXAMINAR ISSO AQUI POR QUE EU NAO ENTENDI FOI NADA
    public static UsuarioResponseDto toDto(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());
        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);
        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
        return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}