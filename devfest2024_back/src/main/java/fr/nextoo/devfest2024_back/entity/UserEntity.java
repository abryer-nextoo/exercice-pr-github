package fr.nextoo.devfest2024_back.entity;

import fr.nextoo.devfest2024_back.enumeration.House;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String phone;

    private String email;

    private String firstname;

    private String lastname;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private House house;

    private int score;

    public UserEntity(String phone, String email, String firstname, String lastname, House house, Integer score) {
        this.phone = phone;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.house = house;
        this.score = score;
    }

    public void addPoints(int pointsToAdd) {
        if (pointsToAdd < 0) {
            throw new IllegalArgumentException("Cannot add negative points");
        }
        this.score += pointsToAdd;
    }
}