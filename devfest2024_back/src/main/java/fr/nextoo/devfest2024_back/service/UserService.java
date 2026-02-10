package fr.nextoo.devfest2024_back.service;

import fr.nextoo.devfest2024_back.dto.CreateUserDTO;
import fr.nextoo.devfest2024_back.dto.UpdateScoreDTO;
import fr.nextoo.devfest2024_back.dto.UserResponseDTO;
import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.enumeration.House;
import fr.nextoo.devfest2024_back.exception.NoWinnerFoundException;
import fr.nextoo.devfest2024_back.exception.UserAlreadyExistsException;
import fr.nextoo.devfest2024_back.exception.UserNotFoundException;
import fr.nextoo.devfest2024_back.mapper.UserMapper;
import fr.nextoo.devfest2024_back.repository.UserRepository;
import fr.nextoo.devfest2024_back.repository.projection.HouseScore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final int PAGE_SIZE = 20;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(
            final UserRepository userRepository,
            final UserMapper userMapper
    ){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Map<House, Integer> getScoresByHouse() {
        List<HouseScore> results = userRepository.findScoresByHouse();

        Map<House, Integer> scoreMap = Arrays.stream(House.values())
                .collect(Collectors.toMap(h -> h, h -> 0));

        results.forEach(result ->
                scoreMap.put(result.getHouse(), result.getScore())
        );

        return scoreMap;
    }

    public Page<UserResponseDTO> getUsers(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Order.desc("score")));
        Page<UserEntity> entities = userRepository.findAll(pageable);
        return entities.map(userMapper::toDto);
    }

    public Page<UserResponseDTO> getUsersByHouse(int page, House house) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Order.desc("score")));
        return userRepository.findAllByHouse(house, pageable)
                .map(userMapper::toDto);
    }

    @Transactional
    public UserResponseDTO addUser(CreateUserDTO createUserDto) {
        Optional<UserEntity> existingUser = userRepository.findByPhone(createUserDto.getPhone());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Un utilisateur avec le numéro de téléphone " + createUserDto.getPhone() + " existe déjà");
        }

        UserEntity userEntity = userMapper.toEntity(createUserDto);
        UserEntity savedEntity = userRepository.save(userEntity);

        return userMapper.toDto(savedEntity);
    }

    @Transactional
    public UserResponseDTO updateUserScore(UUID userId, UpdateScoreDTO updateScoreDto) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "Utilisateur non trouvé avec l'ID: " + userId));

        user.addPoints(updateScoreDto.getPoints());
        UserEntity updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public UserResponseDTO findWinner() {
        Map<House, Integer> scores = getScoresByHouse();
        House houseWithMaxScore = Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();
        List<UserEntity> userList = userRepository.findAllByHouse(houseWithMaxScore);
        Collections.shuffle(userList);

        if (userList.isEmpty()) {
            throw new NoWinnerFoundException("Aucun utilisateur trouvé");
        }

        return userMapper.toDto(userList.get(0));
    }
}
