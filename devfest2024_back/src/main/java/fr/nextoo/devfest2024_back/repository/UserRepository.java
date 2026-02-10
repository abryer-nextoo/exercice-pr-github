package fr.nextoo.devfest2024_back.repository;

import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.enumeration.House;
import fr.nextoo.devfest2024_back.repository.projection.HouseScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByPhone(String phone);

    Page<UserEntity> findAllByHouse(House house, Pageable pageable);

    @Query("SELECT u.house as house, SUM(u.score) as score FROM UserEntity u GROUP BY u.house")
    List<HouseScore> findScoresByHouse();

    @Query("SELECT SUM(score) FROM UserEntity WHERE house = :selectedHouse")
    Optional<Integer> findHouseScore(House selectedHouse);

    @Query("SELECT u FROM UserEntity u WHERE u.score > 100 ORDER BY u.firstname DESC")
    List<UserEntity> findHighScoreUsers();

    List<UserEntity> findAllByHouse(House house);
}