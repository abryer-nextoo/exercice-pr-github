package fr.nextoo.devfest2024_back.service;

import fr.nextoo.devfest2024_back.entity.UserEntity;
import fr.nextoo.devfest2024_back.enumeration.House;
import fr.nextoo.devfest2024_back.exception.UserInformationException;
import fr.nextoo.devfest2024_back.repository.UserRepository;
import fr.nextoo.devfest2024_back.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserValidator validator;

    public UserService(
            final UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    public Map<String,Integer> getScores() {
        Map<String,Integer> scores = new HashMap<>();
        Arrays.stream(House.values()).forEach(house -> scores.put(house.name(), userRepository.findHouseScore(house).orElse(0)));
        return scores;
    }

    public Page<UserEntity> getAllWithPage(int page) {
        Pageable pageable = PageRequest.of(page,20, Sort.by(Sort.Order.desc("score")));
        Random rand = new Random();
        try {
            Thread.sleep(rand.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userRepository.findAll(pageable);// Pas de gestion des exceptions
    }

    public Page<UserEntity> getAllWithHouse(int page, House house) {
        Pageable pageable = PageRequest.of(page,20, Sort.by(Sort.Order.desc("score")));
        return userRepository.findAllByHouse(house,pageable);
    }

    public UserEntity addUser(UserEntity userEntity) {
        validator.validateUser(userEntity);
        Optional<UserEntity> userEntityOptional = this.userRepository.findByPhone(userEntity.getPhone());
        userEntityOptional.ifPresent(entity -> userEntity.setId(entity.getId()));
        return userRepository.save(userEntity);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public UserEntity findWinner() {
        Map<House, Integer> scores = Arrays.stream(House.values())
                .collect(Collectors.toMap(house -> house, house -> userRepository.findHouseScore(house).orElse(0)));

        House houseWithMaxScore = Collections.max(scores.entrySet(), Map.Entry.comparingByValue()).getKey();

        List<UserEntity> userList = userRepository.findAllByHouse(houseWithMaxScore);
        Collections.shuffle(userList);
        if(userList.isEmpty()) {
            return null;
        }else{
            return userList.get(0);
        }
    }
}
