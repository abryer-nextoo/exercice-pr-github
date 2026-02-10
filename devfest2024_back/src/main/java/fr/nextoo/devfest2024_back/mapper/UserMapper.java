package fr.nextoo.devfest2024_back.mapper;

import fr.nextoo.devfest2024_back.dto.CreateUserDTO;
import fr.nextoo.devfest2024_back.dto.UserResponseDTO;
import fr.nextoo.devfest2024_back.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity toEntity(CreateUserDTO dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setFirstname(dto.getFirstname());
        entity.setLastname(dto.getLastname());
        entity.setHouse(dto.getHouse());
        entity.setScore(dto.getScore());

        return entity;
    }

    public UserResponseDTO toDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return new UserResponseDTO (
                entity.getId(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getHouse(),
                entity.getScore()
        );
    }
}