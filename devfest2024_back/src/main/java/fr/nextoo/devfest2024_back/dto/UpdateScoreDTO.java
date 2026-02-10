package fr.nextoo.devfest2024_back.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScoreDTO {

    @NotNull(message = "Le score est obligatoire")
    @Min(value = 0, message = "Le score doit être positif")
    private Integer points;

}