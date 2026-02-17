package fr.nextoo.devfest2024_back.service;

import fr.nextoo.devfest2024_back.enumeration.House;
import fr.nextoo.devfest2024_back.repository.UserRepository;
import fr.nextoo.devfest2024_back.repository.projection.HouseScore;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    private final UserRepository userRepository;

    public ScoreService(final UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Map<House, Integer> getScoresByHouse() {
        List<HouseScore> results = userRepository.findScoresByHouse();
        Map<House, Integer> scoreMap = Arrays.stream(House.values())
                .collect(Collectors.toMap(h -> h, h -> 0));
        results.forEach(result -> scoreMap.put(result.getHouse(), result.getScore()));
        return scoreMap;
    }

    public House getWinningHouse() {
        Map<House, Integer> scores = getScoresByHouse();
        return Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
