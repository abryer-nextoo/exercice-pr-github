package fr.nextoo.devfest2024_back.service;

import fr.nextoo.devfest2024_back.dto.UserResponseDTO;
import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.enumeration.House;
import fr.nextoo.devfest2024_back.exception.NoWinnerFoundException;
import fr.nextoo.devfest2024_back.mapper.UserMapper;
import fr.nextoo.devfest2024_back.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WinnerSelectionService {
    private final ScoreService scoreService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public WinnerSelectionService(
            final ScoreService scoreService,
            final UserRepository userRepository,
            final UserMapper userMapper
    ){
        this.scoreService = scoreService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO selectRandomWinner() {
        House winningHouse = scoreService.getWinningHouse();
        List<UserEntity> candidates = userRepository.findAllByHouse(winningHouse);

        if (candidates.isEmpty()) {
            throw new NoWinnerFoundException("Aucun utilisateur trouvé");
        }

        Collections.shuffle(candidates);
        return userMapper.toDto(candidates.get(0));
    }
}
