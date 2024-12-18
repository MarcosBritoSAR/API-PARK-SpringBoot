package com.marcosbrito.parkapi.web.dto.mapper;

import com.marcosbrito.parkapi.entity.Vaga;
import com.marcosbrito.parkapi.web.dto.VagaCreateDto;
import com.marcosbrito.parkapi.web.dto.VagaResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto toDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
