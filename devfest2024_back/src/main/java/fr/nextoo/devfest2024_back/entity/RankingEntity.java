package fr.nextoo.devfest2024_back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ranking")
@NoArgsConstructor
public class RankingEntity {

    @Id
    private String userId;

    @ManyToOne
    private UserEntity user;

    private Integer points;

    public RankingEntity(String userId, Integer points) {
        this.userId = userId;
        this.points = points;
    }

    // Getters et setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
