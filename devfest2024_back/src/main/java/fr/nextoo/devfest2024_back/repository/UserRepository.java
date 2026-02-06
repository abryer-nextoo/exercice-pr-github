package fr.nextoo.devfest2024_back.repository;

import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.enumeration.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, UUID> {

    Optional<UserEntity> findByPhone(String phone);

    Page<UserEntity> findAllByHouse(House house, Pageable pageable);

    UserEntity save(UserEntity userEntity);

    @Query("SELECT SUM(score) FROM UserEntity WHERE house = :selectedHouse")
    Optional<Integer> findHouseScore(House selectedHouse);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity")
    void deleteAll();

    @Query("SELECT u FROM UserEntity u WHERE u.score > 100 ORDER BY u.firstname DESC")
    List<UserEntity> findHighScoreUsers();

    List<UserEntity> findAllByHouse(House house);
}