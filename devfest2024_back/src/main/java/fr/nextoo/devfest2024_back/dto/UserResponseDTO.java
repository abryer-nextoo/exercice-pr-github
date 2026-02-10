package fr.nextoo.devfest2024_back.dto;

import fr.nextoo.devfest2024_back.enumeration.House;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String phone;
    private String email;
    private String firstname;
    private String lastname;
    private House house;
    private Integer score;
}