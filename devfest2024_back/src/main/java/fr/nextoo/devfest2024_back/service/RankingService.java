package fr.nextoo.devfest2024_back.service;

import fr.nextoo.devfest2024_back.entity.RankingEntity;
import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.repository.RankingRepository;
import fr.nextoo.devfest2024_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RankingService {

    @Autowired
    UserRepository userRepository;

    private final RankingRepository rankingRepository;

    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    public void updateRanking(UserEntity userEntity, int pointsToAdd) {
        Optional<RankingEntity> existingRanking = rankingRepository.findByUser(userEntity);
        if (existingRanking.isPresent()) {
            RankingEntity ranking = existingRanking.get();
            ranking.setPoints(ranking.getPoints() + pointsToAdd);
            rankingRepository.save(ranking);
        } else {
            RankingEntity newRanking = new RankingEntity(userEntity.getId().toString(), pointsToAdd);
            rankingRepository.save(newRanking);
        }
    }

    public List<RankingEntity> getTopRankings() {
        userRepository.findHighScoreUsers();
        return rankingRepository.findAll();
    }
}
