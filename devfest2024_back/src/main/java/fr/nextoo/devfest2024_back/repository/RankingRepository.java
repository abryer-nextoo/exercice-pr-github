package fr.nextoo.devfest2024_back.repository;

import fr.nextoo.devfest2024_back.entity.RankingEntity;
import fr.nextoo.devfest2024_back.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<RankingEntity, String> {

    @Query("SELECT r FROM RankingEntity r WHERE r.user = :user")
    Optional<RankingEntity> findByUser(UserEntity user);

    @Query("SELECT r FROM RankingEntity r")
    List<RankingEntity> findAll();
}
