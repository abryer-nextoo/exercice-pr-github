package fr.nextoo.devfest2024_back.controller;

import fr.nextoo.devfest2024_back.entity.RankingEntity;
import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.service.RankingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateRanking(@RequestParam String userId, @RequestParam int points) {
        rankingService.updateRanking(new UserEntity(userId), points);
        return new ResponseEntity<>("Classement mis à jour", HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<List<RankingEntity>> getTopRankings() {
        List<RankingEntity> rankings = rankingService.getTopRankings();
        if (rankings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }
}
