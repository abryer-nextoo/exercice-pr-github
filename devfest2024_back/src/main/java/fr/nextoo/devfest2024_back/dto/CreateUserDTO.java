package fr.nextoo.devfest2024_back.dto;

import fr.nextoo.devfest2024_back.enumeration.House;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^\\d{2} \\d{2} \\d{2} \\d{2} \\d{2}$",
            message = "Le format du téléphone doit être: XX XX XX XX XX")
    private String phone;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
    private String email;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 1, max = 50, message = "Le nom doit contenir entre 1 et 50 caractères")
    private String lastname;

    @NotNull(message = "La maison est obligatoire")
    private House house;

    @NotNull(message = "Le score est obligatoire")
    @Min(value = 0, message = "Le score doit être positif")
    private Integer score;

}